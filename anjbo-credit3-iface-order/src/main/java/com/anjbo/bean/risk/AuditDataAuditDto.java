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
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@ApiModel(value = "订单审批-资料审核")
public class AuditDataAuditDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 订单审批-资料审核 */
	@ApiModelProperty(value = "订单审批-资料审核")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 审核时间 */
	@ApiModelProperty(value = "审核时间")
	private java.util.Date auditTime;
	
	/** 名称 */
	@ApiModelProperty(value = "名称")
	private java.lang.String name;

	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
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
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setAuditTime(java.util.Date value) {
		this.auditTime = value;
	}	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("remark",getRemark())
			.append("auditTime",getAuditTime())
			.toString();
	}
}

