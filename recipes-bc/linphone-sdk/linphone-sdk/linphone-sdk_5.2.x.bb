# Used on the release 5.2 branch, for a stable version please use the "latest" recipe
VERSION_DEPENDENT_DEPENDS=""
VERSION_DEPENDENT_INHERIT="python3native"

# The default commit sha used if LINPHONE_SDK_REV is not set and LATEST_REVISIONS disabled
# 5.2.0
DEFAULT_COMMIT="5688d2a90710da6501e4d4420ee9178d9538a363"

require linphone-sdk.inc

# AGPLv3 or later
LICENSE = "AGPL-3.0-or-later"

inherit gitpkgv

PR = "${INC_PR}.0"
PV = "5.2.x"
PKGV = "${GITPKGVTAG}"
