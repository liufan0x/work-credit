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
@ApiModel(value = "返佣信息")
public class RebateDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 返佣信息 */
	@ApiModelProperty(value = "返佣信息")
	private java.lang.Integer id;
	
	/** 最后更新时间 */
	@ApiModelProperty(value = "最后更新时间")
	private java.util.Date lastUpdateTime;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 返佣时间 */
	@ApiModelProperty(value = "返佣时间")
	@JsonFormat( pattern="yyyy-MM-dd")
	private java.util.Date rebateTime;
	
	/** 返佣图片 */
	@ApiModelProperty(value = "返佣图片")
	private java.lang.String rebateImg;
	
	/** 返佣金额 */
	@ApiModelProperty(value = "返佣金额")
	private Double rebateMoney;
	
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
	public void setRebateTime(java.util.Date value) {
		this.rebateTime = value;
	}	
	public java.util.Date getRebateTime() {
		return this.rebateTime;
	}
	public void setRebateImg(java.lang.String value) {
		this.rebateImg = value;
	}	
	public java.lang.String getRebateImg() {
		return this.rebateImg;
	}
	public Double getRebateMoney() {
		return rebateMoney;
	}
	public void setRebateMoney(Double rebateMoney) {
		this.rebateMoney = rebateMoney;
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
			.append("rebateTime",getRebateTime())
			.append("rebateImg",getRebateImg())
			.append("rebateMoney",getRebateMoney())
			.append("remark",getRemark())
			.toString();
	}
}

