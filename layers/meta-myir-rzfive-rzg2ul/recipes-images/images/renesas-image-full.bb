#require dynamic-layers/qt5-layer/images/core-image-qt.bb
#LICENSE = "MIT"
require recipes-images/images/myir-image-weston.bb
#require recipes-images/images/myir-image-measy-iot.bb
inherit populate_sdk_qt5 
inherit core-image

RDEPENDS_${PN} += "systemd-analyze"
IMAGE_FEATURES += "splash package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL += " \
    python3 \
    python \
    measy-iot \
    qt-demo \
    measy-command \
    ppp \
    mtd-utils \
    libgpiod \
    libgpiod-tools \
    libmodbus \
    glmark2 \
    watchdog \
    can-utils \
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
    cjson \
    i2c-tools \
    evtest \
    gstreamer1.0-plugins-ugly \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-base \
    gstreamer1.0-meta-base \
    qtquickcontrols \
    qtquickcontrols2 \
    qtquickcontrols2-dev \
    qtmultimedia \
    qtgraphicaleffects \
"
#qtvirtualkeyboard

CORE_IMAGE_EXTRA_INSTALL += " \
        packagegroup-qt5-toolchain-target \
        packagegroup-gstreamer1.0-plugins \
        packagegroup-multimedia-libs \
    "
