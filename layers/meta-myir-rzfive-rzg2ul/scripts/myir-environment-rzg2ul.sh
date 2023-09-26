BUID=$(pwd)/layers/meta-myir-rzfive-rzg2ul/docs/template/conf/myir-rzg2ul
source $(pwd)/layers/poky/oe-init-build-env
cp $BUID/*  $BUILDDIR/conf
#if [ -f $BUILDDIR/conf/conf-notes.txt ]; then
	cat $BUILDDIR/conf/conf-notes.txt
sync

