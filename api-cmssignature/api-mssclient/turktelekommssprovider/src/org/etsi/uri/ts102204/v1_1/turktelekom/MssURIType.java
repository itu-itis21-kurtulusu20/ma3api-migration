
package org.etsi.uri.ts102204.v1_1.turktelekom;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for mssURIType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="mssURIType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mssURI" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="DigestAlgAndValue" type="{http://uri.etsi.org/TS102204/v1.1.2#}DigestAlgAndValueType" minOccurs="0"/&gt;
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mssURIType", propOrder = {
    "mssURI",
    "digestAlgAndValue",
    "any"
})
public class MssURIType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String mssURI;
    @XmlElement(name = "DigestAlgAndValue")
    protected DigestAlgAndValueType digestAlgAndValue;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the mssURI property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMssURI() {
        return mssURI;
    }

    /**
     * Sets the value of the mssURI property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMssURI(String value) {
        this.mssURI = value;
    }

    /**
     * Gets the value of the digestAlgAndValue property.
     *
     * @return
     *     possible object is
     *     {@link DigestAlgAndValueType }
     *
     */
    public DigestAlgAndValueType getDigestAlgAndValue() {
        return digestAlgAndValue;
    }

    /**
     * Sets the value of the digestAlgAndValue property.
     *
     * @param value
     *     allowed object is
     *     {@link DigestAlgAndValueType }
     *
     */
    public void setDigestAlgAndValue(DigestAlgAndValueType value) {
        this.digestAlgAndValue = value;
    }

    /**
     * Gets the value of the any property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     *
     *
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

}
