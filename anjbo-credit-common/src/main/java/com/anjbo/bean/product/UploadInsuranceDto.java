/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.product;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.anjbo.bean.BaseDto;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2019-08-09 17:23:33
 * @version 1.0
 */
public class UploadInsuranceDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 上传电子保单 */
	private java.lang.Integer id;
	
	/** 订单编号 */
	private java.lang.String orderNo;
	
	/** 上传电子保单pdf */
	private java.lang.String uploadInsurancePdf;
	
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
	public java.lang.String getUploadInsurancePdf() {
		return uploadInsurancePdf;
	}
	public void setUploadInsurancePdf(java.lang.String uploadInsurancePdf) {
		this.uploadInsurancePdf = uploadInsurancePdf;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public UploadInsuranceDto() {
		super();
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("uploadInsurancePdf",getUploadInsurancePdf())
			.append("remark",getRemark())
			.toString();
	}
}

