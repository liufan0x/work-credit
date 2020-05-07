/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.finance;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@ApiModel(value = "待放款的订单")
public class LendingDto extends LendingInstructionsDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 待放款的订单 */
	@ApiModelProperty(value = "待放款的订单")
	private java.lang.Integer id;
	
	/** 最后更新时间 */
	@ApiModelProperty(value = "最后更新时间")
	private java.util.Date lastUpdateTime;
	
	/** 放款时间 */
	@ApiModelProperty(value = "放款时间")
	@JsonFormat( pattern="yyyy-MM-dd")
	private java.util.Date lendingTime;
	
	/** 上传截屏 */
	@ApiModelProperty(value = "上传截屏")
	private java.lang.String lendingImg;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 预计回款时间 */
	@ApiModelProperty(value = "预计回款时间")
	private java.util.Date customerPaymentTime;
	
	/** 回款处理人uid */
	@ApiModelProperty(value = "回款处理人uid")
	private java.lang.String receivableForUid;
	
	/** 费用前置后置 */
	@ApiModelProperty(value = "费用前置后置")
	private int isPaymentMethod; 

	public int getIsPaymentMethod() {
		return isPaymentMethod;
	}

	public void setIsPaymentMethod(int isPaymentMethod) {
		this.isPaymentMethod = isPaymentMethod;
	}

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
	public void setLendingTime(java.util.Date value) {
		this.lendingTime = value;
	}	
	public java.util.Date getLendingTime() {
		return this.lendingTime;
	}
	public void setLendingImg(java.lang.String value) {
		this.lendingImg = value;
	}	
	public java.lang.String getLendingImg() {
		return this.lendingImg;
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
	public void setCustomerPaymentTime(java.util.Date value) {
		this.customerPaymentTime = value;
	}	
	public java.util.Date getCustomerPaymentTime() {
		return this.customerPaymentTime;
	}
	public void setReceivableForUid(java.lang.String value) {
		this.receivableForUid = value;
	}	
	public java.lang.String getReceivableForUid() {
		return this.receivableForUid;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("lastUpdateTime",getLastUpdateTime())
			.append("lendingTime",getLendingTime())
			.append("lendingImg",getLendingImg())
			.append("orderNo",getOrderNo())
			.append("remark",getRemark())
			.append("customerPaymentTime",getCustomerPaymentTime())
			.append("receivableForUid",getReceivableForUid())
			.toString();
	}
}

