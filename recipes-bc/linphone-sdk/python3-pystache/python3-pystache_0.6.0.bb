SUMMARY = "Python implementation of Mustache"
HOMEPAGE = "https://github.com/defunkt/pystache"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=eb4417802c56384aac71b34505528a60"

SRC_URI[md5sum] = "c834ab23ec0d4a0e47cfa281bf7bfcd1"
SRC_URI[sha256sum] = "93bf92b2149a4c4b58d12142e2c4c6dd5c08d89e4c95afccd4b6efe2ee1d470d"

inherit pypi setuptools3

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-netserver \
"

BBCLASSEXTEND = "native nativesdk"