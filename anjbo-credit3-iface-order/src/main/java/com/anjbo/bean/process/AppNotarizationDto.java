/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.process;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@ApiModel(value = "公证")
public class AppNotarizationDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;

	/** 公证 */
	@ApiModelProperty(value = "公证")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 公证日期 */
	@ApiModelProperty(value = "公证日期")
	@JsonFormat( pattern="yyyy-MM-dd")
	private Date notarizationTime;
	
	/** 公证照片 */
	@ApiModelProperty(value = "公证照片")
	private String notarizationImg;
	
	/** 预计出款日期 */
	@ApiModelProperty(value = "预计出款日期")
	@JsonFormat( pattern="yyyy-MM-dd")
	private Date estimatedTime;
	
	/** 公证地址id */
	@ApiModelProperty(value = "公证地址id")
	private java.lang.String notarizationAddressCode;
	
	/** 公证地点(公证局名称) 和 notaryOfficeId对应 */
	@ApiModelProperty(value = "公证地点(公证局名称) 和 notaryOfficeId对应")
	private java.lang.String notarizationAddress;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 公证类型 */
	@ApiModelProperty(value = "公证类型")
	private java.lang.String notarizationType;
	

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
	public void setNotarizationImg(java.lang.String value) {
		this.notarizationImg = value;
	}	
	public java.lang.String getNotarizationImg() {
		return this.notarizationImg;
	}
	public Date getNotarizationTime() {
		return notarizationTime;
	}
	public void setNotarizationTime(Date notarizationTime) {
		this.notarizationTime = notarizationTime;
	}
	public Date getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(Date estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public void setNotarizationAddressCode(java.lang.String value) {
		this.notarizationAddressCode = value;
	}	
	public java.lang.String getNotarizationAddressCode() {
		return this.notarizationAddressCode;
	}
	public void setNotarizationAddress(java.lang.String value) {
		this.notarizationAddress = value;
	}	
	public java.lang.String getNotarizationAddress() {
		return this.notarizationAddress;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setNotarizationType(java.lang.String value) {
		this.notarizationType = value;
	}	
	public java.lang.String getNotarizationType() {
		return this.notarizationType;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("notarizationTime",getNotarizationTime())
			.append("notarizationImg",getNotarizationImg())
			.append("estimatedTime",getEstimatedTime())
			.append("notarizationAddressCode",getNotarizationAddressCode())
			.append("notarizationAddress",getNotarizationAddress())
			.append("remark",getRemark())
			.append("notarizationType",getNotarizationType())
			.toString();
	}
}

