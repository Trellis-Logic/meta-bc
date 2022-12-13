DESCRIPTION = "Linphone version of sofia-sip"
LICENSE = "LGPL-2.1-only"
HOMEPAGE = "http://sofia-sip.sourceforge.net/"
SECTION = "libs/network"

INC_PR = "r0"

PROVIDES = "sofia-sip"

PN = "sofia-sip-ua-bc"
DEPENDS = "openssl"
PROVIDES = "libsofia-sip-ua-bc"
S = "${WORKDIR}/git"

PACKAGES += " ${PN}-common"

FILES:${PN}-common = "${datadir}/sofia-sip/tag_dll.awk ${datadir}/sofia-sip/msg_parser.awk"

inherit autotools pkgconfig

do_autoreconf () {
    ./autogen.sh
}
