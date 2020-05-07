package com.anjbo.bean.element.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditInfoVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private Integer id;
	private String title;
	private Date createTime;
	private String customerName;
	private String applierName;
	private String deliverTo;//转交的审批人
	private String orderNo;
	private String sealDepartment;
	private String riskElement;//风控要件描述
	private String receivableElement;//回款要件描述
	private String publicSeal;//借用的公章描述
	private Date beginTime;
	private Date endTime;
	private int state;
	private int type;
	private String creditTypeUid;
	private int orderType;
	
	private boolean hasRead = true; 
	
	public String getTitle() {
		return title;
	}
	public String getCreateTime() {
		if(createTime!=null){
			return fmt.format(createTime);
		}
		return null;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getRiskElement() {
		return riskElement;
	}
	public String getReceivableElement() {
		return receivableElement;
	}
	public String getPublicSeal() {
		return publicSeal;
	}
	public String getBeginTime() {
		if(beginTime!=null){
			return fmt.format(beginTime);
		}
		return null;
	}
	public String getEndTime() {
		if(endTime!=null){
			return fmt.format(endTime);
		}
		return null;
	}
	public int getState() {
		return state;
	}
	public Integer getId() {
		return id;
	}
	public String getSealDepartment() {
		return sealDepartment;
	}
	public int getType() {
		return type;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public boolean isHasRead() {
		return hasRead;
	}
	public int getOrderType() {
		return orderType;
	}
	public String getCreditTypeUid() {
		return creditTypeUid;
	}
	public String getDeliverTo() {
		if(deliverTo!=null){
			return "已转交给"+deliverTo;
		}
		return deliverTo;
	}
	

	
	
	
	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}
	public void setCreditTypeUid(String creditTypeUid) {
		this.creditTypeUid = creditTypeUid;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setSealDepartment(String sealDepartment) {
		this.sealDepartment = sealDepartment;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setRiskElement(String riskElement) {
		this.riskElement = riskElement;
	}
	public void setReceivableElement(String receivableElement) {
		this.receivableElement = receivableElement;
	}
	public void setPublicSeal(String publicSeal) {
		this.publicSeal = publicSeal;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getApplierName() {
		return applierName;
	}
	public void setApplierName(String applierName) {
		this.applierName = applierName;
	}
	@Override
	public String toString() {
		return "AuditInfoVo [fmt=" + fmt + ", id=" + id + ", title=" + title + ", createTime=" + createTime
				+ ", customerName=" + customerName + ", applierName=" + applierName + ", sealDepartment="
				+ sealDepartment + ", riskElement=" + riskElement + ", receivableElement=" + receivableElement
				+ ", publicSeal=" + publicSeal + ", beginTime=" + beginTime + ", endTime=" + endTime + ", state="
				+ state + ", type=" + type + "]";
	}
	
	
}
