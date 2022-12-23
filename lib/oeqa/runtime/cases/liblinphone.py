from oeqa.runtime.case import OERuntimeTestCase
from oeqa.runtime.decorator.package import OEHasPackage

class LibLinphoneTest(OERuntimeTestCase):

    @OEHasPackage(['linphone-sdk'])
    def test_liblinphone(self):
        (status, output) = self.target.run('/opt/belledonne-communications/bin/liblinphone_tester')
        self.assertEqual(status, 0, msg='LibLinphone Test failed: %s' % output)
