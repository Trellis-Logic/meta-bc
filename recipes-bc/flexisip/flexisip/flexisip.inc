DESCRIPTION = "General purpose SIP proxy with media capabilities from Belledonne Communications"
LICENSE = "AGPL-3.0-only"
HOMEPAGE = "http://www.linphone.org/technical-corner/flexisip/overview"
SECTION = "comm"

INC_PR = "r0"

PROVIDES = "flexisip flexisip-proxy flexisip-presence flexisip-conference"

DEPENDS = "\
	doxygen-native git-native protobuf-c-native python3-native python3-pystache-native python3-six-native \
	glew gsm hiredis jansson jsoncpp virtual/libgl libopus libsofia-sip-ua-bc libvpx libxv \
	libyuv mariadb nghttp2 protobuf redis speex speexdsp xerces-c \
	"

S = "${WORKDIR}/git"

inherit cmake systemd python3native

PACKAGECONFIG ??= "presence conference"
PACKAGECONFIG[presence] = "-DENABLE_PRESENCE=YES, -DENABLE_PRESENCE=NO"
PACKAGECONFIG[conference] = "-DENABLE_CONFERENCE=YES, -DENABLE_CONFERENCE=NO"
PACKAGECONFIG[mdns]= "-DENABLE_MDNS=YES, -DENABLE_MDNS=NO"

BUILD_INSTALL_PREFIX = "${WORKDIR}/git"

INSTALL_PATH = "/opt/belledonne-communications"

BUILD_INSTALL_PATH = "${BUILD_INSTALL_PREFIX}${INSTALL_PATH}"

OECMAKE_EXTRA_ROOT_PATH = "${INSTALL_PATH}"

EXTRA_OECMAKE += " -DCMAKE_CROSSCOMPILING=YES"
EXTRA_OECMAKE += " -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake"

EXTRA_OECMAKE += " -DCMAKE_PREFIX_PATH=${STAGING_DIR_HOST}/${OECMAKE_EXTRA_ROOT_PATH}"

EXTRA_OECMAKE += " -DCMAKE_INSTALL_DATAROOTDIR=share -DCMAKE_INSTALL_LIBDIR=lib"
EXTRA_OECMAKE += " -DENABLE_TRANSCODER=YES -DENABLE_SNMP=NO -DENABLE_SOCI=YES -DENABLE_SOCI_POSTGRESQL_BACKEND=NO -DENABLE_DOC=NO"

#The actual target installation prefix when cross-compiling
EXTRA_OECMAKE += " -DCMAKE_INSTALL_PREFIX=${INSTALL_PATH}"

EXTRA_OECMAKE += " -DCMAKE_SKIP_RPATH=YES"
EXTRA_OECMAKE += " -DCMAKE_EXE_LINKER_FLAGS='-Wl,-rpath-link=${STAGING_DIR_HOST}${INSTALL_PATH}/lib -Wl,-rpath=${INSTALL_PATH}/lib'"

EXTRA_OECMAKE += " -DCMAKE_C_IMPLICIT_INCLUDE_DIRECTORIES=${STAGING_INCDIR}"
EXTRA_OECMAKE += " -DCMAKE_CXX_IMPLICIT_INCLUDE_DIRECTORIES=${STAGING_INCDIR}"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES:${PN} += "${INSTALL_PATH}/lib/libflexisip.so"
FILES:${PN} += "${INSTALL_PATH}/lib/*.so.*"
FILES:${PN} += "${INSTALL_PATH}/lib/flexisip/plugins/*.so.*"
FILES:${PN} += "${INSTALL_PATH}/bin/flexisip"
FILES:${PN} += "${INSTALL_PATH}/bin/flexisip_cli.py"
FILES:${PN} += "${INSTALL_PATH}/bin/flexisip_tester"
FILES:${PN} += "${INSTALL_PATH}/share/*"
FILES:${PN} += "/lib/systemd/system/"

FILES:${PN}-dev = "${INSTALL_PATH}/bin/flexisip_pusher"
FILES:${PN}-dev += "${INSTALL_PATH}/bin/flexisip_serializer"
FILES:${PN}-dev += "${INSTALL_PATH}/bin/linphone-daemon*"
FILES:${PN}-dev += "${INSTALL_PATH}/cmake/*"
FILES:${PN}-dev += "${INSTALL_PATH}/include/*"
FILES:${PN}-dev += "${INSTALL_PATH}/lib/pkgconfig/*"
FILES:${PN}-dev += "${INSTALL_PATH}/lib/cmake/*"
FILES:${PN}-dev += "${INSTALL_PATH}/lib/*.so"
FILES:${PN}-dev += "${INSTALL_PATH}/lib/flexisip/plugins/*.so"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "flexisip-proxy.service flexisip-proxy@.service flexisip-conference.service flexisip-conference@.service flexisip-presence.service flexisip-presence@.service"

PRIVATE_LIBS += "libsofia-sip-ua.so.0"