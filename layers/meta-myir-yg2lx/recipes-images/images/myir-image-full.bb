#require dynamic-layers/qt5-layer/images/core-image-qt.bb
#LICENSE = "MIT"
require recipes-images/images/myir-image-weston.bb
require recipes-images/images/myir-image-measy-iot.bb
inherit populate_sdk_qt5 
inherit core-image


IMAGE_FEATURES += "splash package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL += " \
    qt-demo \
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
    ppp-quectel \
    quectel-cm \
    apache2 \
    docker-ce \
    rpmsg-sample \
    cjson \
    iw \
    i2c-tools \
    evtest \
    x264 \
    gstreamer1.0-plugins-ugly \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-base \
    gstreamer1.0-meta-base \
    gstreamer1.0-omx \
    gstreamer1.0-vaapi \
    apache-websocket \
    qtquickcontrols \
    qtquickcontrols2 \
    qtquickcontrols2-dev \
    qtmultimedia \
    qtvirtualkeyboard \
    qtgraphicaleffects \
"

CORE_IMAGE_EXTRA_INSTALL += " \
        packagegroup-qt5-toolchain-target \
        packagegroup-gstreamer1.0-plugins \
        packagegroup-multimedia-libs \
    "

