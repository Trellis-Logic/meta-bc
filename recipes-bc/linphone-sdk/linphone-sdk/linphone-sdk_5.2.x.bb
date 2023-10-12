# Used on the release 5.2 branch, for the last stable version please use the "latest" recipe
VERSION_DEPENDENT_DEPENDS="perl-native virtual/gettext gsm jsoncpp libjpeg-turbo"
VERSION_DEPENDENT_INHERIT="python3native"

# The default commit sha used if LINPHONE_SDK_REV is not set and LATEST_REVISIONS disabled
# 5.2.73
DEFAULT_COMMIT="20cb52c50818566a507e8927a5c79e7843187131"

inherit cmakebuilder gettext pkgconfig perlnative 

require linphone-sdk.inc

PACKAGECONFIG[h264] = "-DENABLE_NON_FREE_CODECS=ON -DENABLE_OPENH264=ON, -DENABLE_OPENH264=OFF, openh264 nasm-native"
PACKAGECONFIG[opus] = "-DENABLE_OPUS=ON, -DENABLE_OPUS=OFF, libopus"
PACKAGECONFIG[sqlite] = "-DENABLE_DB_STORAGE=ON -DENABLE_SQLITE=ON, -DENABLE_DB_STORAGE=OFF -DENABLE_SQLITE=OFF, sqlite3"
PACKAGECONFIG[vpx] = "-DENABLE_VPX=ON, -DENABLE_VPX=OFF, libvpx yasm-native"
PACKAGECONFIG[yuv] = "-DENABLE_LIBYUV=ON, -DENABLE_LIBYUV=OFF, libyuv"
PACKAGECONFIG[zlib] = "-DENABLE_ZLIB=ON, -DENABLE_ZLIB=OFF, zlib"

EXTRA_OECMAKE:append = " -DENABLE_GTK_UI=OFF -DENABLE_LDAP=OFF -DENABLE_TESTS=ON"


BUILD_INSTALL_PREFIX = "${WORKDIR}/git/linphone-sdk/desktop"
BUILD_INSTALL_PATH = "${BUILD_INSTALL_PREFIX}${INSTALL_PATH}"
# Used by cmake.bbclass to add the temporary target directory as root for find_package, required to find correct libraries directories
OECMAKE_EXTRA_ROOT_PATH = "${BUILD_INSTALL_PATH}"

EXTRA_OECMAKE:append = " -DCMAKE_STAGING_PREFIX=${BUILD_INSTALL_PATH}"

EXTRA_OECMAKE:append = " -DCMAKE_EXE_LINKER_FLAGS='-Wl,-rpath-link=${BUILD_INSTALL_PATH}/lib -Wl,-rpath=${INSTALL_PATH}/lib'"
EXTRA_OECMAKE:append = " -DCMAKE_SHARED_LINKER_FLAGS='-Wl,-rpath=${INSTALL_PATH}/lib -Wl,-rpath-link=${BUILD_INSTALL_PATH}/lib'"
EXTRA_OECMAKE:append = " -DCMAKE_MODULE_LINKER_FLAGS='-Wl,-rpath=${INSTALL_PATH}/lib -Wl,-rpath-link=${BUILD_INSTALL_PATH}/lib'"

do_install:append () {
	# Do not include generated and installed cmake/pkgconfig files to the target package
	rm -rf "${BUILD_INSTALL_PATH}/cmake"
	rm -rf "${BUILD_INSTALL_PATH}/lib/pkgconfig"
	
	install -m 0755 -d ${D}${INSTALL_PATH}/bin ${D}${INSTALL_PATH}/lib ${D}${INSTALL_PATH}/include ${D}${INSTALL_PATH}/share
	cp -r ${BUILD_INSTALL_PATH}/bin/* ${D}${INSTALL_PATH}/bin
	cp -r ${BUILD_INSTALL_PATH}/lib/* ${D}${INSTALL_PATH}/lib
	cp -r ${BUILD_INSTALL_PATH}/share/* ${D}${INSTALL_PATH}/share
	cp -r ${BUILD_INSTALL_PATH}/include/* ${D}${INSTALL_PATH}/include
}

# AGPLv3 or later
LICENSE = "AGPL-3.0-or-later"

inherit gitpkgv

PR = "${INC_PR}.0"
PV = "5.2.x"
PKGV = "${GITPKGVTAG}"
