#!/bin/bash
echo 519 > /sys/class/gpio/export
echo out > /sys/class/gpio/gpio519/direction
echo 0 > /sys/class/gpio/gpio519/value
echo 518 > /sys/class/gpio/export
echo out > /sys/class/gpio/gpio518/direction
echo 0 > /sys/class/gpio/gpio518/value
