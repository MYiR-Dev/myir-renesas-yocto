require u-boot-myir-common_${PV}.inc
require u-boot-myir.inc

DEPENDS += "bc-native dtc-native"

#UBOOT_URL = "git://github.com/renesas-rz/renesas-u-boot-cip.git"
#BRANCH = "v2021.10/rz"
#UBOOT_URL = "git://github.com/123markhong/yg2lx-uboot.git"
#BRANCH = "uboot-v2021.10"
#UBOOT_URL = "git://github.com/123markhong/myir-renesas-uboot.git"
#BRANCH = "develop-rzg2l-v2021.10"
UBOOT_URL = "git://github.com/MYiR-Dev/myir-renesas-uboot.git"
BRANCH = "develop-remi-v2021.10"
#UBOOT_URL = "git:///opt/hjx-source/renesas/remi-pi/sources/myir-renesas-uboot"
#BRANCH = "develop-rzg2l-v2021.10"


SRC_URI = "${UBOOT_URL};branch=${BRANCH}"
#SRCREV = "90edad6e00ef9ebe0ef010561fc7d864f357ad19"
#SRCREV = "293d59522a41ae07644e8a61c600ca0039076d3a"
#SRCREV = "879bd6891e5b09f3c37d65f20c7e05c4b00034a8"
#SRCREV = "3067361a313c3a29181c1bf15afa441ff42a1622"
SRCREV = "75dee8f1759d6b3b140aa42486ad39a7113903ef"
PV = "v2021.10+git${SRCPV}"

#SRC_URI_append = " \
#        file://0006-myir-myd-yg2lx-cm33.patch \
#"

#SRC_URI += " \
#    file://0001-disable-reserved-area-check.patch \
#    file://0002-cmd-Add-cm33-command.patch \
#    file://0003-configs-smarc-rzg2l_defconfig-Enable-CM33-command.patch \
#    file://0004-configs-smarc-rzg2lc_defconfig-Enable-CM33-command.patch \
#    file://0005-configs-smarc-rzg2ul_defconfig-Enable-CM33-command.patch \
#"


UBOOT_SREC_SUFFIX = "srec"
UBOOT_SREC ?= "u-boot-elf.${UBOOT_SREC_SUFFIX}"
UBOOT_SREC_IMAGE ?= "u-boot-elf-${MACHINE}-${PV}-${PR}.${UBOOT_SREC_SUFFIX}"
UBOOT_SREC_SYMLINK ?= "u-boot-elf-${MACHINE}.${UBOOT_SREC_SUFFIX}"

do_deploy_append() {
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -m 644 ${B}/${config}/${UBOOT_SREC} ${DEPLOYDIR}/u-boot-elf-${type}-${PV}-${PR}.${UBOOT_SREC_SUFFIX}
                    cd ${DEPLOYDIR}
                    ln -sf u-boot-elf-${type}-${PV}-${PR}.${UBOOT_SREC_SUFFIX} u-boot-elf-${type}.${UBOOT_SREC_SUFFIX}
                fi
            done
            unset j
        done
        unset i
    else
        install -m 644 ${B}/${UBOOT_SREC} ${DEPLOYDIR}/${UBOOT_SREC_IMAGE}
        cd ${DEPLOYDIR}
        rm -f ${UBOOT_SREC} ${UBOOT_SREC_SYMLINK}
        ln -sf ${UBOOT_SREC_IMAGE} ${UBOOT_SREC_SYMLINK}
        ln -sf ${UBOOT_SREC_IMAGE} ${UBOOT_SREC}
    fi
}
