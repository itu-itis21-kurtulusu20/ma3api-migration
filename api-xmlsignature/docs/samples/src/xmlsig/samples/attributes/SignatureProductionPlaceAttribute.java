package xmlsig.samples.attributes;

import org.junit.Test;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.Context;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.XMLSignature;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.XMLSignatureException;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.model.xades.SignatureProductionPlace;
import xmlsig.samples.utils.SampleBase;
import xmlsig.samples.validation.Validation;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Adds SignatureProductionPlace attribute to BES
 * @author: suleyman.uslu
 */
public class SignatureProductionPlaceAttribute extends SampleBase {

    public static final String SIGNATURE_FILENAME = "signature_production_place.xml";

    /**
     * Creates BES with SignatureProductionPlace attribute
     * @throws Exception
     */
    @Test
    public void createBESWithSignatureProductionPlace() throws Exception
    {
        try {
            // create context with working directory
            Context context = new Context(BASE_DIR);

            // create signature according to context,
            // with default type (XADES_BES)
            XMLSignature signature = new XMLSignature(context);

            // add document as reference, but do not embed it
            // into the signature (embed=false)
            signature.addDocument("./sample.txt", "text/plain", false);

            // add certificate to show who signed the document
            signature.addKeyInfo(CERTIFICATE);

            // add signature production place
            signature.getQualifyingProperties().getSignedSignatureProperties().setSignatureProductionPlace(
                    new SignatureProductionPlace(context, "Istanbul", "Marmara", "34470", "Turkey"));

            // now sign it by using private key
            signature.sign(PRIVATE_KEY);

            signature.write(new FileOutputStream(BASE_DIR + SIGNATURE_FILENAME));
        }
        catch (XMLSignatureException x){
            // cannot create signature
            x.printStackTrace();
        }
        catch (IOException x){
            // probably couldn't write to the file
            x.printStackTrace();
        }
    }

    @Test
    public void validate() throws Exception {
        Validation.validate(SIGNATURE_FILENAME);
    }
}
