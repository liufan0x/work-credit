package com.anjbo.bean.fc.fund;

import java.util.Date;

/**
 * 带资金审批
 * @author admin
 *
 */
public class AllocationFundAuditDto {

	  private int id; //待资金审批-查询
	  private String createUid ; //提交人uid
	  private Date createTime; //创建时间
	  private String updateUid ; //更新人uid
	  private int updateTime; //更新时间
	  private String orderNo  ; //订单号
	  private Date startTime  ; //开始时间
	  private String startTimeStr;
	  private Date endTime ; //结束时间
	  private String endTimeStr;
	  private int queryFrequency  ; //查询频率（最多5次/天）
	  private String phone  ; //手机号
	  private String remark ;
	  private int dealStatus  ; //放款状态
	  private int grantStatus  ; //勾兑状态
	  private Date lendingTime;  //资金放款时间
	  private String lendingTimeStr;
	  private String dealOpinion;
	 
	  
	public String getDealOpinion() {
		return dealOpinion;
	}
	public void setDealOpinion(String dealOpinion) {
		this.dealOpinion = dealOpinion;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getLendingTimeStr() {
		return lendingTimeStr;
	}
	public void setLendingTimeStr(String lendingTimeStr) {
		this.lendingTimeStr = lendingTimeStr;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(String updateUid) {
		this.updateUid = updateUid;
	}
	public int getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getQueryFrequency() {
		return queryFrequency;
	}
	public void setQueryFrequency(int queryFrequency) {
		this.queryFrequency = queryFrequency;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(int dealStatus) {
		this.dealStatus = dealStatus;
	}
	public int getGrantStatus() {
		return grantStatus;
	}
	public void setGrantStatus(int grantStatus) {
		this.grantStatus = grantStatus;
	}
	  
	  
	  
}
