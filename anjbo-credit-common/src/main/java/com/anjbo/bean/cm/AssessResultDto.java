package com.anjbo.bean.cm;

import java.util.Date;

public class AssessResultDto extends AssessDto{
	private int id;  //评估结果反馈表
	private String orderNo;  //订单编号
	private String houseUse;  //房屋用途
	private double totalAmount;  //总值（全值）
	private double netDeedTax;  //净值（扣契税）
	private double netAllTax;  //净值（扣全税）
	private Date createTime;  //创建时间
	private Date assessResultTime;  //评估时间
	private String result;  //评估是否成功
	private String reason;  //原因（若失败则会把失败原因填上）
	private int applyLoanId;//申请按揭id，用于判断是否已申请按揭
	
	public int getApplyLoanId() {
		return applyLoanId;
	}
	public void setApplyLoanId(int applyLoanId) {
		this.applyLoanId = applyLoanId;
	}
	public Date getAssessResultTime() {
		return assessResultTime;
	}
	public void setAssessResultTime(Date assessResultTime) {
		this.assessResultTime = assessResultTime;
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
	public String getHouseUse() {
		return houseUse;
	}
	public void setHouseUse(String houseUse) {
		this.houseUse = houseUse;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getNetDeedTax() {
		return netDeedTax;
	}
	public void setNetDeedTax(double netDeedTax) {
		this.netDeedTax = netDeedTax;
	}
	public double getNetAllTax() {
		return netAllTax;
	}
	public void setNetAllTax(double netAllTax) {
		this.netAllTax = netAllTax;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}