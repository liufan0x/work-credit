/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:40:22
 * @version 1.0
 */
@ApiModel(value = "风控工具-定时查档")
public class MonitorArchiveDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 风控工具-定时查档 */
	@ApiModelProperty(value = "风控工具-定时查档")
	private java.lang.Integer id;
	
	/** 订单号 */
	@ApiModelProperty(value = "订单号")
	private java.lang.String orderNo;
	
	/** 查档类型(1分户，2分栋) */
	@ApiModelProperty(value = "查档类型(1分户，2分栋)")
	private java.lang.Integer type;
	
	/** 机构Id */
	@ApiModelProperty(value = "机构Id")
	private java.lang.Integer agencyId;
	
	/** 产权证书类型（0房地产权证书 1不动产权证书） */
	@ApiModelProperty(value = "产权证书类型（0房地产权证书 1不动产权证书）")
	private java.lang.Integer estateType;

	@ApiModelProperty(value = "产权证书类型名称（0房地产权证书 1不动产权证书）")
	private String estateTypeStr;
	
	/** 粤（不动产权证书要用） */
	@ApiModelProperty(value = "粤（不动产权证书要用）")
	private java.lang.String yearNo;
	
	/** 房产证号 */
	@ApiModelProperty(value = "房产证号")
	private java.lang.String estateNo;
	
	/** 身份证号 */
	@ApiModelProperty(value = "身份证号")
	private java.lang.String identityNo;
	
	/** 开始时间 */
	@ApiModelProperty(value = "开始时间")
	private java.util.Date startTime;
	
	/** 结束时间 */
	@ApiModelProperty(value = "结束时间")
	private java.util.Date endTime;
	
	/** 查询频率（最多5次/天） */
	@ApiModelProperty(value = "查询频率（最多5次/天）")
	private java.lang.Integer queryFrequency;
	
	/** 查询用途（关联tbl_fc_system_business_product表id） */
	@ApiModelProperty(value = "查询用途（关联tbl_fc_system_business_product表id）")
	private java.lang.String queryUsage;

	/** 查询用途名称 */
	@ApiModelProperty(value = "查询用途名称")
	private java.lang.String queryUsageStr;
	
	/** 手机号 */
	@ApiModelProperty(value = "手机号")
	private java.lang.String phone;
	
	/** 查档id */
	@ApiModelProperty(value = "查档id")
	private java.lang.String archiveId;
	
	/** 查档结果(红本、抵押在银行、查封) */
	@ApiModelProperty(value = "查档结果(红本、抵押在银行、查封)")
	private java.lang.String message;
	
	/** 第一次查档的产权状态(抵押，查封等) */
	@ApiModelProperty(value = "第一次查档的产权状态(抵押，查封等)")
	private java.lang.String propertyStatus;
	
	/** 变更记录 */
	@ApiModelProperty(value = "变更记录")
	private java.lang.String changeRecord;
	

	/** 查档结果集合(红本、抵押在银行、查封) */
	@ApiModelProperty(value = "查档结果集合(红本、抵押在银行、查封)")
	private List<Map<String, Object>> messages;

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
	public void setType(java.lang.Integer value) {
		this.type = value;
	}	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setAgencyId(java.lang.Integer value) {
		this.agencyId = value;
	}	
	public java.lang.Integer getAgencyId() {
		return this.agencyId;
	}
	public void setEstateType(java.lang.Integer value) {
		this.estateType = value;
	}	
	public java.lang.Integer getEstateType() {
		return this.estateType;
	}
	public void setYearNo(java.lang.String value) {
		this.yearNo = value;
	}	
	public java.lang.String getYearNo() {
		return this.yearNo;
	}
	public void setEstateNo(java.lang.String value) {
		this.estateNo = value;
	}	
	public java.lang.String getEstateNo() {
		return this.estateNo;
	}
	public void setIdentityNo(java.lang.String value) {
		this.identityNo = value;
	}	
	public java.lang.String getIdentityNo() {
		return this.identityNo;
	}
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setQueryFrequency(java.lang.Integer value) {
		this.queryFrequency = value;
	}	
	public java.lang.Integer getQueryFrequency() {
		return this.queryFrequency;
	}
	public void setQueryUsage(java.lang.String value) {
		this.queryUsage = value;
	}	
	public java.lang.String getQueryUsage() {
		return this.queryUsage;
	}
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setArchiveId(java.lang.String value) {
		this.archiveId = value;
	}	
	public java.lang.String getArchiveId() {
		return this.archiveId;
	}
	public void setMessage(java.lang.String value) {
		this.message = value;
	}	
	public java.lang.String getMessage() {
		return this.message;
	}
	public void setPropertyStatus(java.lang.String value) {
		this.propertyStatus = value;
	}	
	public java.lang.String getPropertyStatus() {
		return this.propertyStatus;
	}
	public void setChangeRecord(java.lang.String value) {
		this.changeRecord = value;
	}	
	public java.lang.String getChangeRecord() {
		return this.changeRecord;
	}
	
	public String getEstateTypeStr() {
		return estateTypeStr;
	}
	public void setEstateTypeStr(String estateTypeStr) {
		this.estateTypeStr = estateTypeStr;
	}
	public java.lang.String getQueryUsageStr() {
		return queryUsageStr;
	}
	public void setQueryUsageStr(java.lang.String queryUsageStr) {
		this.queryUsageStr = queryUsageStr;
	}
	public List<Map<String, Object>> getMessages() {
		return messages;
	}
	public void setMessages(List<Map<String, Object>> messages) {
		this.messages = messages;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("type",getType())
			.append("agencyId",getAgencyId())
			.append("estateType",getEstateType())
			.append("yearNo",getYearNo())
			.append("estateNo",getEstateNo())
			.append("identityNo",getIdentityNo())
			.append("startTime",getStartTime())
			.append("endTime",getEndTime())
			.append("queryFrequency",getQueryFrequency())
			.append("queryUsage",getQueryUsage())
			.append("phone",getPhone())
			.append("archiveId",getArchiveId())
			.append("message",getMessage())
			.append("propertyStatus",getPropertyStatus())
			.append("changeRecord",getChangeRecord())
			.toString();
	}
}

