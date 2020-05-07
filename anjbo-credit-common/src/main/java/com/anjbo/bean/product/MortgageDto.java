package com.anjbo.bean.product;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 抵押信息
 * 
 * @author yis
 *
 */
public class MortgageDto  extends ForensicsDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**抵押日期*/
	private Date mortgageTime;

	/**国土局*/
	private String mlandBureau;
	
	/**国土局*/
	private String mlandBureauName;

	/**国土局驻点uid*/
	private String mlandBureauUid;

	/**国土局驻点名称*/
	private String mlandBureauUserName;

	private String mortgageImg;   //抵押回执照片
	
	private String mortgageStartTime;   //抵押开始时间
	
	private String mortgageEndTime;  //抵押结束时间
	
	
	private String mortgageTimeStr;
	private String mortgageStartTimeStr;
	private String mortgageEndTimeStr;
	
	private String mortgageName; //抵押机构名称
	
	public String getMortgageName() {
		return mortgageName;
	}

	public void setMortgageName(String mortgageName) {
		this.mortgageName = mortgageName;
	}

	public String getMortgageTimeStr() {
		return mortgageTimeStr;
	}

	public void setMortgageTimeStr(String mortgageTimeStr) {
		this.mortgageTimeStr = mortgageTimeStr;
	}

	public String getMortgageStartTimeStr() {
		return mortgageStartTimeStr;
	}

	public void setMortgageStartTimeStr(String mortgageStartTimeStr) {
		this.mortgageStartTimeStr = mortgageStartTimeStr;
	}

	public String getMortgageEndTimeStr() {
		return mortgageEndTimeStr;
	}

	public void setMortgageEndTimeStr(String mortgageEndTimeStr) {
		this.mortgageEndTimeStr = mortgageEndTimeStr;
	}

	public String getMortgageImg() {
		return mortgageImg;
	}

	public void setMortgageImg(String mortgageImg) {
		this.mortgageImg = mortgageImg;
	}

	public String getMortgageStartTime() {
		return mortgageStartTime;
	}

	public void setMortgageStartTime(String mortgageStartTime) {
		this.mortgageStartTime = mortgageStartTime;
	}

	public String getMortgageEndTime() {
		return mortgageEndTime;
	}

	public void setMortgageEndTime(String mortgageEndTime) {
		this.mortgageEndTime = mortgageEndTime;
	}

	public Date getMortgageTime() {
		return mortgageTime;
	}

	public void setMortgageTime(Date mortgageTime) {
		this.mortgageTime = mortgageTime;
	}

	public String getMlandBureau() {
		return mlandBureau;
	}

	public void setMlandBureau(String mlandBureau) {
		this.mlandBureau = mlandBureau;
	}

	public String getMlandBureauName() {
		return mlandBureauName;
	}

	public void setMlandBureauName(String mlandBureauName) {
		this.mlandBureauName = mlandBureauName;
	}

	public String getMlandBureauUid() {
		return mlandBureauUid;
	}

	public void setMlandBureauUid(String mlandBureauUid) {
		this.mlandBureauUid = mlandBureauUid;
	}

	public String getMlandBureauUserName() {
		return mlandBureauUserName;
	}

	public void setMlandBureauUserName(String mlandBureauUserName) {
		this.mlandBureauUserName = mlandBureauUserName;
	}



	
}