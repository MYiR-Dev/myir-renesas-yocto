BUID=$(pwd)/layers/meta-myir-rzfive-rzg2ul/docs/template/conf/myir-rzfive
source $(pwd)/layers/poky/oe-init-build-env
cp $BUID/*  $BUILDDIR/conf
cat $BUILDDIR/conf/conf-notes.txt
sync
