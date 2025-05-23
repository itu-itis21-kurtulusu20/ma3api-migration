﻿using System;
using System.IO;
using System.Text;
using System.Xml;
using ImzaApiTest.src.tr.gov.tubitak.uekae.esya.api;
using tr.gov.tubitak.uekae.esya.api.common;
using tr.gov.tubitak.uekae.esya.api.xmlsignature;
using tr.gov.tubitak.uekae.esya.api.xmlsignature.config;
using tr.gov.tubitak.uekae.esya.api.xmlsignature.resolver;

namespace tr.gov.tubitak.uekae.esya.api.xades.example
{
    /**
     * Provides required variables and functions for XAdES examples
     */

    public class XadesSampleBase : SampleBase
    {
        public static readonly int[] OID_POLICY_P2 = new int[] {2, 16, 792, 1, 61, 0, 1, 5070, 3, 1, 1};
        public static readonly int[] OID_POLICY_P3 = new int[] {2, 16, 792, 1, 61, 0, 1, 5070, 3, 2, 1};
        public static readonly int[] OID_POLICY_P4 = new int[] {2, 16, 792, 1, 61, 0, 1, 5070, 3, 3, 1};

        private static readonly string configFile; // config file path
        private static readonly string testDataFolder; // base directory where signatures created

        private static readonly string policyFile; // certificate validation policy file path
        private static readonly string policyFileCrl; // path oaf policy file without OCSP

        private static readonly OfflineResolver offlineResolver; // policy resolver for profile examples

        private readonly string ENVELOPE_XML = // sample XML document used for enveloped signature
            "<envelope>\n" +
            "  <data id=\"data1\">\n" +
            "    <item>Item 1</item>\n" +
            "    <item>Item 2</item>\n" +
            "    <item>Item 3</item>\n" +
            "  </data>\n" +
            "</envelope>\n";

        /**
         * Initialize paths and other variables
         */

        static XadesSampleBase()
        {
            testDataFolder = getRootDir() + "/testVerileri/";
            configFile = getRootDir() + "/config/xmlsignature-config.xml";
            policyFile = getRootDir() + "/config/certval-policy-test.xml";
            policyFileCrl = getRootDir() + "/config/certval-policy-test-crl.xml";

            offlineResolver = new OfflineResolver();
            offlineResolver.register("urn:oid:2.16.792.1.61.0.1.5070.3.1.1", getRootDir() +
                                                                             "/config/profiller/Elektronik_Imza_Kullanim_Profilleri_Rehberi.pdf",
                "text/plain");
            offlineResolver.register("urn:oid:2.16.792.1.61.0.1.5070.3.2.1", getRootDir() +
                                                                             "/config/profiller/Elektronik_Imza_Kullanim_Profilleri_Rehberi.pdf",
                "text/plain");
            offlineResolver.register("urn:oid:2.16.792.1.61.0.1.5070.3.3.1", getRootDir() +
                                                                             "/config/profiller/Elektronik_Imza_Kullanim_Profilleri_Rehberi.pdf",
                "text/plain");
        }

        /**
         * Creates context for signature creation and validation
         *
         * @return created context
         */

        protected Context createContext()
        {
            Context context = new Context(testDataFolder);
            //context.Config = new Config(configFile);
            //return context;

            /*for getting test TimeStamp or qualified TimeStamp account, mail to bilgi@kamusm.gov.tr.
            This configuration, user ID (user_id) and password (password), is invalid.*/

            throw new ESYAException("\n- Zaman Damgası kullanmak için zaman damgası hesap bilgilerini, xmlsignature-config.xml dosyasındaki <timestamp-server> tagleri arasına giriniz. Varsayılan olarak tanımlı olan user ID (user_id) ve password (password) geçersizdir. Zaman damgası hesap bilgilerini ayarladıktan sonra \"context.Config = new Config(configFile);\" ve \"return context;\" satırlarını aktifleştiriniz.\n" +
                                    "- MA3 API sadece KamuSM Zaman Damgası ile çalışabilmektedir.\n" +
                                    "- Zaman damgası test kullanıcısı talep etmek amacıyla Kamu SM (bilgi[at]kamusm.gov.tr)'ye e-posta gönderilmesi gerekmektedir. İlgili e-posta'nın konu kısmında \"Zamane test kullanıcı talebi\", içeriğinde ise \"Kurum adı, kurum vergi kimlik numarası, kurum adresi, kurum sabit telefon, yetkili kişi adı ve soyadı, cep telefonu numarası, yetkili kişi e-posta\" bilgilerinin yer alması gerekmektedir.\n" +
                                    " Ayrıntılar için: https://kamusm.bilgem.tubitak.gov.tr/urunler/zaman_damgasi/ucretsiz_zaman_damgasi_istemci_yazilimi.jsp");
        }

