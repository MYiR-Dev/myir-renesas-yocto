#require dynamic-layers/qt5-layer/images/core-image-qt.bb
#LICENSE = "MIT"
#inherit populate_sdk_qt5 
inherit core-image
require include/core-image-renesas-base.inc
require include/core-image-renesas-mmp.inc
require include/core-image-bsp.inc

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"
IMAGE_FEATURES += "splash package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL += " \
    python3 \
    ppp \
    mtd-utils \
    libgpiod \
    libgpiod-tools \
    libmodbus \
    watchdog \
    can-utils \
    ${@bb.utils.contains('MEASY-COMMAND', '1', 'measy-command', '', d)} \
    ${@bb.utils.contains('MEASY-COMMAND-RZFIVE', '1', 'measy-command-rzfive', '', d)} \
    sqlite3 \
    tslib \
    tslib-calibrate \
    tslib-conf \
    tslib-tests \
    iperf3 \
    quectel-cm \
    ppp-quectel \
    iw \
    ffmpeg \
    dpkg  \
    opkg \
    vsftpd \
    proftpd \
    hostapd \
    iptables \
    net-tools \
    ethtool \
    wpa-supplicant \
    alsa-utils \
    v4l-utils \
    docker-ce \
    cjson \
    iw \
    i2c-tools \
    evtest \
    busybox \
    gstreamer1.0-plugins-ugly \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-base \
"

#CORE_IMAGE_EXTRA_INSTALL += " \
#	packagegroup-qt5 \
#        packagegroup-qt5-toolchain-target \
#    "

