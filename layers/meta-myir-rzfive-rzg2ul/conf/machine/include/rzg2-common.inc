EXT_GFX_BACKEND = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', '1', '0', d)}"

# 32BIT package install (default is disable)
# This variables can be used only in multilib.
USE_32BIT_PKGS ?= "0"
USE_32BIT_WAYLAND ?= "0"
USE_32BIT_MMP ?= "0"

MACHINE_FEATURES = ""

KERNEL_IMAGETYPE = "Image"
IMAGE_FSTYPES_append = " tar.bz2 ext4 "

SERIAL_CONSOLES = "115200;ttySC0"

# Configuration for ARM Trusted Firmware
EXTRA_IMAGEDEPENDS += " tf-a-myir"

# Add variable to Build Configuration in build log
BUILDCFG_VARS_append = " SOC_FAMILY"

# We must disable ptest when can not use GPLv3 License,
# ptest require many package, which have GPLv3 License,
DISTRO_FEATURES_remove += "${@bb.utils.contains("INCOMPATIBLE_LICENSE", "GPLv3", "ptest", "",d)}"

# Match version between native and target sysroot in meta-gplv2 when not using GPLv3.
PREFERRED_VERSION_gdbm-native ?= "${@bb.utils.contains("INCOMPATIBLE_LICENSE", "GPLv3", "1.8.3", "",d)}"

# u-boot
PREFERRED_VERSION_u-boot-myir = "v2021.10%"
EXTRA_IMAGEDEPENDS += " u-boot-myir"

DISTRO_FEATURES_append = " docker"

UBI_VOLNAME = "rootfs"
# The biggest NANDs on current modules are 256MB.
# This sums up to 2048 LEBs available for the ubifs (-c)
#MKUBIFS_ARGS = "-F -m 2048 -e 126976 -c 2048"
MKUBIFS_ARGS = "-F -m 2048 -e 126976 -c 4900"
UBINIZE_ARGS = "-m 2048 -p 128KiB -s 2048 -O 2048"
