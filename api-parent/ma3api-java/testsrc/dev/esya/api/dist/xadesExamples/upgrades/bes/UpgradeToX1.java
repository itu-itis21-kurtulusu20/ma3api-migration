package tr.gov.tubitak.uekae.esya.api.xades.example.upgrades.bes;

import org.junit.Test;
import tr.gov.tubitak.uekae.esya.api.signature.SignatureType;
import tr.gov.tubitak.uekae.esya.api.xades.example.XadesSampleBase;
import tr.gov.tubitak.uekae.esya.api.xades.example.validation.XadesSignatureValidation;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.Context;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.XMLSignature;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.document.FileDocument;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Provides upgrade function from BES to X1
 */
public class UpgradeToX1 extends XadesSampleBase {

    public static final String SIGNATURE_FILENAME = "x1_from_bes.xml";

    /**
     * Upgrades BES to X1. BES needs to be provided before upgrade process.
     * It can be created in formats.BES.
     *
     * @throws Exception
     */
    @Test
    public void upgradeBESToX1() throws Exception {

        // create context with working directory
        Context context = createContext();

        // read signature to be upgraded
        XMLSignature signature = XMLSignature.parse(new FileDocument(new File(getTestDataFolder() + "bes.xml")), context);

        // upgrade to X1
        signature.upgrade(SignatureType.ES_X_Type1);

        FileOutputStream fileOutputStream = new FileOutputStream(getTestDataFolder() + SIGNATURE_FILENAME);
        signature.write(fileOutputStream);
        fileOutputStream.close();

        XadesSignatureValidation signatureValidation = new XadesSignatureValidation();
        signatureValidation.validate(SIGNATURE_FILENAME);
    }

}
