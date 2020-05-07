/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.finance;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@ApiModel(value = "待回款的订单")
public class ReceivableForDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 待回款的订单 */
	@ApiModelProperty(value = "待回款的订单")
	private java.lang.Integer id;
	
	/** 最后更新时间 */
	@ApiModelProperty(value = "最后更新时间")
	private java.util.Date lastUpdateTime;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 是否一次性付款（1是2否  ） */
	@ApiModelProperty(value = "是否一次性付款（1是2否  ）")
	private java.lang.Integer oneTimePay;
	
	/** 回款时间(允许有多个) */
	@ApiModelProperty(value = "回款时间(允许有多个)")
	private java.lang.String payMentAmountDate;
	
	/** 回款金额(允许有多个) */
	@ApiModelProperty(value = "回款金额(允许有多个)")
	private Long payMentAmount;
	
	/** 回款照片证明url(允许有多个) */
	@ApiModelProperty(value = "回款照片证明url(允许有多个)")
	private java.lang.String payMentPic;
	
	/** 是否是首期（1:首期  2尾期） */
	@ApiModelProperty(value = "是否是首期（1:首期  2尾期）")
	private java.lang.Integer isFrist;
	
	/** 机构ID */
	@ApiModelProperty(value = "机构ID")
	private java.lang.Integer agencyId;
	
	/** 产品代码 */
	@ApiModelProperty(value = "产品代码")
	private java.lang.String productCode;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setOneTimePay(java.lang.Integer value) {
		this.oneTimePay = value;
	}	
	public java.lang.Integer getOneTimePay() {
		return this.oneTimePay;
	}
	public java.lang.String getPayMentAmountDate() {
		return payMentAmountDate;
	}
	public void setPayMentAmountDate(java.lang.String payMentAmountDate) {
		this.payMentAmountDate = payMentAmountDate;
	}
	public void setPayMentAmount(Long value) {
		this.payMentAmount = value;
	}	
	public Long getPayMentAmount() {
		return this.payMentAmount;
	}
	public void setPayMentPic(java.lang.String value) {
		this.payMentPic = value;
	}	
	public java.lang.String getPayMentPic() {
		return this.payMentPic;
	}
	public void setIsFrist(java.lang.Integer value) {
		this.isFrist = value;
	}	
	public java.lang.Integer getIsFrist() {
		return this.isFrist;
	}
	public void setAgencyId(java.lang.Integer value) {
		this.agencyId = value;
	}	
	public java.lang.Integer getAgencyId() {
		return this.agencyId;
	}
	public void setProductCode(java.lang.String value) {
		this.productCode = value;
	}	
	public java.lang.String getProductCode() {
		return this.productCode;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("lastUpdateTime",getLastUpdateTime())
			.append("orderNo",getOrderNo())
			.append("oneTimePay",getOneTimePay())
			.append("payMentAmountDate",getPayMentAmountDate())
			.append("payMentAmount",getPayMentAmount())
			.append("payMentPic",getPayMentPic())
			.append("isFrist",getIsFrist())
			.append("agencyId",getAgencyId())
			.append("productCode",getProductCode())
			.toString();
	}
}

