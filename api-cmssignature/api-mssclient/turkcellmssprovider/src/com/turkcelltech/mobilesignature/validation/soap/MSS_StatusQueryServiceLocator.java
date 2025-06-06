/**
 * MSS_StatusQueryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.turkcelltech.mobilesignature.validation.soap;

import java.util.logging.Logger;

public class MSS_StatusQueryServiceLocator extends org.apache.axis.client.Service implements com.turkcelltech.mobilesignature.validation.soap.MSS_StatusQueryService {

    public MSS_StatusQueryServiceLocator() {
    }


    public MSS_StatusQueryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MSS_StatusQueryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MSS_StatusPort
    //private java.lang.String MSS_StatusPort_address = "http://mobilimza.corbuss.com.tr/MSSP/services/MSS_StatusPort";
    private java.lang.String MSS_StatusPort_address = "https://msign.turkcell.com.tr/MSSP2/services/MSS_StatusPort";
    // this default address is changed to MSSP2 and will be tested

    public java.lang.String getMSS_StatusPortAddress() {
        return MSS_StatusPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MSS_StatusPortWSDDServiceName = "MSS_StatusPort";

    public java.lang.String getMSS_StatusPortWSDDServiceName() {
        return MSS_StatusPortWSDDServiceName;
    }

    public void setMSS_StatusPortWSDDServiceName(java.lang.String name) {
        MSS_StatusPortWSDDServiceName = name;
    }

    public com.turkcelltech.mobilesignature.validation.soap.MSS_StatusQueryPortType getMSS_StatusPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MSS_StatusPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMSS_StatusPort(endpoint);
    }

    public com.turkcelltech.mobilesignature.validation.soap.MSS_StatusQueryPortType getMSS_StatusPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.turkcelltech.mobilesignature.validation.soap.MSS_StatusQueryBindingStub _stub = new com.turkcelltech.mobilesignature.validation.soap.MSS_StatusQueryBindingStub(portAddress, this);
            _stub.setPortName(getMSS_StatusPortWSDDServiceName());
            return _stub;
        }
        catch (Exception e) {
            return null;
        }
    }

    public void setMSS_StatusPortEndpointAddress(java.lang.String address) {
        MSS_StatusPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.turkcelltech.mobilesignature.validation.soap.MSS_StatusQueryPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.turkcelltech.mobilesignature.validation.soap.MSS_StatusQueryBindingStub _stub = new com.turkcelltech.mobilesignature.validation.soap.MSS_StatusQueryBindingStub(new java.net.URL(MSS_StatusPort_address), this);
                _stub.setPortName(getMSS_StatusPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Exception e) {
            throw new javax.xml.rpc.ServiceException(e);
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
        if ("MSS_StatusPort".equals(inputPortName)) {
            return getMSS_StatusPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://turkcelltech.com/mobilesignature/validation/soap", "MSS_StatusQueryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://turkcelltech.com/mobilesignature/validation/soap", "MSS_StatusPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MSS_StatusPort".equals(portName)) {
            setMSS_StatusPortEndpointAddress(address);
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
