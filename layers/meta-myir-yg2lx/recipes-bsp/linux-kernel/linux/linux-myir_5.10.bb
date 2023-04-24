DESCRIPTION = "Linux kernel for the RZG2 based board"

require recipes-kernel/linux/linux-yocto.inc
require include/docker-control.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"
#COMPATIBLE_MACHINE_rzg2l = "(smarc-rzg2l|smarc-rzg2lc|smarc-rzg2ul|smarc-rzv2l|rzv2l-dev)"
#COMPATIBLE_MACHINE_rzg2h = "(ek874|hihope-rzg2n|hihope-rzg2m|hihope-rzg2h)"
COMPATIBLE_MACHINE_yg2lx = "(myir-yg2lx|myir-yg2lx-1g)"

#KERNEL_URL = " \
#    git://github.com/renesas-rz/rz_linux-cip.git"

#SRC_URI += "\
#      git://github.com/123markhong/yg2lx-kernel.git;protocol=https;branch=master"
#SRCREV= "96c21b36384abbc828f93ae5ea3edb1e55210082"

#SRC_URI += "\
#      git://github.com/123markhong/myir-renesas-linux.git;protocol=https;branch=develop-rz-L5.10.83"
#SRCREV= "0218cba1bd7bf83c95fa7b942542d4251c6fe345"
#SRCREV= "63e19f3ffb2ce335a11bfe057bd980a684629e34"
#SRCREV = "195975536df5cf843dbe9c629f73ef839b04a199"

SRC_URI += "\
      git://github.com/MYiR-Dev/myir-renesas-linux.git;protocol=https;branch=develop-rz-L5.10.83"
SRCREV= "a42f9ad9078e442a26f89daf433ce9861c45444b"



LINUX_VERSION = "5.10.83"


SRC_URI += " \
    file://0001-arm64-dts-renesas-rzg2l-smarc-Add-uio-support.patch \
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



#BRANCH = "${@oe.utils.conditional("IS_RT_BSP", "1", "rz-5.10-cip13-rt5", "rz-5.10-cip13",d)}"
#SRCREV = "${@oe.utils.conditional("IS_RT_BSP", "1", "c8798f35184b8115f9aba9f972eb12ed3bb4e9e0", "61bbadb8a4b899371c83e1cbadd0a46ffd6ebb40",d)}"

#SRC_URI = "${KERNEL_URL};protocol=https;nocheckout=1;branch=${BRANCH}"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
#LINUX_VERSION ?= "${@oe.utils.conditional("IS_RT_BSP", "1", "5.10.131-cip13-rt5", "5.10.131-cip13",d)}"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR = "r1"

SRC_URI_append = "\
  ${@oe.utils.conditional("USE_DOCKER", "1", " file://docker.cfg ", "", d)} \
  file://touch.cfg \
"

KBUILD_DEFCONFIG = "defconfig"
KCONFIG_MODE = "alldefconfig"

do_kernel_metadata_af_patch() {
	# need to recall do_kernel_metadata after do_patch for some patches applied to defconfig
	rm -f ${WORKDIR}/defconfig
	do_kernel_metadata
}

do_deploy_append() {
	for dtbf in ${KERNEL_DEVICETREE}; do
		dtb=`normalize_dtb "$dtbf"`
		dtb_ext=${dtb##*.}
		dtb_base_name=`basename $dtb .$dtb_ext`
		for type in ${KERNEL_IMAGETYPE_FOR_MAKE}; do
			ln -sf $dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext $deployDir/$type-$dtb_base_name.$dtb_ext
		done
	done
}

addtask do_kernel_metadata_af_patch after do_patch before do_kernel_configme

# Fix race condition, which can causes configs in defconfig file be ignored
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}binutils:do_populate_sysroot"
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}gcc:do_populate_sysroot"
do_kernel_configme[depends] += "bc-native:do_populate_sysroot bison-native:do_populate_sysroot"

# Fix error: openssl/bio.h: No such file or directory
DEPENDS += "openssl-native"
