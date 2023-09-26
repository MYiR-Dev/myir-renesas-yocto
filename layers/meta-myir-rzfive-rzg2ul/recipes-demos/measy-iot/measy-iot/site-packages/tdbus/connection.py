#
# This file is part of python-tdbus. Python-tdbus is free software
# available under the terms of the MIT license. See the file "LICENSE" that
# was provided together with this source file for the licensing terms.
#
# Copyright (c) 2012 the python-tdbus authors. See the file "AUTHORS" for a
# complete list.

import logging
import sys
import traceback

from tdbus import _tdbus


DBusError = _tdbus.Error

MEMBER_REQUEST_NAME = "RequestName"
MEMBER_ADDMATCH = "AddMatch"

PATH_SEPARATOR = '/'

def check_path(path):
    if not path.startswith(PATH_SEPARATOR):
        raise ValueError('Path should be absolute')

class DBusConnection(object):
    """A connection to the D-BUS."""

    Loop = None

    def __init__(self, address):
        """Create a new connection."""
        if self.Loop is None:
            raise NotImplementedError('cannot create Connection without a Loop')
        self._connection = _tdbus.Connection(address)
        self._connection.set_loop(self.Loop(self._connection))
        self._connection.add_filter(self._dispatch)
        self.handlers = []
        self.objects = {}
        self.logger = logging.getLogger('tdbus')

    def add_handler(self, handler):
        """Add a new method/signal handler for this connection."""
        self.handlers.append(handler)

    def add_object(self, path, dbus_object):
        check_path(path)

        self.objects[path] = dbus_object

    def remove_handler(self, handler):
        self.handlers = [x for x in self.handlers if x != handler]

    def remove_object(self, path):
        self.objects.pop(path, None)

    def open(self, address):
        self._connection.open(address)

    def close(self):
        """Close the connection."""
        self._connection.close()

    def get_unique_name(self):
        """Return the unique connection name."""
        return self._connection.get_unique_name()

    def register_name(self, name, do_no_queue=True, allow_replacement=True, replace_existing=True):
        """Register a human readable name for this client

        booleans relate to DBUS_NAME_FLAG_ALLOW_REPLACEMENT, DBUS_NAME_FLAG_REPLACE_EXISTING and DBUS_NAME_FLAG_DO_NOT_QUEUE
        See http://dbus.freedesktop.org/doc/dbus-specification.html#message-bus-names for their meaning


        The reply is one of DBUS_REQUEST_NAME_REPLY_PRIMARY_OWNER, DBUS_REQUEST_NAME_REPLY_IN_QUEUE, DBUS_REQUEST_NAME_REPLY_EXISTS, DBUS_REQUEST_NAME_REPLY_ALREADY_OWNER
        these are defined on this the tdbus package
        """
        flags = 0

        if do_no_queue:
            flags += _tdbus.DBUS_NAME_FLAG_DO_NOT_QUEUE

        if allow_replacement:
            flags += _tdbus.DBUS_NAME_FLAG_ALLOW_REPLACEMENT

        if replace_existing:
            flags += _tdbus.DBUS_NAME_FLAG_REPLACE_EXISTING

        reply = self.call_method(_tdbus.DBUS_PATH_DBUS, MEMBER_REQUEST_NAME, _tdbus.DBUS_INTERFACE_DBUS,
                                             format="su", args=[name, flags],
                                             destination=_tdbus.DBUS_SERVICE_DBUS, timeout=1)

        return reply.get_args()[0]

    def subscribe_to_signals(self):
        """
        Call this to register all signal handlers with dbus.

        The result of this is that signals will be delivered even if they are not explicitly sent to this client
        """
        for handler in self.handlers:
            for signal_handler in handler.signal_handlers.values():
                member = "member='%s'" % signal_handler.member if signal_handler.member else  ""
                interface = "interface='%s'" % signal_handler.interface if signal_handler.interface else  ""
                path = "path='%s'" % signal_handler.path if signal_handler.path else  ""

                signature = ','.join([string for string in [member, interface, path] if string])

                self.call_method(_tdbus.DBUS_PATH_DBUS, MEMBER_ADDMATCH, _tdbus.DBUS_INTERFACE_DBUS,
                                             format="s", args=[signature],
                                             destination=_tdbus.DBUS_SERVICE_DBUS, timeout=1)


    def send_method_return(self, message, format=None, args=None):
        """Send a method call return."""
        reply = _tdbus.Message(_tdbus.DBUS_MESSAGE_TYPE_METHOD_RETURN,
                               reply_serial=message.get_serial(),
                               destination=message.get_sender())
        if format is not None:
            reply.set_args(format, args)
        self._connection.send(reply)

    def send_error(self, message, error_name, format=None, args=None):
        """Send an error reply."""
        reply = _tdbus.Message(_tdbus.DBUS_MESSAGE_TYPE_ERROR,
                               reply_serial=message.get_serial(),
                               destination=message.get_sender(),
                               error_name=error_name)
        if format is not None:
            reply.set_args(format, args)
        self._connection.send(reply)

    def send_signal(self, path, member, interface=None, format=None, args=None,
                    destination=None):
        """Send a signal."""
        if '.' in member:
            member, interface = self._split_member(member)
        if interface is None:
            raise Exception('you need to specify the interface')
        message = _tdbus.Message(_tdbus.DBUS_MESSAGE_TYPE_SIGNAL,
                                 member=member, interface=interface, path=path)
        if destination is not None:
            message.set_destination(destination)
        if format is not None:
            message.set_args(format, args)
        self._connection.send(message)

    def _dispatch(self, message):
        """Dispatch a message. This is installed as a filter into libdbus so
        it gets called on all incoming messages."""
        for handler in self.handlers:
            self.spawn(handler.dispatch, self, message)

        #Dispatch objects
        dbus_object = self.objects.get(message.get_path(), None)
        if dbus_object:
            self.spawn(dbus_object.dispatch, self, message, ignore_path)

        return True

    def spawn(self, handler, *args):
        """Spawn a handler. Can be overrided in a subclass."""
        try:
            handler(*args)
        except Exception as e:
            self.logger.error('Uncaught exception in method call')
            self.logger.exception(e)

    def call_method(self, path, member, interface=None, format=None, args=None,
                    destination=None, callback=None, timeout=None):
        """Call a method."""
        if '.' in member:
            member, interface = self._split_member(member)
        if interface is None:
            raise Exception('you need to specify the interface')

        message = _tdbus.Message(_tdbus.DBUS_MESSAGE_TYPE_METHOD_CALL,
                                 path=path, member=member, interface=interface,
                                 destination=destination)
        if format is not None:
            message.set_args(format, args)
        if callback is None:
            message.set_no_reply(True)
            self._connection.send(message)
        else:
            if timeout is None:
                timeout = -1
            else:
                timeout = int(1000 * timeout)
            deferred = self._connection.send_with_reply(message, timeout)
            deferred.set_notify(callback)

    def _handle_errors(self, reply):
        if reply.get_type() == _tdbus.DBUS_MESSAGE_TYPE_ERROR:
            name = reply.get_error_name()
            args = reply.get_args()

            # strip our own prefix, was only needed to provide a valid dbus error name
            if name.startswith('net.tdbus.UncaughtException'):
                name = name[28:]

            # A string as first argument is optional in dbus error replies.
            # When present is should be an error message
            if args:
                raise type(str(name), (DBusError,), {'type': name})(args[0])
            else:
                raise type(str(name), (DBusError,), {'type': name})()


    def _split_member(self, member):
        return member.rsplit(".", 1)
