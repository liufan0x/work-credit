/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.finance;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.order.BaseBorrowDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@ApiModel(value = "待核实利息的订单")
public class LendingHarvestDto extends BaseBorrowDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 待核实利息的订单 */
	@ApiModelProperty(value = "待核实利息的订单")
	private java.lang.Integer id;
	
	/** 最后更新时间 */
	@ApiModelProperty(value = "最后更新时间")
	private java.util.Date lastUpdateTime;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 收取利息时间 */
	@ApiModelProperty(value = "收取利息时间")
	@JsonFormat( pattern="yyyy-MM-dd")
	private java.util.Date interestTime;
	
	/** 上传截屏 */
	@ApiModelProperty(value = "上传截屏")
	private java.lang.String interestImg;
	
	/** 实收利息金额 */
	@ApiModelProperty(value = "实收利息金额")
	private java.lang.Double collectInterestMoney;
	
	/** 应付利息金额 */
	@ApiModelProperty(value = "应付利息金额")
	private java.lang.Double payInterestMoney;
	
	/** 返佣金额 */
	@ApiModelProperty(value = "返佣金额")
	private java.lang.Double returnMoney;
	
	/** 应收利息金额 */
	@ApiModelProperty(value = "应收利息金额")
	private java.lang.Double receivableInterestMoney;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 费率凭证 */
	@ApiModelProperty(value = "费率凭证")
	private java.lang.String rateImg;
	
	/** 扣回后置费用 */
	@ApiModelProperty(value = "扣回后置费用")
	private java.lang.Double backExpensesMoney;
	
	/** 类型 1：收利息  2：扣回后置费用 3：核实收费 */
	@ApiModelProperty(value = "类型 1：收利息  2：扣回后置费用 3：核实收费")
	private java.lang.Integer type;
	
	/** 返佣uid */
	@ApiModelProperty(value = "返佣uid")
	private java.lang.String rebateUid;
	
	/** 下一处理人 */
	@ApiModelProperty(value = "下一处理人")
	private String uid;

	  public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setInterestTime(java.util.Date value) {
		this.interestTime = value;
	}	
	public java.util.Date getInterestTime() {
		return this.interestTime;
	}
	public void setInterestImg(java.lang.String value) {
		this.interestImg = value;
	}	
	public java.lang.String getInterestImg() {
		return this.interestImg;
	}
	public void setCollectInterestMoney(java.lang.Double value) {
		this.collectInterestMoney = value;
	}	
	public java.lang.Double getCollectInterestMoney() {
		return this.collectInterestMoney;
	}
	public void setPayInterestMoney(java.lang.Double value) {
		this.payInterestMoney = value;
	}	
	public java.lang.Double getPayInterestMoney() {
		return this.payInterestMoney;
	}
	public void setReturnMoney(java.lang.Double value) {
		this.returnMoney = value;
	}	
	public java.lang.Double getReturnMoney() {
		return this.returnMoney;
	}
	public void setReceivableInterestMoney(java.lang.Double value) {
		this.receivableInterestMoney = value;
	}	
	public java.lang.Double getReceivableInterestMoney() {
		return this.receivableInterestMoney;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setRateImg(java.lang.String value) {
		this.rateImg = value;
	}	
	public java.lang.String getRateImg() {
		return this.rateImg;
	}
	public void setBackExpensesMoney(java.lang.Double value) {
		this.backExpensesMoney = value;
	}	
	public java.lang.Double getBackExpensesMoney() {
		return this.backExpensesMoney;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setRebateUid(java.lang.String value) {
		this.rebateUid = value;
	}	
	public java.lang.String getRebateUid() {
		return this.rebateUid;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("lastUpdateTime",getLastUpdateTime())
			.append("orderNo",getOrderNo())
			.append("interestTime",getInterestTime())
			.append("interestImg",getInterestImg())
			.append("collectInterestMoney",getCollectInterestMoney())
			.append("payInterestMoney",getPayInterestMoney())
			.append("returnMoney",getReturnMoney())
			.append("receivableInterestMoney",getReceivableInterestMoney())
			.append("remark",getRemark())
			.append("rateImg",getRateImg())
			.append("backExpensesMoney",getBackExpensesMoney())
			.append("type",getType())
			.append("rebateUid",getRebateUid())
			.toString();
	}
}

