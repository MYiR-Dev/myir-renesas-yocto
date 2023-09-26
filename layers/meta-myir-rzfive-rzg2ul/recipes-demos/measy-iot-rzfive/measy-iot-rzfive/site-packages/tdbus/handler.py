""""
This file is part of python-tdbus. Python-tdbus is free software
available under the terms of the MIT license. See the file "LICENSE" that
was provided together with this source file for the licensing terms.

Copyright (c) 2012 the python-tdbus authors. See the file "AUTHORS" for a
complete list.
"""

import fnmatch
import logging

from tdbus import _tdbus, DBusError


def method(path=None, member=None, interface=None):
    def _decorate(func):
        func.method = True
        func.member = member or func.__name__
        func.path = path
        func.interface = interface
        return func
    return _decorate


def signal_handler(path=None, member=None, interface=None):
    def _decorate(func):
        func.signal_handler = True
        func.member = member or func.__name__
        func.path = path
        func.interface = interface
        return func
    return _decorate


def dbus_object(cls):
    _init_handlers(cls)
    return cls


def _connection(self):
    return self.local.connection


def _message(self):
    return self.local.message


def _set_response(self, format, args):
    """Used by method call handlers to set the response arguments."""
    self.logger.debug("Returning: (%s, %s)", format, args)
    self.local.response = (format, args)


def _init_handlers(cls):
    methods = {}
    signal_handlers = {}

    for name in dir(cls):
        handler = getattr(cls, name)

        if getattr(handler, 'method', False):
            methods[handler.member] = handler
        elif getattr(handler, 'signal_handler', False):
            signal_handlers[handler.member] = handler

    def dispatch(self, connection, message, ignore_path=False):
        """Dispatch a message. Returns True if the message was dispatched."""
        if not hasattr(self, 'local'):
            self.local = connection.Local()
        self.local.connection = connection
        self.local.message = message
        self.local.response = (None, None)
        mtype = message.get_type()
        member = message.get_member()
        if mtype == _tdbus.DBUS_MESSAGE_TYPE_METHOD_CALL:
            if member not in methods:
                return False
            handler = methods[member]
            if handler.interface and handler.interface != message.get_interface():
                return False
            if (not ignore_path and
                handler.path and not fnmatch.fnmatch(message.get_path(), handler.path)):
                return False
            try:
                self.logger.info("calling method for '%s'", member)
                handler(self, message)
            except Exception as e:
                self.logger.error('Uncaught exception in method call: %s', e)
                self.logger.exception(e)
                connection.send_error(message, 'net.tdbus.UncaughtException.' + e.__class__.__name__,
                                           format="s", args=[str(e)])
            else:
                fmt, args = self.local.response
                connection.send_method_return(message, fmt, args)
        elif mtype == _tdbus.DBUS_MESSAGE_TYPE_SIGNAL:
            if member not in signal_handlers:
                return False
            handler = signal_handlers[member]
            if handler.interface and handler.interface != message.get_interface():
                return False
            if handler.path and not fnmatch.fnmatch(message.get_path(), handler.path):
                return False
            try:
                self.logger.info("calling signal handler for '%s'", member)
                handler(self, message)
            except Exception as e:
                self.logger.error('Uncaught exception in signal handler:')
                self.logger.exception(e)
        else:
            return False

    cls.signal_handlers = signal_handlers
    cls.set_response = _set_response
    cls.dispatch = dispatch
    cls.connection = property(_connection)
    cls.message = property(_message)
    cls.logger = logging.getLogger('tdbus')


class DBusHandler(object):
    """Handler for method calls and signals."""

    def __init__(self):
        _init_handlers(type(self))

