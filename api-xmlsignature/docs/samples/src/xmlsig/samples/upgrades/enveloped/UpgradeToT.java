package xmlsig.samples.upgrades.enveloped;

import org.junit.Test;
import org.w3c.dom.Element;
import tr.gov.tubitak.uekae.esya.api.signature.SignatureType;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.Context;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.XMLSignature;
import xmlsig.samples.utils.SampleBase;
import xmlsig.samples.validation.Validation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Provides functions for upgrade of enveloped BES to type T
 * @author suleyman.uslu
 */
public class UpgradeToT extends SampleBase {

    public static final String SIGNATURE_FILENAME = "t_from_bes_enveloped.xml";

    /**
     * Upgrades enveloped BES to type T
     * @throws Exception
     */
    @Test
    public void upgradeBESToT() throws Exception {

        // create context with working directory
        Context context = new Context(BASE_DIR);

        // parse the previously created enveloped signature
        org.w3c.dom.Document document = parseDoc("enveloped.xml", context);

        // get the signature in DOM document
        XMLSignature signature = readSignature(document, context);

        // upgrade the signature to type T
        signature.upgrade(SignatureType.ES_T);

        // writing enveloped XML to the file
        Source source = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, new StreamResult(new FileOutputStream(BASE_DIR + SIGNATURE_FILENAME)));
    }

    @Test
    public void validate() throws Exception {
        Validation.validate(SIGNATURE_FILENAME);
    }

    /**
     * Reads an XML document into DOM document format
     * @param uri XML file to be read
     * @param aContext signature context
     * @return DOM document format of read XML document
     * @throws Exception
     */
    private org.w3c.dom.Document parseDoc(String uri, Context aContext) throws Exception {

        // generate document builders for parsing
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        // open the document
        File f = new File(BASE_DIR + uri);

        // parse into DOM format
        org.w3c.dom.Document document = db.parse(f);
        aContext.setDocument(document);

        return document;
    }

    /**
     * Gets the signature by searching for tag in an XML document
     * @param aDocument XML document to be looked for
     * @param aContext signature context
     * @return XML signature in the XML document
     * @throws Exception
     */
    private XMLSignature readSignature(org.w3c.dom.Document aDocument, Context aContext) throws Exception {

        // get the signature in enveloped signature format
        Element signatureElement = ((Element)aDocument.getDocumentElement().getElementsByTagName("ds:Signature").item(0));

        // return the XML signature created with signature element
        return new XMLSignature(signatureElement, aContext);
    }

}
