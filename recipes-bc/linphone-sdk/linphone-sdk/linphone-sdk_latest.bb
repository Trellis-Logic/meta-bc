# Same as the "5.2.x" recipe for the moment, this recipe must always evolve to
# work with the last stable linphone-sdk version.
VERSION_DEPENDENT_DEPENDS=""
VERSION_DEPENDENT_INHERIT="python3native"

# The default commit sha used if LINPHONE_SDK_REV is not set and LATEST_REVISIONS disabled
DEFAULT_COMMIT="61ae4f35e4edad1028b82b274fc59633351b65b3"

require linphone-sdk.inc

# License variables to use pre Linphone-SDK 5.2
# GPLv2 or later
# LICENSE = "GPL-2.0-or-later"
# LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ff3103b5db8ba4e2c66c511b7a73e407"

# Licences variables to use for Linphone-SDK>=5.2
# AGPLv3 or later
LICENSE = "AGPL-3.0-or-later"
# LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=eb1e647870add0502f8f010b19de32af"

inherit gitpkgv

PR = "${INC_PR}.0"
PV = "latest"
PKGV = "${GITPKGVTAG}"
