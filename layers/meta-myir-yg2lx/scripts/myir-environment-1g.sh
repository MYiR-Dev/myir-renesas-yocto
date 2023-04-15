BUID=$(pwd)/layers/meta-myir-yg2lx/docs/template/conf/myir-yg2lx-1g/
source $(pwd)/layers/poky/oe-init-build-env
cp $BUID/*  $BUILDDIR/conf
cat $BUILDDIR/conf/conf-notes.txt
sync
