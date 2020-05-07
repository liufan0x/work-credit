package com.anjbo.bean.cm;

import java.util.Date;


public class AssessExtendDto extends AssessDto{
	private int id;  //评估基本表
	private String uid; //操作人UID
	private String orderNo;  //订单编号
	private double loanAmount;  //贷款金额
	private double transactionPrice;  //成交价
	private double houseArea;  //房屋面积
	private String userName;// 用户姓名
	private String userMobile;// 用户手机号
	private Date createTime;// 创建时间
	private String cause;//关闭的原因
	private String loanAmountUpdate;//云按揭后台修改app提单贷额度
	
	private String propertyId;  //物业id
	private String buildingId;  //楼栋id
	private String roomId;  //房号id
	private double area;//面积
	
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public double getTransactionPrice() {
		return transactionPrice;
	}
	public void setTransactionPrice(double transactionPrice) {
		this.transactionPrice = transactionPrice;
	}
	public double getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(double houseArea) {
		this.houseArea = houseArea;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getLoanAmountUpdate() {
		return loanAmountUpdate;
	}
	public void setLoanAmountUpdate(String loanAmountUpdate) {
		this.loanAmountUpdate = loanAmountUpdate;
	}
	
	
}