package com.anjbo.bean.element.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ElementOrderVo implements Serializable{
	
	private static final long serialVersionUID = 4995862107189697116L;
	
	private Integer id;
	private String customerName;
	private Integer borrowingDay;
	private String boxNo;
	private String orderNo;
	private Double borrowingAmount;
	private int orderType;
	private String cityName;
	private String state;
	private int borrowButton;
	private String channelManagerName;
	private String acceptMemberName;
	private String sealDepartment;
	private Integer orderStatus;
	private String creditType;
	private String sealDescrib;
	private String currentBoxElementSet;
	 /**审批类型*/
	private Map<String,Object> typeMap;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getBorrowingDay() {
		return borrowingDay;
	}
	public void setBorrowingDay(Integer borrowingDay) {
		this.borrowingDay = borrowingDay;
	}
	public String getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Double getBorrowingAmount() {
		return borrowingAmount;
	}
	public void setBorrowingAmount(Double borrowingAmount) {
		this.borrowingAmount = borrowingAmount;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getBorrowButton() {
		if(borrowButton<0){//没有权限时设置不显示
			return 1;
		}
		return StringUtils.isNotBlank(currentBoxElementSet)?0:1;
	}
	public void setBorrowButton(int borrowButton) {
		this.borrowButton = borrowButton;
	}
	public String getChannelManagerName() {
		return channelManagerName;
	}
	public void setChannelManagerName(String channelManagerName) {
		this.channelManagerName = channelManagerName;
	}
	public String getAcceptMemberName() {
		return acceptMemberName;
	}
	public void setAcceptMemberName(String acceptMemberName) {
		this.acceptMemberName = acceptMemberName;
	}
	public String getSealDepartment() {
		return sealDepartment;
	}
	public void setSealDepartment(String sealDepartment) {
		this.sealDepartment = sealDepartment;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCreditType() {
		return creditType;
	}
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	public String getSealDescrib() {
		return sealDescrib;
	}
	public void setSealDescrib(String sealDescrib) {
		this.sealDescrib = sealDescrib;
	}
	public String getCurrentBoxElementSet() {
		return currentBoxElementSet;
	}
	public void setCurrentBoxElementSet(String currentBoxElementSet) {
		this.currentBoxElementSet = currentBoxElementSet;
	}
	public Map<String,Object> getTypeMap() {
		return typeMap;
	}
	public void setTypeMap(Map<String,Object> typeMap) {
		this.typeMap = typeMap;
	}
	
}
