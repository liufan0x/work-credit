/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.finance;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@ApiModel(value = "罚息")
public class ReceivablePayDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 罚息 */
	@ApiModelProperty(value = "罚息")
	private java.lang.Integer id;
	
	/** orderNo */
	@ApiModelProperty(value = "orderNo")
	private java.lang.String orderNo;
	
	/** 罚息图片 */
	@ApiModelProperty(value = "罚息图片")
	private java.lang.String payImg;
	
	/** 退费时间/付罚息时间 */
	@ApiModelProperty(value = "退费时间/付罚息时间")
	@JsonFormat( pattern="yyyy-MM-dd")
	private java.util.Date payTime;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** lastUpdateTime */
	@ApiModelProperty(value = "lastUpdateTime")
	private java.util.Date lastUpdateTime;
	
	/** 罚息金额 */
	@ApiModelProperty(value = "罚息金额")
	private Double penaltyPayable;

	@ApiModelProperty(value = "返佣金额")
	private Double rebateMoney;

	@ApiModelProperty(value = "应收罚息")
	private Double penaltyReal;

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
	public void setPayImg(java.lang.String value) {
		this.payImg = value;
	}	
	public java.lang.String getPayImg() {
		return this.payImg;
	}
	public void setPayTime(java.util.Date value) {
		this.payTime = value;
	}	
	public java.util.Date getPayTime() {
		return this.payTime;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	public Double getPenaltyPayable() {
		return penaltyPayable;
	}
	public void setPenaltyPayable(Double penaltyPayable) {
		this.penaltyPayable = penaltyPayable;
	}
	public Double getRebateMoney() {
		return rebateMoney;
	}
	public void setRebateMoney(Double rebateMoney) {
		this.rebateMoney = rebateMoney;
	}
	public Double getPenaltyReal() {
		return penaltyReal;
	}
	public void setPenaltyReal(Double penaltyReal) {
		this.penaltyReal = penaltyReal;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("payImg",getPayImg())
			.append("payTime",getPayTime())
			.append("remark",getRemark())
			.append("lastUpdateTime",getLastUpdateTime())
			.append("penaltyPayable",getPenaltyPayable())
			.toString();
	}
}

