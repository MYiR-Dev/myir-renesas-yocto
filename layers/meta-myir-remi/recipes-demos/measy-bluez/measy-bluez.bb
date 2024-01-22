# MYIR - 2023   Mark .
DESCRIPTION = "BLUE-DEMO for MYiR"

inherit systemd

DEPENDS = "zlib glibc ncurses  "
SECTION = "libs"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"



## local files
SRC_URI +=" \
      file://hcieventmask \
      file://hcisecfilter \
      file://dbus-org.bluez.service \
      file://bluetooth.conf \
      file://98-bluez5.preset \
      file://firmware \
      file://bluetooth \
      file://write-sn \
      file://MYD-YG2L23_factoryconfig.json \
      file://MEasyTest-DEV \
      file://myir_test \
      file://rtk_hciattach \
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
      install -d ${D}/lib/systemd/system-preset/
      install -d ${D}/usr/libexec/bluetooth/
      install -d ${D}/lib/
      install -d ${D}/lib/systemd/system/
      install -d ${D}/home/root/
      install -d ${D}/usr/share/


      cp -r ${S_G}/98-bluez5.preset ${D}/lib/systemd/system-preset/
      cp -r ${S_G}/bluetooth.conf ${D}/etc/dbus-1/system.d/
      cp -r ${S_G}/hcieventmask ${D}/usr/bin/
      cp -r ${S_G}/hcisecfilter ${D}/usr/bin/
      cp -r ${S_G}/firmware ${D}/lib/
      cp -r ${S_G}/bluetooth/obexd ${D}/usr/libexec/bluetooth

      cp -r ${S_G}/write-sn/libmyir_code.so ${D}/lib/
      cp -r ${S_G}/write-sn/pcba_eeprom ${D}/usr/bin/

    #  install -m 0644 ${S_G}/dbus-org.bluez.service ${D}/etc/systemd/system/
      install -m 0644 ${S_G}/dbus-org.bluez.service ${D}/lib/systemd/system/

      cp -r ${S_G}/MYD-YG2L23_factoryconfig.json ${D}/home/root
      cp -r ${S_G}/MEasyTest-DEV ${D}/home/root
      cp -r ${S_G}/myir_test ${D}/usr/share
      cp -r ${S_G}/rtk_hciattach ${D}/usr/bin/
}

FILES_${PN} = " \
               /usr/bin \
               /etc/dbus-1/system.d/ \
              /etc/systemd/system/ \
             /lib/systemd/system/ \
            /lib/systemd/system-preset/ \
           /usr/libexec/bluetooth/ \
           /lib/ \
          /home/root/ \
         /usr/share/ \
            "

#For dev packages only
INSANE_SKIP_${PN}-dev = "ldflags"
INSANE_SKIP_${PN} = "${ERROR_QA} ${WARN_QA}"
SYSTEMD_SERVICE_${PN} = "dbus-org.bluez.service"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"
