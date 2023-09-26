#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#SRC_URI_append = " \
#	file://0001-Fixed-an-issue-that-caused-flicker-when-outputting-t.patch \
#"

BRANCH="develop-rzfive-L5.10"
SRC_URI += "\
      git:///home/hjx/renesas/rzg2ul/sources/v3.0.1/myir-renesas-linux;branch=${BRANCH};protocol=file"
SRCREV= "c49eadb5ccd70bdeb590943737f4b58e5d669bcf"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION = "5.10.175"
PV = "${LINUX_VERSION}+git${SRCPV}"
PR = "r1"
KBUILD_DEFCONFIG = "myc_rzfive_defconfig"
