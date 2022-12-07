# Used on the release 5.2 branch, for a stable version please use the "latest" recipe
VERSION_DEPENDENT_DEPENDS=""
VERSION_DEPENDENT_INHERIT="python3native"

# The default commit sha used if LINPHONE_SDK_REV is not set and LATEST_REVISIONS disabled
# 4.5.14
DEFAULT_COMMIT="0ddfcfdf6f61aec999330f3b689ffdac3677b500"

require linphone-sdk.inc

#GPLv2 or later
LICENSE = "GPLv2+"

inherit gitpkgv

PR = "${INC_PR}.0"
PV = "5.2.x"
PKGV = "${GITPKGVTAG}"
