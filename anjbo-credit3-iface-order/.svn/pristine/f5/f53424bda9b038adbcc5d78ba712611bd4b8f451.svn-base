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
@ApiModel(value = "待回款其他信息")
public class ReceivableHasDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;

	/** 待回款其他信息 */
	@ApiModelProperty(value = "待回款其他信息")
	private java.lang.Integer id;
	
	/** 最后更新时间 */
	@ApiModelProperty(value = "最后更新时间")
	private java.util.Date lastUpdateTime;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 类型（0准时回款 1提前回款 2逾期回款  ） */
	@ApiModelProperty(value = "类型（0准时回款 1提前回款 2逾期回款  ）")
	private java.lang.Integer type;
	
	/** 退费(0 否    1是)  当类型1提前回款时 */
	@ApiModelProperty(value = "退费(0 否    1是)  当类型1提前回款时")
	private java.lang.Integer refund;
	
	/** 支出金额（包含逾期罚款和提前退费两种情况则一） */
	@ApiModelProperty(value = "支出金额（包含逾期罚款和提前退费两种情况则一）")
	private Double penaltyPayable;
	
	/** 回款情况（提前5天或者逾期） */
	@ApiModelProperty(value = "回款情况（提前5天或者逾期）")
	private java.lang.Integer datediff;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	

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
	public void setType(java.lang.Integer value) {
		this.type = value;
	}	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setRefund(java.lang.Integer value) {
		this.refund = value;
	}	
	public java.lang.Integer getRefund() {
		return this.refund;
	}
	public Double getPenaltyPayable() {
		return penaltyPayable;
	}
	public void setPenaltyPayable(Double penaltyPayable) {
		this.penaltyPayable = penaltyPayable;
	}
	public void setDatediff(java.lang.Integer value) {
		this.datediff = value;
	}	
	public java.lang.Integer getDatediff() {
		return this.datediff;
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
			.append("lastUpdateTime",getLastUpdateTime())
			.append("orderNo",getOrderNo())
			.append("type",getType())
			.append("refund",getRefund())
			.append("penaltyPayable",getPenaltyPayable())
			.append("datediff",getDatediff())
			.append("remark",getRemark())
			.toString();
	}
}

