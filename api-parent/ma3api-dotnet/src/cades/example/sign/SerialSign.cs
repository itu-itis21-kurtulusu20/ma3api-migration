using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using NUnit.Framework;
using tr.gov.tubitak.uekae.esya.api.asn.x509;
using tr.gov.tubitak.uekae.esya.api.cades.example.validation;
using tr.gov.tubitak.uekae.esya.api.cmssignature;
using tr.gov.tubitak.uekae.esya.api.cmssignature.attribute;
using tr.gov.tubitak.uekae.esya.api.cmssignature.example.util;
using tr.gov.tubitak.uekae.esya.api.cmssignature.signature;
using tr.gov.tubitak.uekae.esya.api.cmssignature.validation;
using tr.gov.tubitak.uekae.esya.api.common.crypto;
using tr.gov.tubitak.uekae.esya.api.common.util;
using tr.gov.tubitak.uekae.esya.api.smartcard.pkcs11;

/**
 * Creates serial signature structures
 */

namespace tr.gov.tubitak.uekae.esya.api.cades.example.sign
{
    [TestFixture]
    public class SerialSign : CadesSampleBase
    {
        private readonly DirectoryInfo testDataDirectory = Directory.CreateDirectory(getTestDataFolder());

        public static readonly int SIGNATURE_COUNT = 5;

        /***
	     * Creates signature structure that has one counter signature in every level. 
	     * @param cert
	     * @param signer
	     * @return
	     */

        public static byte[] singInLoop(ECertificate cert, BaseSigner signer)
        {
            try
            {
                Dictionary<string, object> params_ = new Dictionary<string, object>();

                params_[EParameters.P_CERT_VALIDATION_POLICY] = getPolicy();
                bool validateCertificate = false;
                params_[EParameters.P_VALIDATE_CERTIFICATE_BEFORE_SIGNING] = validateCertificate;

                byte[] content = Encoding.ASCII.GetBytes("test");
                byte[] sign = null;

                for (int i = 0; i < SIGNATURE_COUNT; i++)
                {
                    BaseSignedData bs;
                    List<Signer> signerList = null;

                    if (sign == null)
                    {
                        // there is no signature, add content
                        bs = new BaseSignedData();
                        bs.addContent(new SignableByteArray(content));
                    }
                    else
                    {
                        // there are signatures, get signer list.
                        bs = new BaseSignedData(sign);
                        signerList = bs.getSignerList();
                    }


                    if ((signerList != null) && (signerList.Count > 0))
                    {
                        //find last counter signature of last signature
                        Signer lastSigner = signerList[signerList.Count - 1];
                        while (lastSigner.getCounterSigners().Count > 0)
                            lastSigner = lastSigner.getCounterSigners()[lastSigner.getCounterSigners().Count - 1];
                        lastSigner.addCounterSigner(ESignatureType.TYPE_BES, cert, signer, null, params_);
                    }
                    else
                    {
                        //add first signature.
                        bs.addSigner(ESignatureType.TYPE_BES, cert, signer, null, params_);
                    }

                    sign = bs.getEncoded();
                }

                return sign;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return null;
        }

        /**
	     * Creates signature structure that has two counter signatures in every level. 
	     * @param cert1
	     * @param signer1
	     * @param cert2
	     * @param signer2
	     * @return
	     */

        public static byte[] singInLoopWith2Signer(ECertificate cert1, BaseSigner signer1, ECertificate cert2,
            BaseSigner signer2)
        {
            try
            {
                Dictionary<string, object> params_ = new Dictionary<string, object>();

                params_[EParameters.P_CERT_VALIDATION_POLICY] = getPolicy();

                byte[] content = Encoding.ASCII.GetBytes("test");
                byte[] signature = null;

                for (int i = 0; i < SIGNATURE_COUNT; i++)
                {
                    BaseSignedData bs;
                    List<Signer> signerList = null;

                    if (signature == null)
                    {
                        // there is no signature, add content
                        bs = new BaseSignedData();
                        bs.addContent(new SignableByteArray(content));
                    }
                    else
                    {
                        // there are signatures, get signer list.
                        bs = new BaseSignedData(signature);
                        signerList = bs.getSignerList();
                    }

                    if ((signerList != null) && (signerList.Count > 0))
                    {
                        Signer lastSigner = signerList[signerList.Count - 1];
                        while (lastSigner.getCounterSigners().Count > 0)
                            lastSigner = lastSigner.getCounterSigners()[lastSigner.getCounterSigners().Count - 1];
                        lastSigner.addCounterSigner(ESignatureType.TYPE_BES, cert1, signer1, null, params_);
                        lastSigner.addCounterSigner(ESignatureType.TYPE_BES, cert2, signer2, null, params_);
                    }
                    else
                    {
                        //add first level two signatures.
                        bs.addSigner(ESignatureType.TYPE_BES, cert1, signer1, null, params_);
                        bs.addSigner(ESignatureType.TYPE_BES, cert2, signer2, null, params_);
                    }

                    signature = bs.getEncoded();
                }

                return signature;
            }
            catch (Exception e)
            {
                //e.printStackTrace();
                Console.WriteLine(e.StackTrace);
            }
            return null;
        }

        /**
	 * Creates a signature structure that has one different signer; and validates the structure.
	 * @throws Exception
	 */

        [Test]
        public void testSignInLoop()
        {
            SmartCardManager scm = SmartCardManager.getInstance();

            ECertificate cert = scm.getSignatureCertificate(isQualified());
            BaseSigner signer = scm.getSigner(getPin(), cert);

            byte[] lastSign = singInLoop(cert, signer);

            scm.logout();

            FileUtil.writeBytes(testDataDirectory.FullName + @"\counterSignatures.p7s", lastSign);

            CadesSignatureValidation validationUtil = new CadesSignatureValidation();
            SignedDataValidationResult sdvr = validationUtil.validate(lastSign, null);

            Assert.AreEqual(SignedData_Status.ALL_VALID, sdvr.getSDStatus());
        }

        /**
	 * Creates a signature structure that has two different signers; and validates the structure.
	 * @throws Exception
	 */

        [Test]
        public void testSignInLoopWith2Signer()
        {
            bool checkQCStatement = isQualified();

            Console.WriteLine("Select card - 1");

            SmartCardManager.reset();

            SmartCardManager scm1 = SmartCardManager.getInstance();

            ECertificate cert1 = scm1.getSignatureCertificate(checkQCStatement);
            BaseSigner signer1 = scm1.getSigner(getPin(), cert1);

            SmartCardManager.reset();


            Console.WriteLine("Select card - 2");

            SmartCardManager scm2 = SmartCardManager.getInstance();

            ECertificate cert2 = scm2.getSignatureCertificate(checkQCStatement);
            BaseSigner signer2 = scm2.getSigner(getPin(), cert2);

            SmartCardManager.reset();

            byte[] lastSign = singInLoopWith2Signer(cert1, signer1, cert2, signer2);

            try
            {
                scm1.logout();
                scm2.logout();
            }
            catch (SmartCardException sce)
            {
                //sce.printStackTrace();
                Console.WriteLine(sce.StackTrace);
            }

            FileUtil.writeBytes(testDataDirectory.FullName + @"\counterSignatures2.p7s", lastSign);

            CadesSignatureValidation validationUtil = new CadesSignatureValidation();
            SignedDataValidationResult sdvr = validationUtil.validate(lastSign, null);

            Assert.AreEqual(SignedData_Status.ALL_VALID, sdvr.getSDStatus());
        }
    }
}