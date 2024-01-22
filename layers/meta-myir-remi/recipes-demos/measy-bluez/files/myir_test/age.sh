#!/bin/bash
echo host > /sys/devices/platform/soc/11c50200.usb-phy/role
sleep 3
ifconfig eth0 192.168.40.123
ifconfig eth1 192.168.40.234
ifconfig can0 down
ip link set can0 up type can bitrate 500000 sample-point 0.75 dbitrate 4000000 dsample-point 0.8 fd on
ifconfig can0 up
rtk_hciattach -n -s 115200 ttySC1 rtk_h5 &
sleep 5
rfkill unblock all
hciconfig up
wpa_passphrase MYD-YG2LX-REMI 12345678 > /tmp/wpa_supplicant.conf
wpa_supplicant -c /tmp/wpa_supplicant.conf -i wlan0 -Dnl80211 -B Successfully initialized wpa_supplicant
sleep 2
udhcpc -i wlan0
ifconfig wlan0 192.168.30.248 netmask 255.255.255.0
udhcpc -i eth0
udhcpc -i eth1
sleep 3
ifconfig eth0 192.168.40.123
ifconfig eth1 192.168.40.234
echo "Configuration completed" > /dev/ttySC0
echo "root@myir-remi-1g:~# " > /dev/ttySC0 