# Used on the release 5.2 branch, for a stable version please use the "latest" recipe
VERSION_DEPENDENT_DEPENDS=""
VERSION_DEPENDENT_INHERIT="python3native"

# The default commit sha used if LINPHONE_SDK_REV is not set and LATEST_REVISIONS disabled
# 5.2.0
DEFAULT_COMMIT="62a0a8fcd49caab7a170dccffb4aa5163079cc25"

require linphone-sdk.inc

# AGPLv3 or later
LICENSE = "AGPL-3.0-or-later"

inherit gitpkgv

PR = "${INC_PR}.0"
PV = "5.2.x"
PKGV = "${GITPKGVTAG}"
