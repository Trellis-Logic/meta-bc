# Used on the release 5.2 branch, for the last stable version please use the "latest" recipe
VERSION_DEPENDENT_DEPENDS="perl-native virtual/gettext gsm jsoncpp libjpeg-turbo"
VERSION_DEPENDENT_INHERIT="python3native"

# The default commit sha used if LINPHONE_SDK_REV is not set and LATEST_REVISIONS disabled
# 5.2.73
DEFAULT_COMMIT="820367237bd1291301ebfdee6c698db65e3eeacc"

inherit gettext pkgconfig perlnative 

require linphone-sdk.inc

PACKAGECONFIG[h264] = "-DENABLE_NON_FREE_CODECS=ON -DENABLE_OPENH264=ON, -DENABLE_OPENH264=OFF, openh264 nasm-native"
PACKAGECONFIG[opus] = "-DENABLE_OPUS=ON, -DENABLE_OPUS=OFF, libopus"
PACKAGECONFIG[sqlite] = "-DENABLE_DB_STORAGE=ON -DENABLE_SQLITE=ON, -DENABLE_DB_STORAGE=OFF -DENABLE_SQLITE=OFF, sqlite3"
PACKAGECONFIG[vpx] = "-DENABLE_VPX=ON, -DENABLE_VPX=OFF, libvpx yasm-native"
PACKAGECONFIG[yuv] = "-DENABLE_LIBYUV=ON, -DENABLE_LIBYUV=OFF, libyuv"
PACKAGECONFIG[zlib] = "-DENABLE_ZLIB=ON, -DENABLE_ZLIB=OFF, zlib"

EXTRA_OECMAKE:append = " -DENABLE_GTK_UI=OFF -DENABLE_LDAP=OFF -DENABLE_TESTS=ON"

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
