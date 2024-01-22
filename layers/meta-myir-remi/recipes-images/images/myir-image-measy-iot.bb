SUMMARY = "Myir  RZ/G2L  core image."
LICENSE = "MIT"

#include recipes-myir/images/myir-image.inc

inherit core-image

#IMAGE_LINGUAS = "en-us"

#IMAGE_FEATURES += "\
#    package-management  \
#    ssh-server-dropbear \
#    "
#IMAGE_INSTALL_append = "\
#                        measy-iot \
#                        "
IMAGE_FEATURES += "splash package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL += " \
    cjson \
    dbus \
    python \
    libffi \
    ncurses \
    readline \
    ppp \
    libgpiod \
    libgpiod-tools \
    can-utils \
    sqlite3 \
    tslib \
    tslib-calibrate \
    tslib-conf \
    tslib-tests \
    iperf3 \
    proftpd \
    hostapd \
    iptables \
    rpmsg-sample \
    net-tools \
    ffmpeg \
    alsa-utils \
    v4l-utils \
    apache2 \
    apache-websocket \
"

INSANE_SKIP_${PN} = "dev-so"

#delete connman  connman-dev

# INSTALL addons
#
