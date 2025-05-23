using System;
using System.IO;
using System.Reflection;
using log4net;
using tr.gov.tubitak.uekae.esya.api.common.util;
using tr.gov.tubitak.uekae.esya.api.crypto.alg;
using tr.gov.tubitak.uekae.esya.api.signature.util;

namespace ImzaApiTest.src.tr.gov.tubitak.uekae.esya.api
{
    public class SampleBase
    {
        private static readonly ILog logger = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        // bundle root directory of project
        private static readonly string ROOT_DIR = "C:/ma3api-dotnet";

        // gets only qualified certificates in smart card
        private static readonly bool IS_QUALIFIED = true;

        // the pin of the smart card
        private static readonly string PIN_SMARTCARD = "12345";

        static SampleBase()
        {
            try
            {
                LicenseUtil.setLicenseXml(new FileStream(ROOT_DIR + "/lisans/lisans.xml", FileMode.Open, FileAccess.Read));

                DateTime expirationDate = LicenseUtil.getExpirationDate();
                Console.WriteLine("License expiration date : " + expirationDate.ToShortDateString());

                /* // To set class path
                URL root = CadesSampleBase.class.getResource("/");
                String classPath = root.getPath();
                File binDir = new File(classPath);
                ROOT_DIR = binDir.getParentFile().getParent();
                */

                /*
                string dir = Directory.GetCurrentDirectory();
                ROOT_DIR = Directory.GetParent(dir).Parent.Parent.Parent.FullName;
                if (dir.Contains("x86") || dir.Contains("x64"))
                {
                    ROOT_DIR = Directory.GetParent(ROOT_DIR).FullName;
                }
                */
            }
            catch (Exception e)
            {
                logger.Error(e);
            }
        }

        /**
         * Gets the bundle root directory of project
         *
         * @return the root dir
         */

        protected static string getRootDir()
        {
            return ROOT_DIR;
        }

        /**
         * Gets the pin of the smart card
         *
         * @return the pin
         */

        protected static string getPin()
        {
            throw new Exception("Set the pin of the smart card!");
            //return PIN_SMARTCARD;
        }

        /**
         * The parameter to choose the qualified certificates in smart card
         *
         * @return the
         */

        protected static bool isQualified()
        {
            return IS_QUALIFIED;
        }

        protected String getPFXPath()
        {
            throw new Exception("Set pfx file path!");
            //return getRootDir() + "/sertifika deposu/test1@test.com_745418.pfx";
        }

        protected String getPFX_PIN()
        {
            throw new Exception("Set pfx file password!");
            //return "745418";
        }

        /**
         * Gets pfx signer
         *
         * @return the pfx signer
         */

        protected PfxSigner getPfxSigner()
        {
            return new PfxSigner(SignatureAlg.RSA_SHA256.getName(), getPFXPath(), getPFX_PIN());
        }
    }
}