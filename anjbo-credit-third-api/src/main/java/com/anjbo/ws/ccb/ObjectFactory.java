
package com.anjbo.ws.ccb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.grape.webservice.server.impl.agency package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AgencyCompanyLoadReqResponse_QNAME = new QName("http://agency.impl.server.webservice.grape.com/", "agencyCompanyLoadReqResponse");
    private final static QName _AgencyCompanyLoadReq_QNAME = new QName("http://agency.impl.server.webservice.grape.com/", "agencyCompanyLoadReq");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.grape.webservice.server.impl.agency
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AgencyCompanyLoadReq }
     * 
     */
    public AgencyCompanyLoadReq createAgencyCompanyLoadReq() {
        return new AgencyCompanyLoadReq();
    }

    /**
     * Create an instance of {@link AgencyCompanyLoadReqResponse }
     * 
     */
    public AgencyCompanyLoadReqResponse createAgencyCompanyLoadReqResponse() {
        return new AgencyCompanyLoadReqResponse();
    }

    /**
     * Create an instance of {@link SecondHouseLoadResp }
     * 
     */
    public SecondHouseLoadResp createSecondHouseLoadResp() {
        return new SecondHouseLoadResp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgencyCompanyLoadReqResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://agency.impl.server.webservice.grape.com/", name = "agencyCompanyLoadReqResponse")
    public JAXBElement<AgencyCompanyLoadReqResponse> createAgencyCompanyLoadReqResponse(AgencyCompanyLoadReqResponse value) {
        return new JAXBElement<AgencyCompanyLoadReqResponse>(_AgencyCompanyLoadReqResponse_QNAME, AgencyCompanyLoadReqResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgencyCompanyLoadReq }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://agency.impl.server.webservice.grape.com/", name = "agencyCompanyLoadReq")
    public JAXBElement<AgencyCompanyLoadReq> createAgencyCompanyLoadReq(AgencyCompanyLoadReq value) {
        return new JAXBElement<AgencyCompanyLoadReq>(_AgencyCompanyLoadReq_QNAME, AgencyCompanyLoadReq.class, null, value);
    }

}
