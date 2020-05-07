/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.process;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:34
 * @version 1.0
 */
@ApiModel(value = "抵押")
public class AppMortgageDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 抵押 */
	@ApiModelProperty(value = "抵押")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 抵押日期 */
	@ApiModelProperty(value = "抵押日期")
	private java.util.Date mortgageTime;
	
	/** 国土局关联tbl_fc_system_land_bureau 中ID */
	@ApiModelProperty(value = "国土局关联tbl_fc_system_land_bureau 中ID")
	private java.lang.String mlandBureau;
	
	/** 抵押开始时间 */
	@ApiModelProperty(value = "抵押开始时间")
	private java.lang.String mortgageStartTime;
	
	/** 抵押结束时间 */
	@ApiModelProperty(value = "抵押结束时间")
	private java.lang.String mortgageEndTime;
	
	/** 国土局驻点uid */
	@ApiModelProperty(value = "国土局驻点uid")
	private java.lang.String mlandBureauUid;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 抵押回执照片 */
	@ApiModelProperty(value = "抵押回执照片")
	private java.lang.String mortgageImg;
	
	/** 国土局名称 */
	@ApiModelProperty(value = "国土局名称")
	private java.lang.String mlandBureauName;
	
	/** 国土局驻点人名称 */
	@ApiModelProperty(value = "国土局驻点人名称")
	private java.lang.String mlandBureauUserName;
	
	/** 抵押机构名称 */
	@ApiModelProperty(value = "抵押机构名称")
	private java.lang.String mortgageName;
	

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
	public void setMortgageTime(java.util.Date value) {
		this.mortgageTime = value;
	}	
	public java.util.Date getMortgageTime() {
		return this.mortgageTime;
	}
	public void setMlandBureau(java.lang.String value) {
		this.mlandBureau = value;
	}	
	public java.lang.String getMlandBureau() {
		return this.mlandBureau;
	}
	public void setMortgageStartTime(java.lang.String value) {
		this.mortgageStartTime = value;
	}	
	public java.lang.String getMortgageStartTime() {
		return this.mortgageStartTime;
	}
	public void setMortgageEndTime(java.lang.String value) {
		this.mortgageEndTime = value;
	}	
	public java.lang.String getMortgageEndTime() {
		return this.mortgageEndTime;
	}
	public void setMlandBureauUid(java.lang.String value) {
		this.mlandBureauUid = value;
	}	
	public java.lang.String getMlandBureauUid() {
		return this.mlandBureauUid;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setMortgageImg(java.lang.String value) {
		this.mortgageImg = value;
	}	
	public java.lang.String getMortgageImg() {
		return this.mortgageImg;
	}
	public void setMlandBureauName(java.lang.String value) {
		this.mlandBureauName = value;
	}	
	public java.lang.String getMlandBureauName() {
		return this.mlandBureauName;
	}
	public void setMlandBureauUserName(java.lang.String value) {
		this.mlandBureauUserName = value;
	}	
	public java.lang.String getMlandBureauUserName() {
		return this.mlandBureauUserName;
	}
	public void setMortgageName(java.lang.String value) {
		this.mortgageName = value;
	}	
	public java.lang.String getMortgageName() {
		return this.mortgageName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("mortgageTime",getMortgageTime())
			.append("mlandBureau",getMlandBureau())
			.append("mortgageStartTime",getMortgageStartTime())
			.append("mortgageEndTime",getMortgageEndTime())
			.append("mlandBureauUid",getMlandBureauUid())
			.append("remark",getRemark())
			.append("mortgageImg",getMortgageImg())
			.append("mlandBureauName",getMlandBureauName())
			.append("mlandBureauUserName",getMlandBureauUserName())
			.append("mortgageName",getMortgageName())
			.toString();
	}
}