        public XmlDocument newEnvelope()
        {
            try
            {
                byte[] bytes = Encoding.UTF8.GetBytes(ENVELOPE_XML);
                MemoryStream ms = new MemoryStream(bytes);

                XmlDocument doc = new XmlDocument();
                doc.PreserveWhitespace = true;
                XmlReader reader = XmlReader.Create(ms);
                doc.Load(reader);

                return doc;
            }
            catch (Exception x)
            {
                // we shouldn't be here if ENVELOPE_XML is valid
                Console.WriteLine(x.StackTrace);
            }
            throw new ESYAException("Cant construct envelope xml ");
        }

        /**
         * Creates sample envelope XML that will contain signature inside
         * by reading the given file name in base directory
         */

        public XmlDocument newEnvelope(string file)
        {
            try
            {
                //logger.Debug(getRootDir() + file);
                byte[] bytes = File.ReadAllBytes(getRootDir() + file);
                MemoryStream ms = new MemoryStream(bytes);

                XmlDocument doc = new XmlDocument();
                doc.PreserveWhitespace = true;
                XmlReader reader = XmlReader.Create(ms);
                doc.Load(reader);

                return doc;
            }
            catch (Exception x)
            {
                // we shouldn't be here if ENVELOPE_XML is valid
                Console.WriteLine(x.StackTrace);
            }
            throw new ESYAException("Cant construct envelope xml ");
        }

        /**
         * Reads an XML document into XmlDocument format
         */

        public XmlDocument parseDoc(string uri)
        {
            byte[] bytes = File.ReadAllBytes(getRootDir() + uri);
            MemoryStream ms = new MemoryStream(bytes);

            XmlDocument doc = new XmlDocument();
            doc.PreserveWhitespace = true;
            XmlReader reader = XmlReader.Create(ms);
            doc.Load(reader);

            return doc;
        }

        /**
         * Gets the signature by searching for tag in an XML document
         */

        public XMLSignature readSignature(XmlDocument aDocument, Context aContext)
        {
            // get the signature in enveloped signature format
            XmlNode signatureElement = aDocument.GetElementsByTagName("ds:Signature").Item(0);

            // return the XML signature created with signature element
            return new XMLSignature((XmlElement) signatureElement, aContext);
        }

        /**
         * Gets the signature by searching for tag in an XML document
         */

        public XMLSignature readSignature(XmlDocument aDocument, Context aContext, int item)
        {
            // get the signature in parallel signature format
            XmlNode signatureElement =
                ((XmlElement) aDocument.GetElementsByTagName("signatures").Item(0)).GetElementsByTagName("ds:Signature")
                    .Item(item);

            // return the XML signature created with signature element
            return new XMLSignature((XmlElement) signatureElement, aContext);
        }

        /**
         * Gets test data folder
         *
         * @return the test data folder
         */

        protected string getTestDataFolder()
        {
            return testDataFolder;
        }

        /**
         * Gets offline policy resolver for profile examples
         *
         * @return the offline resolver
         */

        protected OfflineResolver getPolicyResolver()
        {
            return offlineResolver;
        }

        /**
         * Gets policy file crl
         *
         * @return the policy file crl
         */

        protected string getPolicyFileCrl()
        {
            return policyFileCrl;
        }

        /**
         * Gets policy file
         *
         * @return the policy file
         */

        protected string getPolicyFile()
        {
            return policyFile;
        }

    }
}