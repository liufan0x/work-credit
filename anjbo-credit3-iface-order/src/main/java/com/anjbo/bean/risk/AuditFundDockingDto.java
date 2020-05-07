/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.risk;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:32
 * @version 1.0
 */
@ApiModel(value = "订单审批-资料推送")
public class AuditFundDockingDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 订单审批-资料推送 */
	@ApiModelProperty(value = "订单审批-资料推送")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 推送时间 */
	@ApiModelProperty(value = "推送时间")
	private java.util.Date auditTime;
	
	/** remark */
	@ApiModelProperty(value = "remark")
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
	public void setAuditTime(java.util.Date value) {
		this.auditTime = value;
	}	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("auditTime",getAuditTime())
			.append("remark",getRemark())
			.toString();
	}
}

