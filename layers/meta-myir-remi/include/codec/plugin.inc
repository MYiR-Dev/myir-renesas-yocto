USE_CODEC_DEC ?= "1"
USE_CODEC_ENC ?= "1"
USE_CODEC_DEC_H265 ?= "1"

USE_CODEC_DEC_r9a07g044c = "0"
USE_CODEC_ENC_r9a07g044c = "0"
USE_CODEC_DEC_r9a07g043u = "0"
USE_CODEC_ENC_r9a07g043u = "0"
USE_CODEC_DEC_H265_rzg2l = "0"

require omx-control.inc

DISTRO_FEATURES_append = "${@oe.utils.conditional('USE_CODEC_DEC', '1', 'h264dec_lib', '',d)}"
DISTRO_FEATURES_append_rzg2h = "${@oe.utils.conditional('USE_CODEC_DEC_H265', '1', 'h265dec_lib', '',d)}"
DISTRO_FEATURES_append = "${@oe.utils.conditional('USE_CODEC_ENC', '1', 'h264enc_lib', '',d)}"

LICENSE_FLAGS_WHITELIST_append=" \
	${@bb.utils.contains("USE_VIDEO_OMX", "1", "commercial_gstreamer1.0-omx", "", d )} \
	${@bb.utils.contains("USE_VIDEO_OMX", "1", "commercial_lib32-libomxil", "", d )} \
	${@bb.utils.contains("USE_VIDEO_OMX", "1", "commercial_libomxil", "", d )} \
"

IMAGE_INSTALL_append=" \
	${@bb.utils.contains("USE_VIDEO_OMX", "1", " kernel-module-uvcs-drv ", "", d)} \
	${@bb.utils.contains("USE_VIDEO_OMX", "1", " omx-user-module ", "",d)} \
"
