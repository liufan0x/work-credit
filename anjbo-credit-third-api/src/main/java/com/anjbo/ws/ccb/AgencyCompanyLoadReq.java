
package com.anjbo.ws.ccb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agencyCompanyLoadReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agencyCompanyLoadReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tranCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="encryptFlag" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="context" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agencyCompanyLoadReq", propOrder = {
    "tranCode",
    "encryptFlag",
    "context"
})
public class AgencyCompanyLoadReq {

    protected String tranCode;
    protected int encryptFlag;
    protected String context;

    /**
     * Gets the value of the tranCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranCode() {
        return tranCode;
    }

    /**
     * Sets the value of the tranCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranCode(String value) {
        this.tranCode = value;
    }

    /**
     * Gets the value of the encryptFlag property.
     * 
     */
    public int getEncryptFlag() {
        return encryptFlag;
    }

    /**
     * Sets the value of the encryptFlag property.
     * 
     */
    public void setEncryptFlag(int value) {
        this.encryptFlag = value;
    }

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContext(String value) {
        this.context = value;
    }

}
