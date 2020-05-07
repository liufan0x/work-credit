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
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@ApiModel(value = "订单征信信息")
public class CreditDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 订单征信信息 */
	@ApiModelProperty(value = "订单征信信息")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 信用卡年限 */
	@ApiModelProperty(value = "信用卡年限")
	private java.lang.String creditCardYears;
	
	/** 个人贷款记录年限 */
	@ApiModelProperty(value = "个人贷款记录年限")
	private java.lang.String loanRecordYears;
	
	/** 个人贷款,信用卡累计有效违约率 */
	@ApiModelProperty(value = "个人贷款,信用卡累计有效违约率")
	private java.lang.String violationProportion;
	
	/** 所有房产评估总值(万元) */
	@ApiModelProperty(value = "所有房产评估总值(万元)")
	private Double allHouseWorth;
	
	/** 房产套数 */
	@ApiModelProperty(value = "房产套数")
	private java.lang.Integer allHouseNumber;
	
	/** 信用卡半年月均透支额(万元) */
	@ApiModelProperty(value = "信用卡半年月均透支额(万元)")
	private Double creditCardOverdraft;
	
	/** 授信总额（万元） */
	@ApiModelProperty(value = "授信总额（万元）")
	private Double creditQuota;
	
	/** 已用额度(万元) */
	@ApiModelProperty(value = "已用额度(万元)")
	private Double useQuota;
	
	/** 征信总负债(万元) */
	@ApiModelProperty(value = "征信总负债(万元)")
	private Double creditLiabilities;
	
	/** 负债比例 */
	@ApiModelProperty(value = "负债比例")
	private Double liabilitiesProportion;
	
	/** 借款成数 */
	@ApiModelProperty(value = "借款成数")
	private Double loanPercentage;
	
	/** 赎楼成数 */
	@ApiModelProperty(value = "赎楼成数")
	private Double foreclosurePercentage;
	
	/** 征信报告逾期数 */
	@ApiModelProperty(value = "征信报告逾期数")
	private java.lang.Integer creditOverdueNumber;
	
	/** 近半年征信查询次数 */
	@ApiModelProperty(value = "近半年征信查询次数")
	private java.lang.Integer latelyHalfYearSelectNumber;
	
	/** 2年内金额2000以上有逾期次数 */
	@ApiModelProperty(value = "2年内金额2000以上有逾期次数")
	private java.lang.Integer latelyTwoYearMoneyOverdueNumber;
	
	/** 是否公司产权(是,否) */
	@ApiModelProperty(value = "是否公司产权(是,否)")
	private java.lang.String isCompanyProperty;
	
	/** 产权抵押情况 */
	@ApiModelProperty(value = "产权抵押情况")
	private java.lang.String propertyMortgage;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 是否完成1:是,2:否 */
	@ApiModelProperty(value = "是否完成1:是,2:否")
	private java.lang.Integer isFinish;
	

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
	public void setCreditCardYears(java.lang.String value) {
		this.creditCardYears = value;
	}	
	public java.lang.String getCreditCardYears() {
		return this.creditCardYears;
	}
	public void setLoanRecordYears(java.lang.String value) {
		this.loanRecordYears = value;
	}	
	public java.lang.String getLoanRecordYears() {
		return this.loanRecordYears;
	}
	public void setViolationProportion(java.lang.String value) {
		this.violationProportion = value;
	}	
	public java.lang.String getViolationProportion() {
		return this.violationProportion;
	}
	public void setAllHouseWorth(Double value) {
		this.allHouseWorth = value;
	}	
	public Double getAllHouseWorth() {
		return this.allHouseWorth;
	}
	public void setAllHouseNumber(java.lang.Integer value) {
		this.allHouseNumber = value;
	}	
	public java.lang.Integer getAllHouseNumber() {
		return this.allHouseNumber;
	}
	public void setCreditCardOverdraft(Double value) {
		this.creditCardOverdraft = value;
	}	
	public Double getCreditCardOverdraft() {
		return this.creditCardOverdraft;
	}
	public void setCreditQuota(Double value) {
		this.creditQuota = value;
	}	
	public Double getCreditQuota() {
		return this.creditQuota;
	}
	public void setUseQuota(Double value) {
		this.useQuota = value;
	}	
	public Double getUseQuota() {
		return this.useQuota;
	}
	public void setCreditLiabilities(Double value) {
		this.creditLiabilities = value;
	}	
	public Double getCreditLiabilities() {
		return this.creditLiabilities;
	}
	public void setLiabilitiesProportion(Double value) {
		this.liabilitiesProportion = value;
	}	
	public Double getLiabilitiesProportion() {
		return this.liabilitiesProportion;
	}
	public void setLoanPercentage(Double value) {
		this.loanPercentage = value;
	}	
	public Double getLoanPercentage() {
		return this.loanPercentage;
	}
	public void setForeclosurePercentage(Double value) {
		this.foreclosurePercentage = value;
	}	
	public Double getForeclosurePercentage() {
		return this.foreclosurePercentage;
	}
	public void setCreditOverdueNumber(java.lang.Integer value) {
		this.creditOverdueNumber = value;
	}	
	public java.lang.Integer getCreditOverdueNumber() {
		return this.creditOverdueNumber;
	}
	public void setLatelyHalfYearSelectNumber(java.lang.Integer value) {
		this.latelyHalfYearSelectNumber = value;
	}	
	public java.lang.Integer getLatelyHalfYearSelectNumber() {
		return this.latelyHalfYearSelectNumber;
	}
	public void setLatelyTwoYearMoneyOverdueNumber(java.lang.Integer value) {
		this.latelyTwoYearMoneyOverdueNumber = value;
	}	
	public java.lang.Integer getLatelyTwoYearMoneyOverdueNumber() {
		return this.latelyTwoYearMoneyOverdueNumber;
	}
	public void setIsCompanyProperty(java.lang.String value) {
		this.isCompanyProperty = value;
	}	
	public java.lang.String getIsCompanyProperty() {
		return this.isCompanyProperty;
	}
	public void setPropertyMortgage(java.lang.String value) {
		this.propertyMortgage = value;
	}	
	public java.lang.String getPropertyMortgage() {
		return this.propertyMortgage;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setIsFinish(java.lang.Integer value) {
		this.isFinish = value;
	}	
	public java.lang.Integer getIsFinish() {
		return this.isFinish;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("creditCardYears",getCreditCardYears())
			.append("loanRecordYears",getLoanRecordYears())
			.append("violationProportion",getViolationProportion())
			.append("allHouseWorth",getAllHouseWorth())
			.append("allHouseNumber",getAllHouseNumber())
			.append("creditCardOverdraft",getCreditCardOverdraft())
			.append("creditQuota",getCreditQuota())
			.append("useQuota",getUseQuota())
			.append("creditLiabilities",getCreditLiabilities())
			.append("liabilitiesProportion",getLiabilitiesProportion())
			.append("loanPercentage",getLoanPercentage())
			.append("foreclosurePercentage",getForeclosurePercentage())
			.append("creditOverdueNumber",getCreditOverdueNumber())
			.append("latelyHalfYearSelectNumber",getLatelyHalfYearSelectNumber())
			.append("latelyTwoYearMoneyOverdueNumber",getLatelyTwoYearMoneyOverdueNumber())
			.append("isCompanyProperty",getIsCompanyProperty())
			.append("propertyMortgage",getPropertyMortgage())
			.append("remark",getRemark())
			.append("isFinish",getIsFinish())
			.toString();
	}
}

