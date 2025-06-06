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

namespace tr.gov.tubitak.uekae.esya.api.cades.example.sign.profil
{
    [TestFixture]
    public class P1 : CadesSampleBase
    {
        private DirectoryInfo testDataDirectory = Directory.CreateDirectory(getTestDataFolder());

        [Test]
        public void testCreateP1()
        {
            BaseSignedData bs = new BaseSignedData();

            ISignable content = new SignableByteArray(Encoding.ASCII.GetBytes("test"));
            bs.addContent(content);

            List<IAttribute> optionalAttributes = new List<IAttribute>();
            optionalAttributes.Add(new SigningTimeAttr(DateTime.UtcNow));

            Dictionary<string, object> params_ = new Dictionary<string, object>();

            //if the user does not want certificate validation,he can add 
            //P_VALIDATE_CERTIFICATE_BEFORE_SIGNING parameter with its value set to false
            params_[EParameters.P_VALIDATE_CERTIFICATE_BEFORE_SIGNING] = false;

            bool checkQCStatement = isQualified();
            params_[EParameters.P_CERT_VALIDATION_POLICY] = getPolicy();

            //Get qualified or non-qualified certificate.
            ECertificate cert = SmartCardManager.getInstance().getSignatureCertificate(checkQCStatement);
            BaseSigner signer = SmartCardManager.getInstance().getSigner(getPin(), cert);

            //add signer
            bs.addSigner(ESignatureType.TYPE_BES, cert, signer, optionalAttributes, params_);

            SmartCardManager.getInstance().logout();

            byte[] signedDocument = bs.getEncoded();

            FileUtil.writeBytes(testDataDirectory.FullName + @"\P1.p7s", signedDocument);

            CadesSignatureValidation validationUtil = new CadesSignatureValidation();
            SignedDataValidationResult sdvr = validationUtil.validate(signedDocument, null);

            Assert.AreEqual(SignedData_Status.ALL_VALID, sdvr.getSDStatus());
        }
    }
}