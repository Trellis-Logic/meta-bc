# This recipe is only used for 5.2.x branch. On latest, libyuv is built with the linphone SDK.

SUMMARY = "libyuv is an open source project that includes YUV scaling and conversion functionality"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=464282cfb405b005b9637f11103a7325"

PROVIDES = "libyuv"
DEPENDS = "libjpeg-turbo"

SRC_URI = "git://gitlab.linphone.org/BC/public/external/libyuv.git;protocol=https;branch=bc;"

S = "${WORKDIR}/git"

python () {
    d.setVar('SRCREV', "${AUTOREV}")
}

inherit cmake

EXTRA_OECMAKE = "-DENABLE_STATIC=ON"

FILES:${PN} += "/usr/share/YUV/*"
