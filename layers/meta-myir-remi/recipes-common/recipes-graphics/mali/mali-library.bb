DESCRIPTION = "Mali EGL/GLES User Module"
LICENSE = "CLOSED"

require include/rzg2-path-common.inc
require mali-package.inc

COMPATIBLE_MACHINE = "(r9a07g044l|r9a07g054l|r9a07g044c)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/mali_pkg/mali_um"

SRC_URI = "\
        file://${MALI_PKG} \
	"

inherit systemd pkgconfig

DEPENDS += "patchelf-native"

do_fetch[file-checksums] = ""
do_populate_lic[noexec] = "1"
do_compile[noexec] = "1"
do_package_qa[noexec] = "1"

# Only need GBM header from mesa when using wayland feature
do_populate_sysroot[depends] += "${@bb.utils.contains("DISTRO_FEATURES", "wayland", "virtual/mesa:do_populate_sysroot", "", d)}"

USE_GLES = "${@'1' if '${USE_RENESAS_GLES}' == '1' else '0'}"
USE_CL = "${@'1' if '${USE_RENESAS_OPENCL}' == '1' else '0'}"
USE_WAYLAND = "${@bb.utils.contains("DISTRO_FEATURES", "wayland", "1", "0", d)}"

# Default backend shoule be wayland if possible
MALI_BACKEND_DEFAULT ?= "${@'wayland' if '${USE_WAYLAND}' == '1' else 'fbdev'}"

LIB_MALI = "libmali.so"

do_unpack_append() {
    bb.build.exec_func('do_unpack_mali_um', d)
}

do_unpack_mali_um() {
	tar xfz ${WORKDIR}/mali_pkg/mali_um*.tar.gz -C ${WORKDIR}/mali_pkg/
}

do_install() {
	if [ "${USE_CL}" = "1" ]; then
		if [ "${USE_GLES}" = "1" ]; then
			PKG_PREFIX="CL_GLES"
		else
			PKG_PREFIX="CL"
		fi
	else
		# Always support GLES when none of using OpenCL
		PKG_PREFIX="GLES"
	fi

	# Install header files
	install -d ${D}${includedir}/EGL
	install -d ${D}${includedir}/KHR
	install -d ${D}${includedir}/GLES
	install -d ${D}${includedir}/GLES2
	install -d ${D}${includedir}/GLES3
	install -d ${D}${includedir}/CL
	install -m 644 ${S}/usr/include/CL/* ${D}/${includedir}/CL/
	install -m 644 ${S}/usr/include/EGL/*.h ${D}/${includedir}/EGL/
	install -m 644 ${S}/usr/include/KHR/*.h ${D}/${includedir}/KHR/
	install -m 644 ${S}/usr/include/GLES/*.h ${D}/${includedir}/GLES/
	install -m 644 ${S}/usr/include/GLES2/*.h ${D}/${includedir}/GLES2/
	install -m 644 ${S}/usr/include/GLES3/*.h ${D}/${includedir}/GLES3/

	# Install pre-built binaries
	install -d ${D}${libdir}
	install -m 755 ${S}/usr/lib/*.so ${D}${libdir}/

	# Install default, always having fbdev support
	install -d ${D}${libdir}/mali_fbdev
	install -m 755 ${S}/usr/lib/${PKG_PREFIX}/mali_fbdev/${LIB_MALI} ${D}${libdir}/mali_fbdev/${LIB_MALI}

	# Create symbolic link
	cd ${D}${libdir}
	ln -fs libEGL.so	libEGL.so.1
	ln -fs libgbm.so	libgbm.so.1
	ln -fs libGLESv1_CM.so	libGLESv1_CM.so.1
	ln -fs libGLESv2.so	libGLESv2.so.2
	ln -fs libOpenCL.so	libOpenCL.so.2
	ln -fs libwayland-egl.so libwayland-egl.so.1
	ln -fs mali_fbdev/${LIB_MALI} ${D}${libdir}/${LIB_MALI}

	if [ "${USE_GLES}" = "1" ]; then
		if [ "${USE_WAYLAND}" = "1" ]; then
			install -d ${D}${libdir}/mali_wayland
			install -m 755 ${S}/usr/lib/${PKG_PREFIX}/mali_wayland/${LIB_MALI} ${D}${libdir}/mali_wayland/${LIB_MALI}

			if [ "${MALI_BACKEND_DEFAULT}" = "wayland" ]; then
				cd ${D}${libdir}
				ln -fs mali_wayland/${LIB_MALI} ${D}${libdir}/${LIB_MALI}
				cd -
			fi
		fi
	fi


	# Mali prebuilt binares do not have DT_SONAME, so we must set them manually.
	# This step help the package can be used in rdepend package.
	patchelf --set-soname libEGL.so ${D}${libdir}/libEGL.so
	patchelf --set-soname libgbm.so ${D}${libdir}/libgbm.so
	patchelf --set-soname libGLESv1_CM.so ${D}${libdir}/libGLESv1_CM.so
	patchelf --set-soname libGLESv2.so ${D}${libdir}/libGLESv2.so
	patchelf --set-soname libOpenCL.so ${D}${libdir}/libOpenCL.so
	patchelf --set-soname libwayland-egl.so ${D}${libdir}/libwayland-egl.so

	# Install pkgconfig
	install -d ${D}${libdir}/pkgconfig
	install -m 644 ${S}/usr/lib/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

PACKAGES = "\
        ${PN} \
	mali-opencl \
	mali-gles \
	mali-opencl-dev \
	mali-gles-dev \
"

PROVIDES += " virtual/libegl virtual/egl virtual/libgles1 virtual/libgles2 virtual/libgbm virtual/libopencl"

RPROVIDES_${PN} += " \
	libegl \
	libegl1 \
	libgles1 \
	libglesv1-cm \
	libgles2 \
	libgbm \
	libopencl \
"

RDEPENDS_${PN} = " \
	kernel-module-mali \
	${@bb.utils.contains("DISTRO_FEATURES", "wayland", " wayland ", "", d)} \
	${@oe.utils.conditional("USE_RENESAS_GLES", "1", " mali-gles " , "", d)} \
	${@oe.utils.conditional("USE_RENESAS_OPENCL", "1", " mali-opencl " , "", d)} \
"

FILES_${PN} = " \
	${libdir}/libmali.so \
	${libdir}/mali*/libmali.so \
"
FILES_${PN}-dev = " "
FILES_mali-gles = " \
	${libdir}/libEGL.* \
	${libdir}/libGLESv1_CM.* \
	${libdir}/libGLESv2.* \
	${libdir}/libgbm.* \
	${libdir}/libwayland-egl.* \
"
FILES_mali-gles-dev = " \
	${includedir}/EGL/* \
	${includedir}/GLES/* \
	${includedir}/GLES2/* \
	${includedir}/GLES3/* \
	${includedir}/KHR/* \
	${libdir}/pkgconfig/egl.pc \
	${libdir}/pkgconfig/gbm.pc \
	${libdir}/pkgconfig/glesv1.pc \
	${libdir}/pkgconfig/glesv1_cm.pc \
	${libdir}/pkgconfig/glesv2.pc \
	${libdir}/pkgconfig/wayland-egl.pc \
"
FILES_mali-opencl = " \
	${libdir}/libOpenCL.* \
"
FILES_mali-opencl-dev = " \
	${includedir}/CL/* \
	${libdir}/pkgconfig/OpenCL.pc \
"

INSANE_SKIP_${PN} = "already-stripped"
