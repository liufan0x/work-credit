package com.anjbo.thirdInterface.tzc;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

@Service
public class ServiceSoapProxy implements ServiceSoap {
	private String _endpoint = null;
	private ServiceSoap serviceSoap = null;

	public ServiceSoapProxy() {
		_initServiceSoapProxy();
	}

	public ServiceSoapProxy(String endpoint) {
		_endpoint = endpoint;
		_initServiceSoapProxy();
	}

	private void _initServiceSoapProxy() {
		try {
			serviceSoap = (new ServiceLocator()).getServiceSoap();
			if (serviceSoap != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) serviceSoap)
					._setProperty(
							"javax.xml.rpc.service.endpoint.address",
							_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) serviceSoap)
					._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (serviceSoap != null)
			((javax.xml.rpc.Stub) serviceSoap)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public ServiceSoap getServiceSoap() {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap;
	}

	public int changePWD(java.lang.String loginID, java.lang.String oldPWD,
			java.lang.String newPWD) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.changePWD(loginID, oldPWD, newPWD);
	}

	public java.lang.String xjQueryReport(java.lang.String userID,
			java.lang.String password, java.lang.String queryCondition,
			java.lang.String returnStyle) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.xjQueryReport(userID, password, queryCondition,
				returnStyle);
	}

	public java.lang.String tzcQueryReport(java.lang.String userID,
			java.lang.String password, java.lang.String queryCondition,
			java.lang.String returnStyle) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryReport(userID, password, queryCondition,
				returnStyle);
	}

	public java.lang.String tzcResultReport(java.lang.String userID,
			java.lang.String password, java.lang.String queryCondition,
			java.lang.String returnStyle) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcResultReport(userID, password, queryCondition,
				returnStyle);
	}

	public java.lang.String tzcCountReport(java.lang.String userID,
			java.lang.String password, java.lang.String sDate,
			java.lang.String eDate) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcCountReport(userID, password, sDate, eDate);
	}

	public java.lang.String tzcQueryBank(java.lang.String userID,
			java.lang.String password, java.lang.String bankId)
	throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryBank(userID, password, bankId);
	}

	public java.lang.String tzcQuerySubBank(java.lang.String userID,
			java.lang.String password, java.lang.String bankId,
			java.lang.String subBankId) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQuerySubBank(userID, password, bankId, subBankId);
	}

	public java.lang.String tzcQueryBankManager(java.lang.String userID,
			java.lang.String password, java.lang.String managerName,
			java.lang.String phone) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryBankManager(userID, password, managerName,
				phone);
	}

	public java.lang.String tzcManagerReg(java.lang.String userID,
			java.lang.String password, java.lang.String bankId,
			java.lang.String subBankId, java.lang.String managerName,
			java.lang.String phone) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcManagerReg(userID, password, bankId, subBankId,
				managerName, phone);
	}

	public java.lang.String tzcQueryEstate(java.lang.String sKey)
	throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryEstate(sKey);
	}

	public java.lang.String tzcQueryBuilding(java.lang.String estateId,
			java.lang.String sKey) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryBuilding(estateId, sKey);
	}

	public java.lang.String tzcQueryFlag(java.lang.String buildingId,
			java.lang.String sKey) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryFlag(buildingId, sKey);
	}

	public java.lang.String tzcQueryTax(java.lang.String userID,
			java.lang.String password, java.lang.String queryCondition,
			java.lang.String returnStyle) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryTax(userID, password, queryCondition,
				returnStyle);
	}

	public String tzcLimitApply(String userID, String password,
			String queryCondition, String returnStyle) throws RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcLimitApply(userID, password, queryCondition,
				returnStyle);
	}

	public String tzcWebReportApply(String userID, String password,
			String queryCondition, String returnStyle) throws RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcWebReportApply(userID, password, queryCondition,
				returnStyle);
	}

	public java.lang.String tzcQueryEstate2(java.lang.String sKey,java.lang.String cityId)
	throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryEstate2(sKey,cityId);
	}

	public java.lang.String tzcQueryBuilding2(java.lang.String estateId,
			java.lang.String sKey,java.lang.String cityId) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryBuilding2(estateId, sKey,cityId);
	}

	public java.lang.String tzcQueryFlag2(java.lang.String buildingId,
			java.lang.String sKey,java.lang.String cityId) throws java.rmi.RemoteException {
		if (serviceSoap == null)
			_initServiceSoapProxy();
		return serviceSoap.tzcQueryFlag2(buildingId, sKey,cityId);
	}

}