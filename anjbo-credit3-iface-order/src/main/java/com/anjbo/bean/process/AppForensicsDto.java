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
@ApiModel(value = "取证")
public class AppForensicsDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 取证 */
	@ApiModelProperty(value = "取证")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 取证员名称 */
	@ApiModelProperty(value = "取证员名称")
	private java.lang.String licenseRever;
	
	/** 取证员Uid */
	@ApiModelProperty(value = "取证员Uid")
	private java.lang.String licenseReverUid;
	
	/** 预计取证时间 */
	@ApiModelProperty(value = "预计取证时间")
	private java.util.Date licenseRevTime;
	
	/** 取证银行 */
	@ApiModelProperty(value = "取证银行")
	private java.lang.Integer licenseRevBank;
	
	/** 取证支行ID */
	@ApiModelProperty(value = "取证支行ID")
	private java.lang.Integer licenseRevBankSub;
	
	/** 取证银行名称 */
	@ApiModelProperty(value = "取证银行名称")
	private java.lang.String licenseRevBankName;
	
	/** 取证支行名称 */
	@ApiModelProperty(value = "取证支行名称")
	private java.lang.String licenseRevBankSubName;
	
	/** 取证开始时间 */
	@ApiModelProperty(value = "取证开始时间")
	private java.lang.String licenseRevStartTime;
	
	/** 取证结束时间 */
	@ApiModelProperty(value = "取证结束时间")
	private java.lang.String licenseRevEndTime;
	
	/** 取证照片 */
	@ApiModelProperty(value = "取证照片")
	private java.lang.String forensiceImg;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 取证是否银行(1:是,2:否) */
	@ApiModelProperty(value = "取证是否银行(1:是,2:否)")
	private java.lang.Integer isLicenseRevBank;
	
	/** 取证地点 */
	@ApiModelProperty(value = "取证地点")
	private java.lang.String licenseRevAddress;
	
	/** 国土局关联tbl_fc_system_land_bureau 中ID */
	@ApiModelProperty(value = "国土局关联tbl_fc_system_land_bureau 中ID")
	private java.lang.String flandBureau;
	
	/** 国土局名称 */
	@ApiModelProperty(value = "国土局名称")
	private java.lang.String flandBureauName;
	

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
	public void setLicenseRever(java.lang.String value) {
		this.licenseRever = value;
	}	
	public java.lang.String getLicenseRever() {
		return this.licenseRever;
	}
	public void setLicenseReverUid(java.lang.String value) {
		this.licenseReverUid = value;
	}	
	public java.lang.String getLicenseReverUid() {
		return this.licenseReverUid;
	}
	public void setLicenseRevTime(java.util.Date value) {
		this.licenseRevTime = value;
	}	
	public java.util.Date getLicenseRevTime() {
		return this.licenseRevTime;
	}
	public void setLicenseRevBank(java.lang.Integer value) {
		this.licenseRevBank = value;
	}	
	public java.lang.Integer getLicenseRevBank() {
		return this.licenseRevBank;
	}
	public void setLicenseRevBankSub(java.lang.Integer value) {
		this.licenseRevBankSub = value;
	}	
	public java.lang.Integer getLicenseRevBankSub() {
		return this.licenseRevBankSub;
	}
	public void setLicenseRevBankName(java.lang.String value) {
		this.licenseRevBankName = value;
	}	
	public java.lang.String getLicenseRevBankName() {
		return this.licenseRevBankName;
	}
	public void setLicenseRevBankSubName(java.lang.String value) {
		this.licenseRevBankSubName = value;
	}	
	public java.lang.String getLicenseRevBankSubName() {
		return this.licenseRevBankSubName;
	}
	public void setLicenseRevStartTime(java.lang.String value) {
		this.licenseRevStartTime = value;
	}	
	public java.lang.String getLicenseRevStartTime() {
		return this.licenseRevStartTime;
	}
	public void setLicenseRevEndTime(java.lang.String value) {
		this.licenseRevEndTime = value;
	}	
	public java.lang.String getLicenseRevEndTime() {
		return this.licenseRevEndTime;
	}
	public void setForensiceImg(java.lang.String value) {
		this.forensiceImg = value;
	}	
	public java.lang.String getForensiceImg() {
		return this.forensiceImg;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setIsLicenseRevBank(java.lang.Integer value) {
		this.isLicenseRevBank = value;
	}	
	public java.lang.Integer getIsLicenseRevBank() {
		return this.isLicenseRevBank;
	}
	public void setLicenseRevAddress(java.lang.String value) {
		this.licenseRevAddress = value;
	}	
	public java.lang.String getLicenseRevAddress() {
		return this.licenseRevAddress;
	}
	public void setFlandBureau(java.lang.String value) {
		this.flandBureau = value;
	}	
	public java.lang.String getFlandBureau() {
		return this.flandBureau;
	}
	public void setFlandBureauName(java.lang.String value) {
		this.flandBureauName = value;
	}	
	public java.lang.String getFlandBureauName() {
		return this.flandBureauName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("licenseRever",getLicenseRever())
			.append("licenseReverUid",getLicenseReverUid())
			.append("licenseRevTime",getLicenseRevTime())
			.append("licenseRevBank",getLicenseRevBank())
			.append("licenseRevBankSub",getLicenseRevBankSub())
			.append("licenseRevBankName",getLicenseRevBankName())
			.append("licenseRevBankSubName",getLicenseRevBankSubName())
			.append("licenseRevStartTime",getLicenseRevStartTime())
			.append("licenseRevEndTime",getLicenseRevEndTime())
			.append("forensiceImg",getForensiceImg())
			.append("remark",getRemark())
			.append("isLicenseRevBank",getIsLicenseRevBank())
			.append("licenseRevAddress",getLicenseRevAddress())
			.append("flandBureau",getFlandBureau())
			.append("flandBureauName",getFlandBureauName())
			.toString();
	}
}

