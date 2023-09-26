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
    ppp \
    mtd-utils \
    libgpiod \
    libgpiod-tools \
    libmodbus \
    glmark2 \
    ${@bb.utils.contains('QT-DEMO', '1', 'qt-demo', '', d)} \
    ${@bb.utils.contains('MEASY-IOT', '1', 'measy-iot', '', d)} \
    ${@bb.utils.contains('MEASY-COMMAND', '1', 'measy-command', '', d)} \
    ${@bb.utils.contains('MEASY-COMMAND-RZFIVE', '1', 'measy-command-rzfive', '', d)} \
    ${@bb.utils.contains('RPMSG-SAMPLE', '1', 'rpmsg-sample', '', d)} \
    docker-ce \
    watchdog \
    can-utils \
    tslib \
    tslib-calibrate \
    tslib-conf \
    tslib-tests \
    sqlite3 \
    iw \
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
    quectel-cm \
    ppp-quectel \
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
    qtvirtualkeyboard \
    qtgraphicaleffects \
"

CORE_IMAGE_EXTRA_INSTALL += " \
        packagegroup-gstreamer1.0-plugins \
        packagegroup-multimedia-libs \
        packagegroup-qt5-toolchain-target \
    "
