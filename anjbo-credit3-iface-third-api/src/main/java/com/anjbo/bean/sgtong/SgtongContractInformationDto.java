/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
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
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@ApiModel(value = "合同信息表")
public class SgtongContractInformationDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
	private java.lang.Integer count;
	public java.lang.Integer getCount() {
		return count;
	}
	public void setCount(java.lang.Integer count) {
		this.count = count;
	}

	/** 合同信息表 */
	@ApiModelProperty(value = "合同信息表")
	private java.lang.Integer id;
	
	/** orderNo */
	@ApiModelProperty(value = "orderNo")
	private java.lang.String orderNo;
	
	/** 合同金额 */
	@ApiModelProperty(value = "合同金额")
	private java.lang.String pactAmt;
	
	/** 利率类型 */
	@ApiModelProperty(value = "利率类型")
	private java.lang.String rateType;
	
	/** 利率（月） */
	@ApiModelProperty(value = "利率（月）")
	private java.lang.String lnRate;
	
	/** 借款期限 */
	@ApiModelProperty(value = "借款期限")
	private java.lang.String termLoan;
	
	/** 申请地点 */
	@ApiModelProperty(value = "申请地点")
	private java.lang.String appArea;
	
	/** 申请用途 */
	@ApiModelProperty(value = "申请用途")
	private java.lang.String appUse;
	
	/** 扣款日类型01-相对扣款日 02-固定扣款日 */
	@ApiModelProperty(value = "扣款日类型01-相对扣款日 02-固定扣款日")
	private java.lang.String payType;
	
	/** 还款方式 */
	@ApiModelProperty(value = "还款方式")
	private java.lang.String repayment;
	
	/** 合同期限（月） */
	@ApiModelProperty(value = "合同期限（月）")
	private java.lang.String termMon;
	
	/** 合同期限（日） */
	@ApiModelProperty(value = "合同期限（日）")
	private java.lang.String termDay;
	
	/** 担保方式1-质押 2-抵押 4-信用 */
	@ApiModelProperty(value = "担保方式1-质押 2-抵押 4-信用")
	private java.lang.Integer vouType;
	

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
	public void setPactAmt(java.lang.String value) {
		this.pactAmt = value;
	}	
	public java.lang.String getPactAmt() {
		return this.pactAmt;
	}
	public void setRateType(java.lang.String value) {
		this.rateType = value;
	}	
	public java.lang.String getRateType() {
		return this.rateType;
	}
	public void setLnRate(java.lang.String value) {
		this.lnRate = value;
	}	
	public java.lang.String getLnRate() {
		return this.lnRate;
	}
	public void setTermLoan(java.lang.String value) {
		this.termLoan = value;
	}	
	public java.lang.String getTermLoan() {
		return this.termLoan;
	}
	public void setAppArea(java.lang.String value) {
		this.appArea = value;
	}	
	public java.lang.String getAppArea() {
		return this.appArea;
	}
	public void setAppUse(java.lang.String value) {
		this.appUse = value;
	}	
	public java.lang.String getAppUse() {
		return this.appUse;
	}
	public void setPayType(java.lang.String value) {
		this.payType = value;
	}	
	public java.lang.String getPayType() {
		return this.payType;
	}
	public void setRepayment(java.lang.String value) {
		this.repayment = value;
	}	
	public java.lang.String getRepayment() {
		return this.repayment;
	}
	public void setTermMon(java.lang.String value) {
		this.termMon = value;
	}	
	public java.lang.String getTermMon() {
		return this.termMon;
	}
	public void setTermDay(java.lang.String value) {
		this.termDay = value;
	}	
	public java.lang.String getTermDay() {
		return this.termDay;
	}
	public void setVouType(java.lang.Integer value) {
		this.vouType = value;
	}	
	public java.lang.Integer getVouType() {
		return this.vouType;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("pactAmt",getPactAmt())
			.append("rateType",getRateType())
			.append("lnRate",getLnRate())
			.append("termLoan",getTermLoan())
			.append("appArea",getAppArea())
			.append("appUse",getAppUse())
			.append("payType",getPayType())
			.append("repayment",getRepayment())
			.append("termMon",getTermMon())
			.append("termDay",getTermDay())
			.append("vouType",getVouType())
			.toString();
	}
}

