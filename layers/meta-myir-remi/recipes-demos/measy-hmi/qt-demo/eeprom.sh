#!/bin/sh
eeprom_test -d /dev/i2c-3 -a 0x50 -s 0x00 -r 32 > eeprom.txt
eeprom_test -d /dev/i2c-3 -a 0x50 -s 0x20 -r 32 >> eeprom.txt

echo ">>>PN=`cat eeprom.txt | grep PN | awk -F : '{print $3}'`" > /dev/ttySC0
echo
echo
echo ">>>SN=`cat eeprom.txt | grep SN | awk -F : '{print $3}'`" > /dev/ttySC0
echo
echo
echo "smarc-rzg2l login: root (super user)"
