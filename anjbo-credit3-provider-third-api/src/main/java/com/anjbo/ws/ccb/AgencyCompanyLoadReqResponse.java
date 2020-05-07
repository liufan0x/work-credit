
package com.anjbo.ws.ccb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agencyCompanyLoadReqResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agencyCompanyLoadReqResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="secondHouseLoadReqResult" type="{http://agency.impl.server.webservice.grape.com/}secondHouseLoadResp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agencyCompanyLoadReqResponse", propOrder = {
    "secondHouseLoadReqResult"
})
public class AgencyCompanyLoadReqResponse {

    protected SecondHouseLoadResp secondHouseLoadReqResult;

    /**
     * Gets the value of the secondHouseLoadReqResult property.
     * 
     * @return
     *     possible object is
     *     {@link SecondHouseLoadResp }
     *     
     */
    public SecondHouseLoadResp getSecondHouseLoadReqResult() {
        return secondHouseLoadReqResult;
    }

    /**
     * Sets the value of the secondHouseLoadReqResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecondHouseLoadResp }
     *     
     */
    public void setSecondHouseLoadReqResult(SecondHouseLoadResp value) {
        this.secondHouseLoadReqResult = value;
    }

}
