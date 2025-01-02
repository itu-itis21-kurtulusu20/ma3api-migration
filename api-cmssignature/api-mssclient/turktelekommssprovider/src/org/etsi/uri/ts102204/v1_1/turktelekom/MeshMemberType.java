
package org.etsi.uri.ts102204.v1_1.turktelekom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MeshMemberType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MeshMemberType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DNSName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IPAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="URI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *         &lt;element name="IdentifierString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeshMemberType", propOrder = {
    "dnsName",
    "ipAddress",
    "uri",
    "identifierString"
})
public class MeshMemberType {

    @XmlElement(name = "DNSName")
    protected String dnsName;
    @XmlElement(name = "IPAddress")
    protected String ipAddress;
    @XmlElement(name = "URI")
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    @XmlElement(name = "IdentifierString")
    protected String identifierString;

    /**
     * Gets the value of the dnsName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDNSName() {
        return dnsName;
    }

    /**
     * Sets the value of the dnsName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDNSName(String value) {
        this.dnsName = value;
    }

    /**
     * Gets the value of the ipAddress property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIPAddress() {
        return ipAddress;
    }

    /**
     * Sets the value of the ipAddress property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIPAddress(String value) {
        this.ipAddress = value;
    }

    /**
     * Gets the value of the uri property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getURI() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setURI(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the identifierString property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdentifierString() {
        return identifierString;
    }

    /**
     * Sets the value of the identifierString property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdentifierString(String value) {
        this.identifierString = value;
    }

}
