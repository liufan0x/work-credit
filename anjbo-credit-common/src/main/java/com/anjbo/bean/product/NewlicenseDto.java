package com.anjbo.bean.product;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 领新证信息 
 * 
 * @author yis
 *
 */
public class NewlicenseDto  extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 领新证 */
	private Integer id;

	/** 订单编号 */
	private String orderNo;

	/** 领新证时间 */
	private Date newlicenseTime;

	/** 国土局 */
	private String nlandBureau;
	
	private String nlandBureauName; //国土局名称
	
	private String nlandBureauUid;  //国土局驻点UId
	
	private String nlandBureauUserName;  //国土局驻点人名称
	private String remark;
	
	private String newlicenseStartTime;  //领新证开始时间
	
	private String newlicenseEndTime;  //领新证结束时间
	
	private String newlicenseImg;  //新房产证照片
	
	private Date mortgageTime; //抵押日期
	/**抵押国土局*/
	private String mlandBureau;
	private String mlandBureauName;  
	
	/**抵押国土局驻点uid*/
	private String mlandBureauUid;
	private String mlandBureauUserName;
	
	private String code;
	
	private String newlicenseTimeStr;
	private String newlicenseStartTimeStr;
	private String newlicenseEndTimeStr;
	private String mortgageTimeStr;
	
	
	public String getNewlicenseStartTimeStr() {
		return newlicenseStartTimeStr;
	}

	public void setNewlicenseStartTimeStr(String newlicenseStartTimeStr) {
		this.newlicenseStartTimeStr = newlicenseStartTimeStr;
	}

	public String getNewlicenseEndTimeStr() {
		return newlicenseEndTimeStr;
	}

	public void setNewlicenseEndTimeStr(String newlicenseEndTimeStr) {
		this.newlicenseEndTimeStr = newlicenseEndTimeStr;
	}

	public String getMortgageTimeStr() {
		return mortgageTimeStr;
	}

	public void setMortgageTimeStr(String mortgageTimeStr) {
		this.mortgageTimeStr = mortgageTimeStr;
	}

	public String getNewlicenseTimeStr() {
		return newlicenseTimeStr;
	}

	public void setNewlicenseTimeStr(String newlicenseTimeStr) {
		this.newlicenseTimeStr = newlicenseTimeStr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMlandBureau() {
		return mlandBureau;
	}

	public void setMlandBureau(String mlandBureau) {
		this.mlandBureau = mlandBureau;
	}

	public String getMlandBureauUid() {
		return mlandBureauUid;
	}

	public void setMlandBureauUid(String mlandBureauUid) {
		this.mlandBureauUid = mlandBureauUid;
	}

	public Date getMortgageTime() {
		return mortgageTime;
	}

	public void setMortgageTime(Date mortgageTime) {
		this.mortgageTime = mortgageTime;
	}

	public String getMlandBureauName() {
		return mlandBureauName;
	}

	public void setMlandBureauName(String mlandBureauName) {
		this.mlandBureauName = mlandBureauName;
	}

	public String getMlandBureauUserName() {
		return mlandBureauUserName;
	}

	public void setMlandBureauUserName(String mlandBureauUserName) {
		this.mlandBureauUserName = mlandBureauUserName;
	}

	

	public String getNlandBureauName() {
		return nlandBureauName;
	}

	public void setNlandBureauName(String nlandBureauName) {
		this.nlandBureauName = nlandBureauName;
	}

	public String getNlandBureauUserName() {
		return nlandBureauUserName;
	}

	public void setNlandBureauUserName(String nlandBureauUserName) {
		this.nlandBureauUserName = nlandBureauUserName;
	}

	public String getNlandBureauUid() {
		return nlandBureauUid;
	}

	public void setNlandBureauUid(String nlandBureauUid) {
		this.nlandBureauUid = nlandBureauUid;
	}

	public String getNewlicenseStartTime() {
		return newlicenseStartTime;
	}

	public void setNewlicenseStartTime(String newlicenseStartTime) {
		this.newlicenseStartTime = newlicenseStartTime;
	}

	public String getNewlicenseEndTime() {
		return newlicenseEndTime;
	}

	public void setNewlicenseEndTime(String newlicenseEndTime) {
		this.newlicenseEndTime = newlicenseEndTime;
	}

	public String getNewlicenseImg() {
		return newlicenseImg;
	}

	public void setNewlicenseImg(String newlicenseImg) {
		this.newlicenseImg = newlicenseImg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getNewlicenseTime() {
		return newlicenseTime;
	}

	public void setNewlicenseTime(Date newlicenseTime) {
		this.newlicenseTime = newlicenseTime;
	}

	public String getNlandBureau() {
		return nlandBureau;
	}

	public void setNlandBureau(String nlandBureau) {
		this.nlandBureau = nlandBureau;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}