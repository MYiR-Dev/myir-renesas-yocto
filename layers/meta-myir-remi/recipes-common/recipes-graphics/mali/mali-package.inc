PV="r38"
PR="p0"

PRODUCT_PKG="mali_pkg_product_v1.3.tar.gz"
EVAL_PKG="mali_pkg_eval_v1.3.tar.gz"

HAVE_PRODUCT="${@bb.os.path.isfile("${THISDIR}/files/${PRODUCT_PKG}")}"
MALI_PKG="${@oe.utils.conditional("HAVE_PRODUCT", "True", "${PRODUCT_PKG}", "${EVAL_PKG}", d)}"
