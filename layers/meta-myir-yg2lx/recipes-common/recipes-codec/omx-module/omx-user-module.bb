DESCRIPTION = "OMX Media Components RZG2"
LICENSE = "CLOSED"
require include/rzg2-modules-common.inc
require omx-package-rzg2l.inc

COMPATIBLE_MACHINE_rzg2l = "(r9a07g044l|r9a07g054l)"
COMPATIBLE_MACHINE_rzg2h = "(r8a774a1|r8a774b1|r8a774c0|r8a774e1)"

DEPENDS = " \
    kernel-module-mmngr mmngr-user-module \
    vspmif-user-module kernel-module-vspmif \
    kernel-module-vspm \
"

DEPENDS_append_rzg2h = " kernel-module-vsp2driver"

# Task Control. Compile is not performed when not installing OMX Video and Audio Libs.
# Note) dummy-omx-user-module.inc does not exist.
INCLUDE_FILE = '${@oe.utils.conditional("USE_OMX_COMMON", "1", "dummy", "deltask", d )}'
include ${INCLUDE_FILE}-omx-user-module.inc

DEPENDS += '${@oe.utils.conditional("USE_VIDEO_OMX", "1", "kernel-module-uvcs-drv", "", d )}'

inherit autotools

includedir = "${RENESAS_DATADIR}/include"
CFLAGS += " -I${STAGING_DIR_HOST}${RENESAS_DATADIR}/include"
PACKAGE_ARCH = "${MACHINE_ARCH}"

OMX_EVA_PREFIX = '${@oe.utils.conditional("USE_OMX_EVA_PKG", "1", "EVA", "", d )}'

# SRC file name
SRC_URI_OMX = '${@oe.utils.conditional("USE_OMX_COMMON", "1", "file://${OMX_EVA_PREFIX}RTM0AC0000XCMCTL30SL41C.tar.bz2;unpack=0", "", d )}'
SRC_URI_VCMND = '${@oe.utils.conditional("USE_VIDEO_DEC", "1", "file://${OMX_EVA_PREFIX}RTM0AC0000XVCMND30SL41C.tar.bz2;unpack=0", "", d )}'
SRC_URI_VCMNE = '${@oe.utils.conditional("USE_VIDEO_ENC", "1", "file://${OMX_EVA_PREFIX}RTM0AC0000XVCMNE30SL41C.tar.bz2;unpack=0", "", d )}'
SRC_URI_H264D = '${@oe.utils.conditional("USE_H264D_OMX", "1", "file://${OMX_EVA_PREFIX}RTM0AC0000XV264D30SL41C.tar.bz2", "", d )}'
SRC_URI_H264E = '${@oe.utils.conditional("USE_H264E_OMX", "1", "file://${OMX_EVA_PREFIX}RTM0AC0000XV264E30SL41C.tar.bz2", "", d )}'

SRC_URI_H265D = '${@oe.utils.conditional("USE_H265D_OMX", "1", "file://RTM0AC0000XV265D30SL41C.tar.bz2", "", d )}'
SRC_URI_DIVXD = '${@oe.utils.conditional("USE_DIVXD_OMX", "1", "file://RTM0AC0000XVDVXD30SL41C.tar.bz2", "", d )}'
SRC_URI_RVD = '${@oe.utils.conditional("USE_RVD_OMX", "1", "file://RTM0AC0000XVRLVD30SL41C.tar.bz2", "", d )}'
SRC_URI_VP8D = '${@oe.utils.conditional("USE_VP8D_OMX", "1", "file://RTM0AC0000XVVP8D30SL41C.tar.bz2", "", d )}'
SRC_URI_VP8E = '${@oe.utils.conditional("USE_VP8E_OMX", "1", "file://RTM0AC0000XVVP8E30SL41C.tar.bz2", "", d )}'
SRC_URI_VP9D = '${@oe.utils.conditional("USE_VP9D_OMX", "1", "file://RTM0AC0000XVVP9D30SL41C.tar.bz2", "", d )}'
SRC_URI_ACMND = '${@oe.utils.conditional("USE_AUDIO_OMX", "1", "file://RTM0AC0000XACMND30SL41C.tar.gz", "", d )}'
SRC_URI_AACPV2 = '${@oe.utils.conditional("USE_AACPV2D_OMX", "1", "file://RTM0AC0000XAAAPD30SL41C.tar.gz", "", d )}'
SRC_URI_MP3 = '${@oe.utils.conditional("USE_MP3D_OMX", "1", "file://RTM0AC0000XAMP3D30SL41C.tar.gz", "", d )}'
SRC_URI_WMA = '${@oe.utils.conditional("USE_WMAD_OMX", "1", "file://RTM0AC0000XAWMAD30SL41C.tar.gz", "", d )}'
SRC_URI_DDD = '${@oe.utils.conditional("USE_DDD_OMX", "1", "file://RTM0AC0000XADD5D30SL41C.tar.gz", "", d )}'
SRC_URI_AACPV2MZ = '${@oe.utils.conditional("USE_AACPV2_MDW", "1", "file://RTM0AC0000ADAAPMZ1SL41C.tar.gz", "", d )}'
SRC_URI_MP3MZ = '${@oe.utils.conditional("USE_MP3_MDW", "1", "file://RTM0AC0000ADMP3MZ1SL41C.tar.gz", "", d )}'
SRC_URI_WMAMZ = '${@oe.utils.conditional("USE_WMA_MDW", "1", "file://RTM0AC0000ADWMAMZ1SL41C.tar.gz", "", d )}'
SRC_URI_DDMZ = '${@oe.utils.conditional("USE_DD_MDW", "1", "file://RTM0AC0000ADDD5MZ1SL41C.tar.gz", "", d )}'

