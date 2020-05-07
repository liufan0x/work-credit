/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.product;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.anjbo.bean.BaseDto;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2019-08-09 17:23:33
 * @version 1.0
 */
public class SignInsuranceDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 签订投保单 */
	private java.lang.Integer id;
	
	/** 订单编号 */
	private java.lang.String orderNo;
	
	/** 签订时间 */
	private Date signTime;
	
	/** 上传截屏 */
	private java.lang.String signImg;
	
	/** 备注 */
	private java.lang.String remark;
	
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public java.lang.String getSignImg() {
		return signImg;
	}
	public void setSignImg(java.lang.String signImg) {
		this.signImg = signImg;
	}
	
	public SignInsuranceDto() {
		super();
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("signTime",getSignTime())
			.append("signImg",getSignImg())
			.append("remark",getRemark())
			.toString();
	}
}

