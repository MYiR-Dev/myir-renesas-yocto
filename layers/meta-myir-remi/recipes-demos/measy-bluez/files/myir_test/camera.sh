#!/bin/bash
/usr/share/myir_test/v4l2-init.sh
weston-start
gst-launch-1.0 v4l2src device=/dev/video0 ! video/x-raw,format=YUY2,width=640,height=480 ! waylandsink &