SRC_URI_rzg2h = " \
	${SRC_URI_OMX} \
	${SRC_URI_VCMND} \
	${SRC_URI_VCMNE} \
	${SRC_URI_H264D} \
	${SRC_URI_H264E} \
	${SRC_URI_H265D} \
	${SRC_URI_DIVXD} \
	${SRC_URI_RVD} \
	${SRC_URI_ACMND} \
	${SRC_URI_AACPV2} \
	${SRC_URI_MP3} \
	${SRC_URI_WMA} \
	${SRC_URI_DDD} \
	${SRC_URI_AACPV2MZ} \
	${SRC_URI_MP3MZ} \
	${SRC_URI_WMAMZ} \
	${SRC_URI_DDMZ} \
	${SRC_URI_VP8D} \
	${SRC_URI_VP8E} \
	${SRC_URI_VP9D} \
"

SRC_URI_rzg2l = " \
	file://${OMX_PKG_TAR} "

# SRC directory name
OMX_COMMON_SRC_rzg2l = '${@oe.utils.conditional("USE_OMX_COMMON", "1", "${OMX_EVA_PREFIX}omx_common_lib_package", "", d )}'
OMX_VIDEO_DEC_COMMON_SRC_rzg2l = '${@oe.utils.conditional("USE_VIDEO_DEC", "1", "${OMX_EVA_PREFIX}video_dec_common_package", "", d )}'
OMX_VIDEO_ENC_COMMON_SRC_rzg2l = '${@oe.utils.conditional("USE_VIDEO_ENC", "1", "${OMX_EVA_PREFIX}video_enc_common_package", "", d )}'
OMX_H264_DEC_SRC_rzg2l = '${@oe.utils.conditional("USE_H264D_OMX", "1", "${OMX_EVA_PREFIX}h264_decoder_package", "", d )}'
OMX_H264_ENC_SRC_rzg2l = '${@oe.utils.conditional("USE_H264E_OMX", "1", "${OMX_EVA_PREFIX}h264_encoder_package", "", d )}'

OMX_COMMON_SRC_rzg2h = '${@oe.utils.conditional("USE_OMX_COMMON", "1", "${OMX_EVA_PREFIX}RTM0AC0000XCMCTL30SL41C", "", d )}'
OMX_VIDEO_DEC_COMMON_SRC_rzg2h = '${@oe.utils.conditional("USE_VIDEO_DEC", "1", "${OMX_EVA_PREFIX}RTM0AC0000XVCMND30SL41C", "", d )}'
OMX_VIDEO_ENC_COMMON_SRC_rzg2h = '${@oe.utils.conditional("USE_VIDEO_ENC", "1", "${OMX_EVA_PREFIX}RTM0AC0000XVCMNE30SL41C", "", d )}'
OMX_H264_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_H264D_OMX", "1", "${OMX_EVA_PREFIX}RTM0AC0000XV264D30SL41C", "", d )}'
OMX_H264_ENC_SRC_rzg2h = '${@oe.utils.conditional("USE_H264E_OMX", "1", "${OMX_EVA_PREFIX}RTM0AC0000XV264E30SL41C", "", d )}'
OMX_H265_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_H265D_OMX", "1", "RTM0AC0000XV265D30SL41C", "", d )}'
OMX_DIVX_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_DIVXD_OMX", "1", "RTM0AC0000XVDVXD30SL41C", "", d )}'
OMX_RV_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_RVD_OMX", "1", "RTM0AC0000XVRLVD30SL41C", "", d )}'
OMX_VP8_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_VP8D_OMX", "1", "RTM0AC0000XVVP8D30SL41C", "", d )}'
OMX_VP8_ENC_SRC_rzg2h = '${@oe.utils.conditional("USE_VP8E_OMX", "1", "RTM0AC0000XVVP8E30SL41C", "", d )}'
OMX_VP9_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_VP9D_OMX", "1", "RTM0AC0000XVVP9D30SL41C", "", d )}'


