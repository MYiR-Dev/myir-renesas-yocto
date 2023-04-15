# MYIR - 2023   Mark .
DESCRIPTION = "COMMAND-DEMO for MYiR"

inherit systemd

DEPENDS = "zlib glibc ncurses  "
SECTION = "libs"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"



## local files
SRC_URI +=" \
      file://command \
      file://etc \
"

S_G = "${WORKDIR}"
#S = "${WORKDIR}/git"

## compile qt demo
#do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"

do_install () {
      ## 
      install -d ${D}/usr/sbin \
      install -d ${D}/etc \


      cp -r ${S_G}/command/* ${D}/usr/sbin
      cp -r ${S_G}/etc/* ${D}/etc
}

FILES_${PN} = " \
		/usr/sbin \
            "

#For dev packages only
INSANE_SKIP_${PN}-dev = "ldflags"
INSANE_SKIP_${PN} = "${ERROR_QA} ${WARN_QA}"
