DESCRIPTION = "WIP LINPHONE-SDK Audio/video SIP-based IP phone (console edition)"
HOMEPAGE = "http://www.linphone.org/technical-corner/linphone/overview"
SECTION = "network"
SECTION:linphonec =  "console/network"

INC_PR = "r0"

DEPENDS = "\
    doxygen-native git-native python3-native python3-pystache-native python3-six-native \
    alsa-lib libsrtp libxml2 mariadb pulseaudio speex speexdsp xerces-c \
    ${VERSION_DEPENDENT_DEPENDS} \
    "

PROVIDES = "linphone-sdk"

RPROVIDES:${PN} = "\
    bcunit bcg729 bctoolbox belle-sip belr belcard bzrtp liblinphone linphonec lime ortp \
    mediastreamer2 mswebrtc \
    "

S = "${WORKDIR}/git"

inherit chrpath ${VERSION_DEPENDENT_INHERIT}

python () {
# 3 possible revisions:
# - if env[LATEST_REVISIONS] is set, use yocto autorev mecanism
# - if env[LINPHONE_SDK_REV] is defined, use the value as the revision (useful for continous integration)
# - Otherwise, use fixed stable revision

    if os.getenv('LATEST_REVISIONS', '') == "1":
        d.setVar('SRCREV', "${AUTOREV}")
    else:
        d.setVar('SRCREV', os.getenv('LINPHONE_SDK_REV', "${DEFAULT_COMMIT}"))

    d.setVar('LINPHONE_SDK_BRANCH', os.getenv('LINPHONE_SDK_BRANCH', 'master'))
}

# For visualisation
python () {
    print("")
    print("linphone-sdk")
    print(d.getVar('SRCREV', True))
}

SRC_URI = "gitsm://gitlab.linphone.org/BC/public/linphone-sdk.git;protocol=https;branch=${LINPHONE_SDK_BRANCH};"

python () {
    if os.getenv("LINPHONE_SDK_LIC_FILES_CHKSUM", '') != '':
      bb.note("LINPHONE_SDK_LIC_FILES_CHKSUM=" + os.getenv("LINPHONE_SDK_LIC_FILES_CHKSUM", ''))
      d.setVar("LIC_FILES_CHKSUM_ENV", os.getenv("LINPHONE_SDK_LIC_FILES_CHKSUM"))
    else:
      d.setVar("LIC_FILES_CHKSUM_ENV", "eb1e647870add0502f8f010b19de32af")
}
# Bitbakes throws parse errors when we try to set license dynamically, so we only set LIC_FILES_CHKSUM (check .bb files to see which values to set)
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=${LIC_FILES_CHKSUM_ENV}"

PACKAGECONFIG ??= "bzrtp opus sqlite tests vpx yuv zlib ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'video', '', d)}"
PACKAGECONFIG[bzrtp] = " -DENABLE_BZRTP=ON, -DENABLE_BZRTP=OFF"
PACKAGECONFIG[mdns] = "-DENABLE_MDNS=ON, -DENABLE_MDNS=OFF, avahi"
PACKAGECONFIG[tests] = "-DENABLE_UNIT_TESTS=ON, -DENABLE_UNIT_TESTS=OFF"
PACKAGECONFIG[video] = "-DENABLE_VIDEO=ON -DENABLE_X11=ON -DENABLE_XV=ON -DENABLE_GLX=ON -DENABLE_GL=OFF, -DENABLE_VIDEO=OFF, libv4l libxv virtual/libgl glew"

EXTRA_OECMAKE ??= " "
EXTRA_OECMAKE:append = " -DLINPHONESDK_PLATFORM=Yocto"
EXTRA_OECMAKE:append = " -DENABLE_CONSOLE_UI=OFF -DENABLE_CXX_WRAPPER=ON -DENABLE_VCARD=ON -DENABLE_XML2=ON -DENABLE_SOCI_MYSQL=ON"

# Force cmake cross-compiling
EXTRA_OECMAKE:append = " -DCMAKE_CROSSCOMPILING=ON"
EXTRA_OECMAKE:append = " -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake"

INSTALL_PATH = "/opt/belledonne-communications"

OECMAKE_FIND_ROOT_PATH_MODE_PROGRAM = "BOTH"

EXTRA_OECMAKE:append = " -DCMAKE_C_IMPLICIT_INCLUDE_DIRECTORIES=${STAGING_INCDIR}"
EXTRA_OECMAKE:append = " -DCMAKE_CXX_IMPLICIT_INCLUDE_DIRECTORIES=${STAGING_INCDIR}"

# The actual target installation prefix when cross-compiling
EXTRA_OECMAKE:append = " -DCMAKE_INSTALL_PREFIX=${INSTALL_PATH}"

EXTRA_OECMAKE:append = " -DCMAKE_SKIP_RPATH=ON"


# Apply default linker flags provided by Yocto.
# This avoids 'No GNU_HASH in the elf binary' error as documented
# in https://www.yoctoproject.org/docs/2.0.1/ref-manual/ref-manual.html#qa-issue-ldflags .
TARGET_CC_ARCH += "${LDFLAGS}"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES:${PN} += "${INSTALL_PATH}/bin/*"
FILES:${PN} += "${INSTALL_PATH}/lib/*"
FILES:${PN} += "${INSTALL_PATH}/share/*"
FILES:${PN}-dev = "${INSTALL_PATH}/include/*"

SYSROOT_DIRS += "/opt"

# Allow non-symlink .so files and static libraries in ${PN} package
INSANE_SKIP:${PN} += "dev-so staticdev already-stripped"
