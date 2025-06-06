
package org.etsi.uri.ts102204.v1_1.turktelekom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="StatusType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StatusCode" type="{http://uri.etsi.org/TS102204/v1.1.2#}StatusCodeType"/&gt;
 *         &lt;element name="StatusMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StatusDetail" type="{http://uri.etsi.org/TS102204/v1.1.2#}StatusDetailType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusType", propOrder = {
    "statusCode",
    "statusMessage",
    "statusDetail"
})
public class StatusType {

    @XmlElement(name = "StatusCode", required = true)
    protected StatusCodeType statusCode;
    @XmlElement(name = "StatusMessage")
    protected String statusMessage;
    @XmlElement(name = "StatusDetail")
    protected StatusDetailType statusDetail;

    /**
     * Gets the value of the statusCode property.
     *
     * @return
     *     possible object is
     *     {@link StatusCodeType }
     *
     */
    public StatusCodeType getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     *
     * @param value
     *     allowed object is
     *     {@link StatusCodeType }
     *
     */
    public void setStatusCode(StatusCodeType value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the statusMessage property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the value of the statusMessage property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStatusMessage(String value) {
        this.statusMessage = value;
    }

    /**
     * Gets the value of the statusDetail property.
     *
     * @return
     *     possible object is
     *     {@link StatusDetailType }
     *
     */
    public StatusDetailType getStatusDetail() {
        return statusDetail;
    }

    /**
     * Sets the value of the statusDetail property.
     *
     * @param value
     *     allowed object is
     *     {@link StatusDetailType }
     *
     */
    public void setStatusDetail(StatusDetailType value) {
        this.statusDetail = value;
    }

}
