/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.process;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@ApiModel(value = "过户")
public class AppTransferDto extends AppNewlicenseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 过户 */
	@ApiModelProperty(value = "过户")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 过户时间 */
	@ApiModelProperty(value = "过户时间")
	private java.util.Date transferTime;
	
	/** 国土局关联tbl_fc_system_land_bureau 中ID */
	@ApiModelProperty(value = "国土局关联tbl_fc_system_land_bureau 中ID")
	private java.lang.String tlandBureau;
	
	/** 过户开始时间 */
	@ApiModelProperty(value = "过户开始时间")
	private java.lang.String transferStartTime;
	
	/** 过户结束时间 */
	@ApiModelProperty(value = "过户结束时间")
	private java.lang.String transferEndTime;
	
	/** 国土局驻点uid */
	@ApiModelProperty(value = "国土局驻点uid")
	private java.lang.String tlandBureauUid;
	
	/** 过户回执编号 */
	@ApiModelProperty(value = "过户回执编号")
	private java.lang.String receiptNumber;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 国土局驻点名称 */
	@ApiModelProperty(value = "国土局驻点名称")
	private java.lang.String tlandBureauUserName;
	
	/** 国土局名称 */
	@ApiModelProperty(value = "国土局名称")
	private java.lang.String tlandBureauName;
	
	/** transferImg */
	@ApiModelProperty(value = "transferImg")
	private java.lang.String transferImg;
	

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
	public void setTransferTime(java.util.Date value) {
		this.transferTime = value;
	}	
	public java.util.Date getTransferTime() {
		return this.transferTime;
	}
	public void setTlandBureau(java.lang.String value) {
		this.tlandBureau = value;
	}	
	public java.lang.String getTlandBureau() {
		return this.tlandBureau;
	}
	public void setTransferStartTime(java.lang.String value) {
		this.transferStartTime = value;
	}	
	public java.lang.String getTransferStartTime() {
		return this.transferStartTime;
	}
	public void setTransferEndTime(java.lang.String value) {
		this.transferEndTime = value;
	}	
	public java.lang.String getTransferEndTime() {
		return this.transferEndTime;
	}
	public void setTlandBureauUid(java.lang.String value) {
		this.tlandBureauUid = value;
	}	
	public java.lang.String getTlandBureauUid() {
		return this.tlandBureauUid;
	}
	public void setReceiptNumber(java.lang.String value) {
		this.receiptNumber = value;
	}	
	public java.lang.String getReceiptNumber() {
		return this.receiptNumber;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setTlandBureauUserName(java.lang.String value) {
		this.tlandBureauUserName = value;
	}	
	public java.lang.String getTlandBureauUserName() {
		return this.tlandBureauUserName;
	}
	public void setTlandBureauName(java.lang.String value) {
		this.tlandBureauName = value;
	}	
	public java.lang.String getTlandBureauName() {
		return this.tlandBureauName;
	}
	public void setTransferImg(java.lang.String value) {
		this.transferImg = value;
	}	
	public java.lang.String getTransferImg() {
		return this.transferImg;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("transferTime",getTransferTime())
			.append("tlandBureau",getTlandBureau())
			.append("transferStartTime",getTransferStartTime())
			.append("transferEndTime",getTransferEndTime())
			.append("tlandBureauUid",getTlandBureauUid())
			.append("receiptNumber",getReceiptNumber())
			.append("remark",getRemark())
			.append("tlandBureauUserName",getTlandBureauUserName())
			.append("tlandBureauName",getTlandBureauName())
			.append("transferImg",getTransferImg())
			.toString();
	}
}

