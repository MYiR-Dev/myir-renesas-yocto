LIC_FILES_CHKSUM = "file://LICENSE.md;md5=1fb5dca04b27614d6d04abca6f103d8d"
LICENSE="BSD-3-Clause"
PV = "0.89+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#FLASH_WRITER_URL = "git://github.com/renesas-rz/rzg2_flash_writer"
FLASH_WRITER_URL = "git:///home/hjx/renesas/rzg2ul/sources/v3.0.1/myir-renesas-flash-writer;branch=${BRANCH};protocol=file"
BRANCH = "rz_five"
SRCREV = "fde0643cd735349942d2cdac601319e524aca123"

SRC_URI = " \
	${FLASH_WRITER_URL} \
	file://0001-makefile-use-default-build-setting.patch \
"

inherit deploy

S = "${WORKDIR}/git"

do_compile() {
        if [ "${MACHINE}" = "smarc-rzfive" ]; then
                BOARD="RZFIVE_SMARC";
        elif [ "${MACHINE}" = "myir-rzfive" ]; then
                BOARD="RZFIVE_DDR3L_512M_DEV";
        elif [ "${MACHINE}" = "rzfive-dev" ]; then
                BOARD="RZFIVE_13MMSQ_DEV";
        fi
        cd ${S}

	oe_runmake BOARD=${BOARD}
}

do_install[noexec] = "1"

do_deploy() {
        install -d ${DEPLOYDIR}
        install -m 644 ${S}/riscv_output/*.mot ${DEPLOYDIR}
}
PARALLEL_MAKE = "-j 1"
addtask deploy after do_compile