OMX_VIDEO_SRC_LIST = " \
    ${OMX_COMMON_SRC} \
    ${OMX_VIDEO_DEC_COMMON_SRC} \
    ${OMX_VIDEO_ENC_COMMON_SRC} \
    ${OMX_H264_DEC_SRC} \
    ${OMX_H264_ENC_SRC} \
"

OMX_VIDEO_SRC_LIST_append_rzg2h = " \
    ${OMX_H265_DEC_SRC} \
    ${OMX_DIVX_DEC_SRC} \
    ${OMX_RV_DEC_SRC} \
    ${OMX_VP8_DEC_SRC} \
    ${OMX_VP8_ENC_SRC} \
    ${OMX_VP9_DEC_SRC} \
"

AACPV2_MIDDLEWARE_SRC_rzg2h = "RTM0AC0000ADAAPMZ1SL41C"
MP3_MIDDLEWARE_SRC_rzg2h = "RTM0AC0000ADMP3MZ1SL41C"
WMA_MIDDLEWARE_SRC_rzg2h = "RTM0AC0000ADWMAMZ1SL41C"
DD_MIDDLEWARE_SRC_rzg2h = "RTM0AC0000ADDD5MZ1SL41C"

OMX_AUDIO_COMMON_SRC_rzg2h = '${@oe.utils.conditional("USE_AUDIO_OMX", "1", "RTM0AC0000XACMND30SL41C", "", d )}'
OMX_AACPV2_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_AACPV2D_OMX", "1", "RTM0AC0000XAAAPD30SL41C", "", d )}'
OMX_MP3_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_MP3D_OMX", "1", "RTM0AC0000XAMP3D30SL41C", "", d )}'
OMX_WMA_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_WMAD_OMX", "1", "RTM0AC0000XAWMAD30SL41C", "", d )}'
OMX_DD_DEC_SRC_rzg2h = '${@oe.utils.conditional("USE_DDD_OMX", "1", "RTM0AC0000XADD5D30SL41C", "", d )}'

OMX_AUDIO_SRC_LIST_rzg2h = " \
    ${OMX_AUDIO_COMMON_SRC} \
    ${OMX_AACPV2_DEC_SRC} \
    ${OMX_MP3_DEC_SRC} \
    ${OMX_WMA_DEC_SRC} \
    ${OMX_DD_DEC_SRC} \
"

S_rzg2h = "${WORKDIR}/omx/"
S_rzg2l = "${WORKDIR}/${OMX_PKG}/"

do_fetch[file-checksums] = ""
do_compile[noexec] = "1"
do_package_qa[noexec] = "1"

# Create ${S} directory
do_unpack_prepend() {
    os.system("install -d ${S}")
}

do_unpack_append_rzg2l() {
    bb.build.exec_func('do_unpack_omx_module', d)
}
do_unpack_omx_module() {
    cd ${S}
    for omxmc in ${OMX_VIDEO_SRC_LIST}
    do
	tar xf ${S}/${omxmc}.tar.bz2
    done
}

do_unpack_append_rzg2h() {
    bb.build.exec_func('setup_build_tree', d)
}

setup_omx_package(){
    tar xf ${WORKDIR}/omx_pkg*.tar.bz2 --strip=2 -C ${WORKDIR}
    tar xf ${WORKDIR}/h264_decoder_package.tar.bz2 -C ${WORKDIR}
    tar xf ${WORKDIR}/h264_encoder_package.tar.bz2 -C ${WORKDIR}
}

