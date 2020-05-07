/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@ApiModel(value = "计划回款信息")
public class BaseReceivableForDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 计划回款信息 */
	@ApiModelProperty(value = "计划回款信息")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 计划回款时间 */
	@ApiModelProperty(value = "计划回款时间")
	private java.util.Date payMentAmountDate;
	
	/** 回款金额 */
	@ApiModelProperty(value = "回款金额")
	private Long payMentAmount;
	

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
	public void setPayMentAmountDate(java.util.Date value) {
		this.payMentAmountDate = value;
	}	
	public java.util.Date getPayMentAmountDate() {
		return this.payMentAmountDate;
	}
	public void setPayMentAmount(Long value) {
		this.payMentAmount = value;
	}	
	public Long getPayMentAmount() {
		return this.payMentAmount;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("payMentAmountDate",getPayMentAmountDate())
			.append("payMentAmount",getPayMentAmount())
			.toString();
	}
}

