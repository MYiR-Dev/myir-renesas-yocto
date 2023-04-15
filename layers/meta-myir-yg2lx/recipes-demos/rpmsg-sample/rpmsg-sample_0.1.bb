#
# RPMsg Sample for Linux
#

SUMMARY = "Sample rpmsg application"
SECTION = "examples"
LICENSE = "CLOSED"
DEPENDS = "libmetal open-amp"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
#INHIBIT_PACKAGE_STRIP = "1"

SRC_URI = " \
    file://platform_info.c \
    file://platform_info.h \
    file://OpenAMP_RPMsg_cfg.h \
    file://helper.c \
    file://rsc_table.h \
    file://main.c \
    file://rzg2_rproc.c \
    file://Makefile"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 rpmsg_sample_client ${D}${bindir}
}

