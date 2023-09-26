# MYIR - 2023   Mark .
DESCRIPTION = "IOT-DEMO for MYiR"

inherit systemd

DEPENDS = "zlib glibc ncurses  "
SECTION = "libs"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"



## local files
SRC_URI +=" \
      file://etc \
      file://lib \
      file://usr \
      file://bin \
      file://site-packages \
"

S_G = "${WORKDIR}"
#S = "${WORKDIR}/git"

## compile qt demo
#do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"

do_install () {
      ## 
      install -d ${D}/usr/bin \
      install -d ${D}/etc/dbus-1/system.d/
      install -d ${D}/etc/systemd/system/
      install -d ${D}/etc/systemd/network/
      install -d ${D}/lib/systemd/system/
      install -d ${D}/usr/lib/ 
      install -d ${D}/usr/sbin/
      install -d ${D}/usr/share/measy_iot/
#      install -d ${D}/usr/lib64/python3.8/site-packages/


      cp -r ${S_G}/lib/* ${D}/usr/lib/
      #install -m 0755 ${S_G}/lib/* ${D}/usr/lib64/
      cp -r ${S_G}/etc/dbus-1/system.d/* ${D}/etc/dbus-1/system.d/
      cp -r ${S_G}/etc/systemd/network/* ${D}/etc/systemd/network/
      cp -r ${S_G}/etc/systemd/system/connmand.service ${D}/lib/systemd/system/
      cp -r ${S_G}/etc/systemd/system/measy_iot_start.service ${D}/lib/systemd/system/
      cp -r ${S_G}/bin/* ${D}/usr/bin
      cp -r ${S_G}/usr/share/measy_iot/* ${D}/usr/share/measy_iot/
#      cp -r ${S_G}/site-packages/* ${D}/usr/lib64/python3.8/site-packages/
}

FILES_${PN} = " \
                 /usr/lib/ \
		/usr/sbin \
               /usr/bin \
               /etc/dbus-1/system.d/ \
              /etc/systemd/system/ \
             /etc/systemd/network/ \ 
            /lib/systemd/system/ \
           /usr/share/measy_iot/ \
          /usr/lib/python2.7/ \
            "

#For dev packages only
INSANE_SKIP_${PN}-dev = "ldflags"
INSANE_SKIP_${PN} = "${ERROR_QA} ${WARN_QA}"
SYSTEMD_SERVICE_${PN} = "connmand.service measy_iot_start.service"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"
