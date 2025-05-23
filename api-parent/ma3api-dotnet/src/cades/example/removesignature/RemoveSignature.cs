using NUnit.Framework;
using tr.gov.tubitak.uekae.esya.api.cmssignature.signature;
using tr.gov.tubitak.uekae.esya.api.common.util;

/**
 * Removes signature. 
 * Firstly run sign operations in order to create signatures to be removed. 
 */

namespace tr.gov.tubitak.uekae.esya.api.cades.example.removesignature
{
    [TestFixture]
    public class RemoveSignature : CadesSampleBase
    {
        /**
         * Removes the second counter signature and remains one signer.
         * @throws Exception
         */

        [Test]
        public void testKeepOne()
        {
            byte[] content = FileUtil.readBytes(getTestDataFolder() + "counterSignatures.p7s");
            BaseSignedData bs = new BaseSignedData(content);
            bs.getSignerList()[0].getCounterSigners()[0].remove();
            byte[] noSign = bs.getEncoded();

            BaseSignedData removedBsd = new BaseSignedData(noSign);
            Assert.AreEqual(1, removedBsd.getAllSigners().Count);
        }

        /**
         * Removes the fourth counter signature and remains three signer.
         * @throws Exception
         */

        [Test]
        public void testKeepThree()
        {
            byte[] content = FileUtil.readBytes(getTestDataFolder() + "counterSignatures.p7s");
            BaseSignedData bs = new BaseSignedData(content);
            bs.getSignerList()[0].getCounterSigners()[0].getCounterSigners()[0].getCounterSigners()[0].remove();

            byte[] noSign = bs.getEncoded();

            BaseSignedData removedBsd = new BaseSignedData(noSign);
            Assert.AreEqual(3, removedBsd.getAllSigners().Count);
        }

        /**
         * Removes the third counter signature and remains two signer.
         * @throws Exception
         */

        [Test]
        public void testKeepTwo()
        {
            byte[] content = FileUtil.readBytes(getTestDataFolder() + "counterSignatures.p7s");
            BaseSignedData bs = new BaseSignedData(content);
            bs.getSignerList()[0].getCounterSigners()[0].getCounterSigners()[0].remove();

            byte[] noSign = bs.getEncoded();

            BaseSignedData removedBsd = new BaseSignedData(noSign);
            Assert.AreEqual(2, removedBsd.getAllSigners().Count);
        }

        /**
	 * Removes the first counter signature and remains no signer.
	 * @throws Exception
	 */

        [Test]
        public void testRemoveAll()
        {
            byte[] content = FileUtil.readBytes(getTestDataFolder() + "counterSignatures.p7s");
            BaseSignedData bs = new BaseSignedData(content);
            bs.getSignerList()[0].remove();
            byte[] noSign = bs.getEncoded();

            BaseSignedData removedBsd = new BaseSignedData(noSign);
            Assert.AreEqual(0, removedBsd.getAllSigners().Count);
        }
    }
}