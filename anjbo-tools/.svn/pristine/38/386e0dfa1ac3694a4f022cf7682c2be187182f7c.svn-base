/**
 * FanhuaAutoPriceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.anjbo.thirdInterface.sl;

public class FanhuaAutoPriceLocator extends org.apache.axis.client.Service implements com.anjbo.thirdInterface.sl.FanhuaAutoPrice {

    public FanhuaAutoPriceLocator() {
    }


    public FanhuaAutoPriceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FanhuaAutoPriceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IFanhuaAutoPrice
    private java.lang.String BasicHttpBinding_IFanhuaAutoPrice_address = "http://io.worldunion.cn:8306/FanhuaAutoPrice.svc";

    public java.lang.String getBasicHttpBinding_IFanhuaAutoPriceAddress() {
        return BasicHttpBinding_IFanhuaAutoPrice_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IFanhuaAutoPriceWSDDServiceName = "BasicHttpBinding_IFanhuaAutoPrice";

    public java.lang.String getBasicHttpBinding_IFanhuaAutoPriceWSDDServiceName() {
        return BasicHttpBinding_IFanhuaAutoPriceWSDDServiceName;
    }

    public void setBasicHttpBinding_IFanhuaAutoPriceWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IFanhuaAutoPriceWSDDServiceName = name;
    }

    public com.anjbo.thirdInterface.sl.IFanhuaAutoPrice getBasicHttpBinding_IFanhuaAutoPrice() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IFanhuaAutoPrice_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IFanhuaAutoPrice(endpoint);
    }

    public com.anjbo.thirdInterface.sl.IFanhuaAutoPrice getBasicHttpBinding_IFanhuaAutoPrice(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.anjbo.thirdInterface.sl.BasicHttpBinding_IFanhuaAutoPriceStub _stub = new com.anjbo.thirdInterface.sl.BasicHttpBinding_IFanhuaAutoPriceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IFanhuaAutoPriceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IFanhuaAutoPriceEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IFanhuaAutoPrice_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.anjbo.thirdInterface.sl.IFanhuaAutoPrice.class.isAssignableFrom(serviceEndpointInterface)) {
                com.anjbo.thirdInterface.sl.BasicHttpBinding_IFanhuaAutoPriceStub _stub = new com.anjbo.thirdInterface.sl.BasicHttpBinding_IFanhuaAutoPriceStub(new java.net.URL(BasicHttpBinding_IFanhuaAutoPrice_address), this);
                _stub.setPortName(getBasicHttpBinding_IFanhuaAutoPriceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IFanhuaAutoPrice".equals(inputPortName)) {
            return getBasicHttpBinding_IFanhuaAutoPrice();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "FanhuaAutoPrice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IFanhuaAutoPrice"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IFanhuaAutoPrice".equals(portName)) {
            setBasicHttpBinding_IFanhuaAutoPriceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
