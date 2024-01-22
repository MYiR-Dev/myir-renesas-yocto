BUID=$(pwd)/layers/meta-myir-remi/docs/template/conf/myir-remi/
source $(pwd)/layers/poky/oe-init-build-env
cp $BUID/*  $BUILDDIR/conf
#if [ -f $BUILDDIR/conf/conf-notes.txt ]; then
	cat $BUILDDIR/conf/conf-notes.txt
sync

