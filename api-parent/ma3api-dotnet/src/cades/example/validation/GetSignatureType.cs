using NUnit.Framework;
using tr.gov.tubitak.uekae.esya.api.cmssignature.signature;
using tr.gov.tubitak.uekae.esya.api.common.util;

namespace tr.gov.tubitak.uekae.esya.api.cades.example.validation
{
    [TestFixture]
    public class GetSignatureType : CadesSampleBase
    {
        /**
	 * Getting signature type of a ESA type signature
	 * @throws Exception
	 */

        [Test]
        public void testIsItESA()
        {
            byte[] content = FileUtil.readBytes(getTestDataFolder() + "ESA-1.p7s");
            BaseSignedData bs = new BaseSignedData(content);

            ESignatureType type = bs.getSignerList()[0].getType();

            Assert.AreEqual(type, ESignatureType.TYPE_ESA);
        }
    }
}