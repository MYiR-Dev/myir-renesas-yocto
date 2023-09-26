require u-boot-common_${PV}.inc
require u-boot.inc

DEPENDS += "bc-native dtc-native"

#UBOOT_URL = "git://github.com/renesas-rz/renesas-u-boot-cip.git"
#BRANCH = "v2021.10/rz"

#SRC_URI = "${UBOOT_URL};branch=${BRANCH}"
#SRCREV = "711334a179f32dee4ea4c71cfb3cc1408ac65535"
#PV = "v2021.10+git${SRCPV}"

#UBOOT_URL = "git:////home/hjx/renesas/rzg2ul/sources/v3.0.1/source/myir-renesas-uboot;branch=${BRANCH};protocol=file"
#BRANCH = "develop-rzg2l-v2021.10"

#SRC_URI = "${UBOOT_URL};branch=${BRANCH}"
#SRCREV = "1af0ade469912fea9ff4452e4830432b0c2b6e41"
#PV = "v2021.10+git${SRCPV}"
