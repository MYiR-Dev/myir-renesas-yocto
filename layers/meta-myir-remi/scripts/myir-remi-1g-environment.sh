BUID=$(pwd)/layers/meta-myir-remi/docs/template/conf/myir-remi-1g/
source $(pwd)/layers/poky/oe-init-build-env
cp $BUID/*  $BUILDDIR/conf
cat $BUILDDIR/conf/conf-notes.txt
sync
