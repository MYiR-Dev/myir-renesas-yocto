USE_OPENAMP ?= "1"

IMAGE_INSTALL_append = " \
	${@bb.utils.contains("USE_OPENAMP", "1", " libmetal ", "", d)} \
	${@bb.utils.contains("USE_OPENAMP", "1", " open-amp ", "", d)} \
	${@bb.utils.contains("USE_OPENAMP", "1", " rpmsg-sample ", "", d)} \
"
