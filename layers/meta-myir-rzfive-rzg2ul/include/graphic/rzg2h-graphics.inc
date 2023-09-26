# For RZ/G2 (H/M/N/E)
PREFERRED_PROVIDER_virtual/libgles1_rzg2h = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', '', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/libgles2_rzg2h = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'gles-user-module', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/egl_rzg2h = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'libegl', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/libgbm_rzg2h = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'libgbm', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/libgbm_rzg2h = "${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'libgbm', 'mesa', d)}"

BBMASK_append_rzg2h = " recipes-graphics/mali|mesa-gl"
BBMASK_append_rzg2h = " ${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'mesa-gl', 'gles-user-module|kernel-module-gles|wayland-kms|libgbm|libegl', d)}"
BBMASK_append_rzg2h = " ${@bb.utils.contains('COMBINED_FEATURES', 'opengles', 'mesa', 'gles-user-module|kernel-module-gles|wayland-kms|libgbm|libegl', d)}"

