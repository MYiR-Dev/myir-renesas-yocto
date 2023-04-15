RENESAS_GRAPHIC = "${@os.path.isdir("${LAYERDIR}/recipes-graphics/")}"
USE_RENESAS_GLES ?= "${@oe.utils.conditional("RENESAS_GRAPHIC", "True", '1', '0', d)}"
USE_RENESAS_OPENCL ?= "${@oe.utils.conditional("RENESAS_GRAPHIC", "True", '1', '0', d)}"

USE_RENESAS_GLES_r9a07g043u = "0"
USE_RENESAS_OPENCL_r9a07g043u = "0"

MULTI_PROVIDER_WHITELIST_append = " virtual/libegl virtual/egl virtual/libgles1 virtual/libgles2 "

# For RZ/G2 (H/M/N/E)
PREFERRED_PROVIDER_virtual/libgles1_rzg2h = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', '', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libgles2_rzg2h = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'gles-user-module', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/egl_rzg2h = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'libegl', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libgbm_rzg2h = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'libgbm', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libgbm_rzg2h = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'libgbm', 'mesa',d)}"

USE_GLES_WAYLAND = "${@'1' if '${USE_RENESAS_GLES}' == '1' and 'wayland' in '${DISTRO_FEATURES}' else '0'}"
BBMASK_append_rzg2h = " recipes-graphics/mali|mesa-gl"
BBMASK_append_rzg2h = " ${@oe.utils.conditional('USE_RENESAS_GLES', '0', 'gles-user-module|kernel-module-gles|wayland-kms|libgbm|libegl', 'mesa-gl',d)}"

# For RZ/G2L
PREFERRED_PROVIDER_virtual/libgles1_rzg2l = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'mali-library', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libgles2_rzg2l = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'mali-library', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/egl_rzg2l = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'mali-library', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libgbm_rzg2l = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'mali-library', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libopencl_rzg2l ?= "${@oe.utils.conditional('USE_RENESAS_OPENCL', '1', 'mali-library', '', d)}"
MULTI_PROVIDER_WHITELIST_append_rzg2l = " virtual/libgbm "

IMAGE_INSTALL_append_rzg2l = " ${@oe.utils.conditional('USE_RENESAS_OPENCL', '1', ' libopencl ', '', d)} "
BBMASK_append_rzg2l = " recipes-graphics/PowerVR "

# For RZ/V2L
PREFERRED_PROVIDER_virtual/libgles1_rzv2l = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'mali-library', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libgles2_rzv2l = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'mali-library', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/egl_rzv2l = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'mali-library', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libgbm_rzv2l = "${@oe.utils.conditional('USE_RENESAS_GLES', '1', 'mali-library', 'mesa',d)}"
PREFERRED_PROVIDER_virtual/libopencl_rzv2l ?= "${@oe.utils.conditional('USE_RENESAS_OPENCL', '1', 'mali-library', '', d)}"
MULTI_PROVIDER_WHITELIST_append_rzv2l = " virtual/libgbm "

IMAGE_INSTALL_append_rzv2l = " ${@oe.utils.conditional('USE_RENESAS_OPENCL', '1', ' libopencl ', '', d)} "
BBMASK_append_rzv2l = " recipes-graphics/PowerVR "