setup_build_tree() {
    for omxmc in ${OMX_COMMON_SRC} ${OMX_VIDEO_DEC_COMMON_SRC} ${OMX_VIDEO_ENC_COMMON_SRC}
    do
        tar xf ${WORKDIR}/${omxmc}.tar.bz2 -C ${WORKDIR}
        tar xf ${WORKDIR}/${omxmc}.tar.bz2 -C ${S} ${omxmc}/src --strip=2
        tar xf ${WORKDIR}/${omxmc}.tar.bz2 -C ${S} ${omxmc}/include --strip=1
    done
}

B = "${S}"

EXTRA_OECONF = "OMXR_DEFAULT_CONFIG_FILE_NAME=${sysconfdir}/omxr/omxr_config_base.txt"

do_configure_rzg2h() {
    export uvcsdrv_dir="${INCSHARED}"
    chmod u+x autogen.sh
    ./autogen.sh
    oe_runconf
}

do_install_omx_video_prepend_rzg2h() {
    cd ${D}/${libdir}
    for omxmc in ${OMX_VIDEO_SRC_LIST}
    do
        src="${WORKDIR}/${omxmc}"
        install -m 755 ${src}/lib/lib*.so.* ${D}/${libdir}
        install -m 644 ${src}/include/*.h ${D}/${includedir}
        install -m 644 ${src}/config/*.txt ${D}/${sysconfdir}/omxr
    done
}

do_install_omx_video_prepend_rzg2l() {
    cd ${S}
    for omxmc in ${OMX_VIDEO_SRC_LIST}
    do
        install -m 755 ${S}/${omxmc}/lib/lib*.so.* ${D}/${libdir}
        install -m 644 ${S}/${omxmc}/include/*.h ${D}/${includedir}
        install -m 644 ${S}/${omxmc}/config/*.txt ${D}/${sysconfdir}/omxr
    done
}

do_install_omx_video() {
    cd ${D}/${libdir}
    if [ "X${USE_OMX_COMMON}" = "X1" ] ; then
        ln -s libomxr_core.so.3.0.0 libomxr_core.so.3
        ln -s libomxr_core.so.3 libomxr_core.so

        ln -s libomxr_mc_cmn.so.3.0.0 libomxr_mc_cmn.so.3
        ln -s libomxr_mc_cmn.so.3 libomxr_mc_cmn.so
    fi

    if [ "X${USE_VIDEO_OMX}" = "X1" ] ; then
        ln -s libomxr_mc_vcmn.so.3.0.0 libomxr_mc_vcmn.so.3
        ln -s libomxr_mc_vcmn.so.3 libomxr_mc_vcmn.so
    fi

    if [ "X${USE_VIDEO_DEC}" = "X1" ] ; then
        ln -s libomxr_mc_vdcmn.so.3.0.0 libomxr_mc_vdcmn.so.3
        ln -s libomxr_mc_vdcmn.so.3 libomxr_mc_vdcmn.so

        ln -s libuvcs_dec.so.3.0.0 libuvcs_dec.so.3
        ln -s libuvcs_dec.so.3 libuvcs_dec.so
    fi

    if [ "X${USE_VIDEO_ENC}" = "X1" ] ; then
        ln -s libomxr_mc_vecmn.so.3.0.0 libomxr_mc_vecmn.so.3
        ln -s libomxr_mc_vecmn.so.3 libomxr_mc_vecmn.so

        ln -s libuvcs_enc.so.3.0.0 libuvcs_enc.so.3
        ln -s libuvcs_enc.so.3 libuvcs_enc.so
    fi

    if [ "X${USE_H264D_OMX}" = "X1" ]; then
        ln -s libomxr_mc_h264d.so.3.0.0 libomxr_mc_h264d.so.3
        ln -s libomxr_mc_h264d.so.3 libomxr_mc_h264d.so

        ln -s libuvcs_avcd.so.3.0.0 libuvcs_avcd.so.3
        ln -s libuvcs_avcd.so.3 libuvcs_avcd.so
    fi

    if [ "X${USE_H264E_OMX}" = "X1" ]; then
        ln -s libomxr_mc_h264e.so.3.0.0 libomxr_mc_h264e.so.3
        ln -s libomxr_mc_h264e.so.3 libomxr_mc_h264e.so

        ln -s libuvcs_avce.so.3.0.0 libuvcs_avce.so.3
        ln -s libuvcs_avce.so.3 libuvcs_avce.so
    fi

}

do_install_omx_video_append_rzg2l() {
    if [ "X${USE_OMX_COMMON}" = "X1" ] ; then
        ln -s libomxr_uvcs_udf.so.0.0.0 libomxr_uvcs_udf.so
        ln -s libomxr_videoconverter.so.3.0.0 libomxr_videoconverter.so.3
        ln -s libomxr_videoconverter.so.3 libomxr_videoconverter.so
        ln -s libomxr_utility.so.3.0.0 libomxr_utility.so.3
        ln -s libomxr_utility.so.3 libomxr_utility.so
    fi

    if [ "X${USE_VIDEO_DEC}" = "X1" ] ; then
        ln -s libomxr_cnvpisu.so.0.0.0 libomxr_cnvpisu.so
    fi
}

do_install_omx_video_append_rzg2h() {
    if [ "X${USE_H265D_OMX}" = "X1" ]; then
        ln -s libomxr_mc_hevd.so.3.0.0 libomxr_mc_hevd.so.3
        ln -s libomxr_mc_hevd.so.3 libomxr_mc_hevd.so

        ln -s libuvcs_hevd.so.3.0.0 libuvcs_hevd.so.3
        ln -s libuvcs_hevd.so.3 libuvcs_hevd.so
    fi

    if [ "X${USE_DIVXD_OMX}" = "X1" ]; then
        ln -s libomxr_mc_divxd.so.3.0.0 libomxr_mc_divxd.so.3
        ln -s libomxr_mc_divxd.so.3 libomxr_mc_divxd.so

        ln -s libuvcs_dvxd.so.3.0.0 libuvcs_dvxd.so.3
        ln -s libuvcs_dvxd.so.3 libuvcs_dvxd.so
    fi

    if [ "X${USE_RVD_OMX}" = "X1" ]; then
        ln -s libomxr_mc_rlvd.so.3.0.0 libomxr_mc_rlvd.so.3
        ln -s libomxr_mc_rlvd.so.3 libomxr_mc_rlvd.so

        ln -s libuvcs_rlvd.so.3.0.0 libuvcs_rlvd.so.3
        ln -s libuvcs_rlvd.so.3 libuvcs_rlvd.so
    fi

    if [ "X${USE_VP8D_OMX}" = "X1" ]; then
        ln -s libomxr_mc_vp8d.so.3.0.0 libomxr_mc_vp8d.so.3
        ln -s libomxr_mc_vp8d.so.3 libomxr_mc_vp8d.so

        ln -s libuvcs_vp8d.so.3.0.0 libuvcs_vp8d.so.3
        ln -s libuvcs_vp8d.so.3 libuvcs_vp8d.so
    fi

    if [ "X${USE_VP8E_OMX}" = "X1" ]; then
        ln -s libomxr_mc_vp8e.so.3.0.0 libomxr_mc_vp8e.so.3
        ln -s libomxr_mc_vp8e.so.3 libomxr_mc_vp8e.so

        ln -s libuvcs_vp8e.so.3.0.0 libuvcs_vp8e.so.3
        ln -s libuvcs_vp8e.so.3 libuvcs_vp8e.so
    fi

    if [ "X${USE_VP9D_OMX}" = "X1" ]; then
        ln -s libomxr_mc_vp9d.so.3.0.0 libomxr_mc_vp9d.so.3
        ln -s libomxr_mc_vp9d.so.3 libomxr_mc_vp9d.so

        ln -s libuvcs_vp9d.so.3.0.0 libuvcs_vp9d.so.3
        ln -s libuvcs_vp9d.so.3 libuvcs_vp9d.so
    fi
}

do_install_audio_middleware() {
	:
}

do_install_audio_middleware_append_rzg2h() {
    cd ${D}/${libdir}

    if [ "X${USE_AACPV2_MDW}" = "X1" ]; then
        install -m 755 ${WORKDIR}/${AACPV2_MIDDLEWARE_SRC}/lib/libRSACPDLA_L.so.2.0 \
            ${D}/${libdir}
        install -m 644 ${WORKDIR}/${AACPV2_MIDDLEWARE_SRC}/include/*.h ${D}/${includedir}

        ln -s libRSACPDLA_L.so.2.0 libRSACPDLA_L.so.2
        ln -s libRSACPDLA_L.so.2 libRSACPDLA_L.so
    fi

    if [ "X${USE_MP3_MDW}" = "X1" ]; then
        install -m 755 ${WORKDIR}/${MP3_MIDDLEWARE_SRC}/lib/libMP3DLA_L.so.2.0 \
            ${D}/${libdir}
        install -m 644 ${WORKDIR}/${MP3_MIDDLEWARE_SRC}/include/*.h ${D}/${includedir}

        ln -s libMP3DLA_L.so.2.0 libMP3DLA_L.so.2
        ln -s libMP3DLA_L.so.2 libMP3DLA_L.so
    fi

    if [ "X${USE_WMA_MDW}" = "X1" ]; then
        install -m 755 ${WORKDIR}/${WMA_MIDDLEWARE_SRC}/lib/libWMASTDLA_L.so.2.0 \
            ${D}/${libdir}
        install -m 644 ${WORKDIR}/${WMA_MIDDLEWARE_SRC}/include/*.h ${D}/${includedir}

        ln -s libWMASTDLA_L.so.2.0 libWMASTDLA_L.so.2
        ln -s libWMASTDLA_L.so.2 libWMASTDLA_L.so
    fi

    if [ "X${USE_DD_MDW}" = "X1" ]; then
        install -m 755 ${WORKDIR}/${DD_MIDDLEWARE_SRC}/lib/libRSDACDLA_L.so.2.0 \
            ${D}/${libdir}
        install -m 644 ${WORKDIR}/${DD_MIDDLEWARE_SRC}/include/*.h ${D}/${includedir}

        ln -s libRSDACDLA_L.so.2.0 libRSDACDLA_L.so.2
        ln -s libRSDACDLA_L.so.2 libRSDACDLA_L.so
    fi
}

do_install_omx_audio() {
	:
}

do_install_omx_audio_append_rzg2h() {
    cd ${D}/${libdir}
    for omxmc in ${OMX_AUDIO_SRC_LIST}
    do
        src="${WORKDIR}/${omxmc}/"
        install -m 755 ${src}/lib/lib*.so.* ${D}/${libdir}
        if [ -d ${src}/include ]; then
            install -m 644 ${src}/include/*.h ${D}/${includedir}
        fi
        install -m 644 ${src}/config/*.txt ${D}/${sysconfdir}/omxr
    done

    if [ "X${USE_AUDIO_OMX}" = "X1" ]; then
        ln -s libomxr_mc_acmn.so.3.0.0 libomxr_mc_acmn.so.3
        ln -s libomxr_mc_acmn.so.3 libomxr_mc_acmn.so
    fi

    if [ "X${USE_AACPV2D_OMX}" = "X1" ]; then
        ln -s libomxr_mc_aapd.so.3.0.0 libomxr_mc_aapd.so.3
        ln -s libomxr_mc_aapd.so.3 libomxr_mc_aapd.so
    fi

    if [ "X${USE_MP3D_OMX}" = "X1" ]; then
        ln -s libomxr_mc_mp3d.so.3.0.0 libomxr_mc_mp3d.so.3
        ln -s libomxr_mc_mp3d.so.3 libomxr_mc_mp3d.so
    fi

    if [ "X${USE_WMAD_OMX}" = "X1" ]; then
        ln -s libomxr_mc_wmad.so.3.0.0 libomxr_mc_wmad.so.3
        ln -s libomxr_mc_wmad.so.3 libomxr_mc_wmad.so
    fi

    if [ "X${USE_DDD_OMX}" = "X1" ]; then
        ln -s libomxr_mc_ddd.so.3.0.0 libomxr_mc_ddd.so.3
        ln -s libomxr_mc_ddd.so.3 libomxr_mc_ddd.so
    fi
}

do_install_prepend_rzg2h() {
    if [ "X${USE_OMX_COMMON}" = "X1" ]; then
        oe_runmake 'DESTDIR=${D}' install
        # Info dir listing isn't interesting at this point so remove it if it exists.
        if [ -e "${D}/${infodir}/dir" ]; then
            rm -f ${D}/${infodir}/dir
        fi
    fi
}

do_install() {
    # Create destination directory
    install -d ${D}/${libdir}
    install -d ${D}/${includedir}
    if [ "X${USE_OMX_COMMON}" = "X1" ]; then
        install -d ${D}/${sysconfdir}/omxr
    fi

    # Copy omx video library
    do_install_omx_video
}

do_install_append_rzg2h() {
    # Copy audio middleware library
    do_install_audio_middleware
    # Copy omx audio library
    do_install_omx_audio
}

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} += " \
    ${libdir}/*.so \
"

FILES_${PN}-dev = " \
    ${includedir} \
    ${libdir}/*.la \
"

RDEPENDS_${PN} += "mmngr-user-module vspmif-user-module"

#To avoid already-stripped errors and not stripped libs from packages
INSANE_SKIP_${PN} += "already-stripped"

# Skip debug split and strip of do_package()
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
