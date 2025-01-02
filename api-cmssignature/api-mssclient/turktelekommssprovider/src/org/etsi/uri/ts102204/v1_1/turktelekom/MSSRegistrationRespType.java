
package org.etsi.uri.ts102204.v1_1.turktelekom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.w3._2001._04.xmlenc_.EncryptedType;


/**
 * <p>Java class for MSS_RegistrationRespType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MSS_RegistrationRespType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://uri.etsi.org/TS102204/v1.1.2#}MessageAbstractType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Status" type="{http://uri.etsi.org/TS102204/v1.1.2#}StatusType"/&gt;
 *         &lt;element name="EncryptedData" type="{http://www.w3.org/2001/04/xmlenc#}EncryptedType" minOccurs="0"/&gt;
 *         &lt;element name="CertificateURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *         &lt;element name="X509Certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="PublicKey" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MSS_RegistrationRespType", propOrder = {
    "status",
    "encryptedData",
    "certificateURI",
    "x509Certificate",
    "publicKey"
})
public class MSSRegistrationRespType
    extends MessageAbstractType
{

    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "EncryptedData")
    protected EncryptedType encryptedData;
    @XmlElement(name = "CertificateURI")
    @XmlSchemaType(name = "anyURI")
    protected String certificateURI;
    @XmlElement(name = "X509Certificate")
    protected byte[] x509Certificate;
    @XmlElement(name = "PublicKey")
    protected byte[] publicKey;

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link StatusType }
     *
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the encryptedData property.
     *
     * @return
     *     possible object is
     *     {@link EncryptedType }
     *
     */
    public EncryptedType getEncryptedData() {
        return encryptedData;
    }

    /**
     * Sets the value of the encryptedData property.
     *
     * @param value
     *     allowed object is
     *     {@link EncryptedType }
     *
     */
    public void setEncryptedData(EncryptedType value) {
        this.encryptedData = value;
    }

    /**
     * Gets the value of the certificateURI property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCertificateURI() {
        return certificateURI;
    }

    /**
     * Sets the value of the certificateURI property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCertificateURI(String value) {
        this.certificateURI = value;
    }

    /**
     * Gets the value of the x509Certificate property.
     *
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509Certificate() {
        return x509Certificate;
    }

    /**
     * Sets the value of the x509Certificate property.
     *
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509Certificate(byte[] value) {
        this.x509Certificate = ((byte[]) value);
    }

    /**
     * Gets the value of the publicKey property.
     *
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPublicKey() {
        return publicKey;
    }

    /**
     * Sets the value of the publicKey property.
     *
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPublicKey(byte[] value) {
        this.publicKey = ((byte[]) value);
    }

}
