# For RZ/G2L (L/LC/V2L)
PREFERRED_PROVIDER_virtual/libgles1_rzg2l = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'mali-library', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/libgles2_rzg2l = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'mali-library', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/egl_rzg2l = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'mali-library', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/libgbm_rzg2l = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'mali-library', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/libopencl_rzg2l ?= "${@bb.utils.contains('COMBINED_FEATURES', 'opencl', 'mali-library', '', d)}"
MULTI_PROVIDER_WHITELIST_append_rzg2l = " virtual/libgbm "

IMAGE_INSTALL_append_rzg2l = " ${@bb.utils.contains('COMBINED_FEATURES', 'opencl', 'libopencl', '', d)}"

BBMASK_append_rzg2l = " recipes-graphics/powervr "
