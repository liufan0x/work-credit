package com.anjbo.thirdInterface.sl;

import org.springframework.stereotype.Service;

@Service(value="fanhuaAutoPriceProxy")
public class IFanhuaAutoPriceProxy implements com.anjbo.thirdInterface.sl.IFanhuaAutoPrice {
  private String _endpoint = null;
  private com.anjbo.thirdInterface.sl.IFanhuaAutoPrice iFanhuaAutoPrice = null;
  
  public IFanhuaAutoPriceProxy() {
    _initIFanhuaAutoPriceProxy();
  }
  
  public IFanhuaAutoPriceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIFanhuaAutoPriceProxy();
  }
  
  private void _initIFanhuaAutoPriceProxy() {
    try {
      iFanhuaAutoPrice = (new com.anjbo.thirdInterface.sl.FanhuaAutoPriceLocator()).getBasicHttpBinding_IFanhuaAutoPrice();
      if (iFanhuaAutoPrice != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iFanhuaAutoPrice)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iFanhuaAutoPrice)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iFanhuaAutoPrice != null)
      ((javax.xml.rpc.Stub)iFanhuaAutoPrice)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.anjbo.thirdInterface.sl.IFanhuaAutoPrice getIFanhuaAutoPrice() {
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice;
  }
  
  public java.lang.String getProvince(java.lang.String userName, java.lang.String pwd) throws java.rmi.RemoteException{
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice.getProvince(userName, pwd);
  }
  
  public java.lang.String getCity(java.lang.Integer provinceid, java.lang.String userName, java.lang.String pwd) throws java.rmi.RemoteException{
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice.getCity(provinceid, userName, pwd);
  }
  
  public java.lang.String getConstruction(java.lang.String key, java.lang.Integer cityid, java.lang.String userName, java.lang.String pwd) throws java.rmi.RemoteException{
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice.getConstruction(key, cityid, userName, pwd);
  }
  
  public java.lang.String getBuilding(java.lang.Integer constructionid, java.lang.Integer cityid, java.lang.String userName, java.lang.String pwd) throws java.rmi.RemoteException{
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice.getBuilding(constructionid, cityid, userName, pwd);
  }
  
  public java.lang.String getHouse(java.lang.Integer buildingId, java.lang.Integer cityid, java.lang.String userName, java.lang.String pwd) throws java.rmi.RemoteException{
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice.getHouse(buildingId, cityid, userName, pwd);
  }
  
  public java.lang.String getAutoPrice(java.lang.Integer cityid, java.lang.Integer cid, java.lang.Integer bid, java.lang.Integer hid, java.lang.Double area, java.lang.String userName, java.lang.String pwd) throws java.rmi.RemoteException{
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice.getAutoPrice(cityid, cid, bid, hid, area, userName, pwd);
  }
  
  public java.lang.String getAutoPriceHistory(java.util.Calendar startdate, java.util.Calendar endDate, java.lang.String constructionKey, java.lang.Integer cityid, java.lang.Integer areaid, java.lang.Integer regionid, java.lang.String userName, java.lang.String pwd) throws java.rmi.RemoteException{
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice.getAutoPriceHistory(startdate, endDate, constructionKey, cityid, areaid, regionid, userName, pwd);
  }
  
  public java.lang.String getArea(java.lang.Integer cityid, java.lang.String userName, java.lang.String pwd) throws java.rmi.RemoteException{
    if (iFanhuaAutoPrice == null)
      _initIFanhuaAutoPriceProxy();
    return iFanhuaAutoPrice.getArea(cityid, userName, pwd);
  }
  
  
}