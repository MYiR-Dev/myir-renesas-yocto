require include/core-image-renesas-base.inc
require include/core-image-renesas-mmp.inc
require include/core-image-bsp.inc
#IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"
IMAGE_FEATURES += "splash package-management ssh-server-dropbear hwcodecs"

LICENSE = "MIT"

inherit core-image features_check

REQUIRED_DISTRO_FEATURES = "wayland"

CORE_IMAGE_BASE_INSTALL += "weston weston-init weston-examples gtk+3-demo clutter-1.0-examples"
CORE_IMAGE_BASE_INSTALL += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'weston-xwayland matchbox-terminal', '', d)}"


IMAGE_INSTALL_append = " \
    iproute2 \
    i2c-tools \
    can-utils \
    ethtool \
    iperf3 \
    mtd-utils \
    libdrm \
    libdrm-tests \
    pkgconfig \
    glib-2.0 \
"

#IMAGE_INSTALL_append = " \
#    iproute2 \
#    i2c-tools \
#    can-utils \
#    ethtool \
#    iperf3 \
#    mtd-utils \
#    libdrm \
#    libdrm-tests \
#    python3-pip \
#    python \
#    python-pip \
#    python-dbus \
#    python-click \
#    python-chardet \
#    python-certifi \
#    python-babel \
#    python-werkzeug \
#    python-backports-abc \
#   python-dateutil \
#    python-pycairo \
#    python-engineio \
#    python-pytz \
#    python-flask-script \
#    python-flask-user \
#   python-flask-bcrypt \
#    python-flask-navigation \
#   python-flask-sqlalchemy \
#    python-flask-babel \
#    python-flask-restful \
#    python-flask-xstatic \
#    python-flask-bootstrap \
#    python-flask \
#    python-flask-pymongo \
#    python-flask-uploads \
#    python-flask-socketio \
#    python-flask-login \
#    python-flask-nav \
#    python-flask-sijax \
#    python-flask-wtf \
#    python-flask-migrate \
#    python-pip \
#    python-dateutil \
#    python-engineio \
#    python-socketio \
#    python-greenlet \
#    python-dbus \
#    python-setuptools \
#    python-pycairo \
#    python-pygobject \
#"
