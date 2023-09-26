SUMMARY = "GStreamer VSPM filter plugin"
SECTION = "multimedia"
LICENSE = "LGPLv2"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base pkgconfig vspmif-user-module kernel-module-mmngr kernel-module-mmngrbuf mmngr-user-module mmngrbuf-user-module"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d"
inherit autotools pkgconfig

SRC_URI = " \
    file://vspmfilter.tar.xz \
    file://0001-Update-correct-base-number-of-VTOP-ioctl.patch \
    file://0002-Fix-issue-vspmfilter-cannot-plugin.patch \
"

SRC_URI_append_rzg2l = " \
    file://0003-Support-Resize-and-Color-fomat.patch \
    file://0004-gstvspmfilter-Fix-ISU-limitation-about-alignment.patch \
    file://0005-recipes-codec-gstreamer1.0-plugin-vspmfilter-Disable.patch \
    file://0006-vspmfilter-Skip-the-frame-if-physical-address-is-NUL.patch \
    file://0007-vspmfilter-Try-to-get-physical-address-from-dmabuf-f.patch \
    file://0008-vspmfilter-Handle-release-the-mmngr-import-pid.patch \
    file://0009-Change-default-format-to-ISU_YUV420_NV12.patch \
    file://0010-Meta-rz-codecs-vspmfilter-Fix-miscalculation-of-buff.patch \
"

S = "${WORKDIR}/vspmfilter"
PV = "1.16.3"

FILES_${PN} = " \
    ${libdir}/gstreamer-1.0/libgstvspmfilter.so \
"

FILES_${PN}-dev = "${libdir}/gstreamer-1.0/libgstvspmfilter.la"
FILES_${PN}-staticdev = "${libdir}/gstreamer-1.0/libgstvspmfilter.a"
FILES_${PN}-dbg = " \
    ${libdir}/gstreamer-1.0/.debug \
    ${prefix}/src"
