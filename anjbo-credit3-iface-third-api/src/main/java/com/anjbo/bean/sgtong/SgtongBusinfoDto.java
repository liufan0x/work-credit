/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2019 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.sgtong;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2019-01-04 20:25:42
 * @version 1.0
 */
@ApiModel(value = "业务资料")
public class SgtongBusinfoDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 业务资料 */
	@ApiModelProperty(value = "业务资料")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 分类，关联业务资料分类表 tbl_fc_order_businfo_type */
	@ApiModelProperty(value = "分类，关联业务资料分类表 tbl_fc_order_businfo_type")
	private java.lang.Integer typeId;
	
	/** 资料地址 */
	@ApiModelProperty(value = "资料地址")
	private java.lang.String url;
	
	/** 图片类型 */
	@ApiModelProperty(value = "图片类型")
	private java.lang.String type;
	
	/** 该图片是已经推送过了(1:是,2否) */
	@ApiModelProperty(value = "该图片是已经推送过了(1:是,2否)")
	private java.lang.Integer isPlus;
	
	/** 是否已删除(1:是,2:否) */
	@ApiModelProperty(value = "是否已删除(1:是,2:否)")
	private java.lang.Integer isDelete;
	
	/** sgtProductCode */
	@ApiModelProperty(value = "sgtProductCode")
	private java.lang.String sgtProductCode;
	
	/** sgtProductName */
	@ApiModelProperty(value = "sgtProductName")
	private java.lang.String sgtProductName;
	
	/** 推送陕国投批次编号 */
	@ApiModelProperty(value = "推送陕国投批次编号")
	private java.lang.String batchNo;
	
	/** 推送陕国投预审批ID */
	@ApiModelProperty(value = "推送陕国投预审批ID")
	private java.lang.String prePactNo;
	/** 推送陕国投预审批ID */
	@ApiModelProperty(value = "推送陕国投审批状态")
	private java.lang.String pushStatus;
	
	

	public java.lang.String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(java.lang.String pushStatus) {
		this.pushStatus = pushStatus;
	}

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
	public void setTypeId(java.lang.Integer value) {
		this.typeId = value;
	}	
	public java.lang.Integer getTypeId() {
		return this.typeId;
	}
	public void setUrl(java.lang.String value) {
		this.url = value;
	}	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setType(java.lang.String value) {
		this.type = value;
	}	
	public java.lang.String getType() {
		return this.type;
	}
	public void setIsPlus(java.lang.Integer value) {
		this.isPlus = value;
	}	
	public java.lang.Integer getIsPlus() {
		return this.isPlus;
	}
	public void setIsDelete(java.lang.Integer value) {
		this.isDelete = value;
	}	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setSgtProductCode(java.lang.String value) {
		this.sgtProductCode = value;
	}	
	public java.lang.String getSgtProductCode() {
		return this.sgtProductCode;
	}
	public void setSgtProductName(java.lang.String value) {
		this.sgtProductName = value;
	}	
	public java.lang.String getSgtProductName() {
		return this.sgtProductName;
	}
	public void setBatchNo(java.lang.String value) {
		this.batchNo = value;
	}	
	public java.lang.String getBatchNo() {
		return this.batchNo;
	}
	public void setPrePactNo(java.lang.String value) {
		this.prePactNo = value;
	}	
	public java.lang.String getPrePactNo() {
		return this.prePactNo;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("typeId",getTypeId())
			.append("url",getUrl())
			.append("type",getType())
			.append("isPlus",getIsPlus())
			.append("isDelete",getIsDelete())
			.append("sgtProductCode",getSgtProductCode())
			.append("sgtProductName",getSgtProductName())
			.append("batchNo",getBatchNo())
			.append("prePactNo",getPrePactNo())
			.toString();
	}
}

