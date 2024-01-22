# MYIR - 2022   Mark .
DESCRIPTION = "QT-DEMO for MYiR"

inherit systemd
#inherit qmake5
#inherit gettext
#inherit pythonnative
#inherit perlnative
#inherit distro_features_check

#DEPENDS = "zlib glibc ncurses qtbase  "
DEPENDS = "zlib glibc ncurses qtbase qtquickcontrols qtquickcontrols2 qtmultimedia  "
SECTION = "libs"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

#SRCREV = "51219782e64843f2090563ff4b7402b6d6faa809"
#HMIV2_BRANCH ?= "hmi2.0-imx6ulx-gw-nogpu"
#HMIV2_SRC ?= "git://github.com/MYiR-Dev/mxapp.git;protocol=https" 

## github link
#SRC_URI += "${HMIV2_SRC};branch=${HMIV2_BRANCH} \
#"

## local files
SRC_URI +=" \
      file://start.sh \
      file://stop-weston.sh \
      file://myir.service \
      file://quit-weston.service \
      file://pnsn.service \
      file://eeprom.sh \
      file://eeprom_test \
      file://fonts \
      file://Capture \
      file://Music \
      file://Video \
      file://ecg \
      file://mxapp2 \
      file://files \
      file://lib \
"

S_G = "${WORKDIR}"
#S = "${WORKDIR}/git"

## compile qt demo
#do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"

do_install () {
      ## 
     install -d ${D}/usr/lib/fonts/ \
      install -d ${D}/home/ \
      install -d ${D}/usr/bin \
      install -d ${D}/etc/ \
      install -d ${D}/usr/lib64 \
      install -d ${D}/home/root/ \
      install -d ${D}/usr/lib64/fonts \
      install -d ${D}/usr/share/myir/ \
      install -d ${D}/usr/share/myir/Music/ \
      install -d ${D}/usr/share/myir/Capture/ \
      install -d ${D}/usr/share/myir/Video/ \
      install -d ${D}/lib/systemd/system/ \


      cp -r ${S_G}/fonts/* ${D}/usr/lib/fonts/
      cp -r ${S_G}/fonts/* ${D}/usr/lib64/fonts/
      cp -r ${S_G}/files/* ${D}/usr/bin
      cp -r ${S_G}/lib/* ${D}/usr/lib64
      cp -r ${S_G}/ecg/* ${D}/usr/share/myir/
      cp -r ${S_G}/Music/* ${D}/usr/share/myir/Music/
      cp -r ${S_G}/Capture/* ${D}/usr/share/myir/Capture/
      cp -r ${S_G}/Video/video1.mp4 ${D}/usr/share/myir/Video/
      cp -r ${S_G}/Video/video2.mp4 ${D}/usr/share/myir/Video/
      cp -r ${S_G}/mxapp2 ${D}/home/
      cp -r ${S_G}/eeprom.sh ${D}/home/
      cp -r ${S_G}/eeprom_test ${D}/usr/bin
      cp -r ${S_G}/start.sh ${D}/usr/bin/
      cp -r ${S_G}/stop-weston.sh ${D}/usr/bin/
      install -m 0644 ${S_G}/myir.service ${D}/lib/systemd/system/
      install -m 0644 ${S_G}/pnsn.service ${D}/lib/systemd/system/
      install -m 0644 ${S_G}/quit-weston.service ${D}/lib/systemd/system/
}

FILES_${PN} = " \
            /usr/lib/fonts/ \
		/home/ \
                /usr/lib64/ \
                /usr/lib64/fonts/ \
		/usr/bin \
		/etc/ \
		/home/root \
		/usr/lib \
		/usr/share/myir/ \
		/usr/share/myir/Music/ \
		/usr/share/myir/Capture/ \
		/usr/share/myir/Video/ \
                /lib/systemd/system/ \
            "

#For dev packages only
INSANE_SKIP_${PN}-dev = "ldflags"
INSANE_SKIP_${PN} = "${ERROR_QA} ${WARN_QA}"
SYSTEMD_SERVICE_${PN} = "myir.service quit-weston.service pnsn.service"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"
