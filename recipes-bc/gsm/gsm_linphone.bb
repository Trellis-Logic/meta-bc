# This recipe is only used for 5.2.x branch. On latest, gsm is built with the linphone SDK.

SUMMARY = "gsm is an open source project that provides the GSM audio codec"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=fc1372895b173aaf543a122db37e04f5"

PROVIDES = "gsm"

SRC_URI = "git://gitlab.linphone.org/BC/public/external/gsm.git;protocol=https;branch=linphone;"

S = "${WORKDIR}/git"

python () {
    d.setVar('SRCREV', "${AUTOREV}")
}

inherit cmake

EXTRA_OECMAKE = "-DENABLE_STATIC=ON"
OECMAKE_C_FLAGS += " -fPIC"

FILES:${PN} += "/usr/share/GSM/*"
