require include/rzg2l-security-config.inc
inherit python3native

#UBOOT_URL = "git:///home/hjx/renesas/rzg2ul/sources/v3.0.1/myir-renesas-uboot;branch=${BRANCH};protocol=file"
#BRANCH = "develop-rzg2l-v2021.10"
UBOOT_URL = "git://github.com/MYiR-Dev/myir-renesas-uboot.git;branch=${BRANCH}"
BRANCH = "develop-rzg2ul-v2021.10"

SRC_URI = "${UBOOT_URL};branch=${BRANCH}"
SRCREV = "fe3ab663e30aaab63bf8d033b128b2c6de4f0198"
PV = "v2021.10+git${SRCPV}"





DEPENDS_append = " \
	${@oe.utils.conditional("TRUSTED_BOARD_BOOT", "1", "python3-pycryptodome-native python3-pycryptodomex-native secprv-native", "",d)} \
"

do_compile_append() {

	if [ "${TRUSTED_BOARD_BOOT}" = "1" ]; then
		python3 ${MANIFEST_GENERATION_KCERT} -info ${DIRPATH_MANIFEST_GENTOOL}/info/bl33_${IMG_AUTH_MODE}_info.xml \
			-iskey ${SYMLINK_NATIVE_BOOT_KEY_DIR}/bl33_key.pem -certout bl33-kcert.bin
		
		python3 ${MANIFEST_GENERATION_CCERT} -info ${DIRPATH_MANIFEST_GENTOOL}/info/bl33_${IMG_AUTH_MODE}_info.xml \
			-iskey ${SYMLINK_NATIVE_BOOT_KEY_DIR}/bl33_key.pem -imgin ${B}/${config}/u-boot.bin \
			-certout bl33-ccert.bin -imgout u-boot_tbb.bin
	fi
}

do_install_append() {

	if [ "${TRUSTED_BOARD_BOOT}" = "1" ]; then
		# install firmware images
		install -m 0644 ${B}/bl33-kcert.bin ${D}/boot/bl33-kcert-${MACHINE}.bin
		install -m 0644 ${B}/bl33-ccert.bin ${D}/boot/bl33-ccert-${MACHINE}.bin
		install -m 0644 ${B}/u-boot_tbb.bin ${D}/boot/u-boot-${MACHINE}_tbb.bin
	fi
}

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