#!/bin/sh -e

echo "Start MYiR HMI V2.0..."

export TSLIB_TSDEVICE=/dev/input/event1
export TSLIB_CONFFILE=/etc/ts.conf             
export TSLIB_CALIBFILE=/etc/pointercal 
export TSLIB_PLUGINDIR=/usr/lib/ts
export TSLIB_CONSOLEDEVICE=none
export QT_QPA_FB_TSLIB=1
export QT_QPA_GENERIC_PLUGINS=tslib:/dev/input/event1
echo 7 > /sys/class/backlight/backlight/brightness

sleep 5
/home/mxapp2 -platform linuxfb &
/usr/share/myir_test/age.sh &
exit 0

