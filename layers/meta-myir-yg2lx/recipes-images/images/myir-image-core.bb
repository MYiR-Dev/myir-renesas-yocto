#require dynamic-layers/qt5-layer/images/core-image-qt.bb
#LICENSE = "MIT"
#inherit populate_sdk_qt5 
inherit core-image
require recipes-images/images/myir-universal-tool.bb

IMAGE_FEATURES += "splash package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL += " \
    python3 \
    ppp \
    mtd-utils \
    measy-command \
    libgpiod \
    libgpiod-tools \
    libmodbus \
    glmark2 \
    watchdog \
    can-utils \
    sqlite3 \
    tslib \
    tslib-calibrate \
    tslib-conf \
    tslib-tests \
    iperf3 \
    dpkg  \
    opkg \
    vsftpd \
    proftpd \
    hostapd \
    iptables \
    net-tools \
    ethtool \
    wpa-supplicant \
    ffmpeg \
    alsa-utils \
    v4l-utils \
    quectel-cm \
    ppp-quectel \
    apache2 \
    docker-ce \
    rpmsg-sample \
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

