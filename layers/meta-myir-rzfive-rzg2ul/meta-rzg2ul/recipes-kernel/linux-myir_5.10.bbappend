#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#SRC_URI_append = " \
#	file://0001-Fixed-an-issue-that-caused-flicker-when-outputting-t.patch \
#"

#BRANCH="develop-rz-L5.10"
#SRC_URI += "\
#      git:///home/hjx/renesas/rzg2ul/sources/v3.0.1/myir-renesas-linux;branch=${BRANCH};protocol=file"

BRANCH="develop-rzg2ul-L5.10.83"
SRC_URI += "\
      git://github.com/MYiR-Dev/myir-renesas-linux.git;branch=${BRANCH}"
SRCREV= "670352589b31f1eb837827394d2ee232f25827ce"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION = "5.10.83"
PV = "${LINUX_VERSION}+git${SRCPV}"
PR = "r1"

SRC_URI += " \
  file://0002-arm64-dts-renesas-rzg2l-smarc-Disable-OSTM2.patch \
    file://0003-arm64-dts-renesas-rzg2lc-smarc-Add-uio-support.patch \
  file://0004-arm64-dts-renesas-rzg2ul-smarc-Add-uio-support.patch \
    file://0005-arm64-dts-renesas-rzg2lc-smarc-Disable-SCIF1-OSTM2.patch \
    file://0006-clk-renesas-r9a07g044-Set-SCIF1-SCIF2-OSTM2.patch \
    file://0007-arm64-dts-renesas-rzg2ul-smarc-Disable-OSTM2.patch \
    file://0008-clk-renesas-r9a07g043-Set-OSTM2.patch \
"
# Kernel confguration update
SRC_URI += "file://uio.cfg"
KBUILD_DEFCONFIG = "myd_rzg2ul_defconfig"
