# This recipe must always evolve to work with the last stable linphone-sdk version.
VERSION_DEPENDENT_DEPENDS=""
VERSION_DEPENDENT_INHERIT="python3native"

# The default commit sha used if LINPHONE_SDK_REV is not set and LATEST_REVISIONS disabled
DEFAULT_COMMIT="8f1b2d1f6cf5dbb1f0a8e442929e43a2b9af9703"

require linphone-sdk.inc

PACKAGECONFIG:append = " ldap"
PACKAGECONFIG[h264] = "-DENABLE_NON_FREE_FEATURES=ON -DENABLE_OPENH264=ON -DBUILD_OPENH264=OFF, -DENABLE_OPENH264=OFF, openh264 nasm-native"
PACKAGECONFIG[ldap] = "-DENABLE_LDAP=ON, -DENABLE_LDAP=OFF"
PACKAGECONFIG[opus] = "-DENABLE_OPUS=ON -DBUILD_OPUS=OFF, -DENABLE_OPUS=OFF, libopus"
PACKAGECONFIG[sqlite] = "-DENABLE_DB_STORAGE=ON -DENABLE_SQLITE=ON -DBUILD_SQLITE3=OFF, -DENABLE_DB_STORAGE=OFF -DENABLE_SQLITE=OFF, sqlite3"
PACKAGECONFIG[vpx] = "-DENABLE_VPX=ON -DBUILD_LIBVPX=OFF, -DENABLE_VPX=OFF, libvpx yasm-native"
PACKAGECONFIG[yuv] = "-DENABLE_LIBYUV=ON -DBUILD_LIBYUV=ON, -DENABLE_LIBYUV=OFF"
PACKAGECONFIG[zlib] = "-DENABLE_ZLIB=ON -DBUILD_ZLIB=OFF, -DENABLE_ZLIB=OFF, zlib"

EXTRA_OECMAKE:append = " \
        -DENABLE_AV1=OFF \
	-DBUILD_LIBJPEGTURBO=OFF \
	-DBUILD_LIBSRTP2=OFF \
	-DBUILD_LIBXML2=OFF \
	-DBUILD_MBEDTLS=OFF \
	-DBUILD_SPEEX=OFF \
	-DBUILD_XERCESC=OFF \
"

# AGPLv3 or later
LICENSE = "AGPL-3.0-or-later"
# LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=eb1e647870add0502f8f010b19de32af"

inherit gitpkgv

PR = "${INC_PR}.0"
PV = "latest"
PKGV = "${GITPKGVTAG}"
