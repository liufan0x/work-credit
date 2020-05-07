/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.risk;

import java.util.List;

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
@ApiModel(value = "风控-初审")
public class AuditFirstDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 风控-初审 */
	@ApiModelProperty(value = "风控-初审")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 审批时间 */
	@ApiModelProperty(value = "审批时间")
	private java.util.Date auditTime;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 审批状态(1:通过,2不通过(退回),3上报终审) */
	@ApiModelProperty(value = "审批状态(1:通过,2不通过(退回),3上报终审)")
	private java.lang.Integer auditStatus;
	
	/** 0:展示1：不展示 */
	@ApiModelProperty(value = "0:展示1：不展示")
	private java.lang.Integer isShow;
	
	/** 审批人 */
	@ApiModelProperty(value = "审批人")
	private java.lang.String handleUid;
	/** 审批人名称 */
	@ApiModelProperty(value = "审批人名称")
	private java.lang.String handleName;
	
	/** 业务类型 */
	@ApiModelProperty(value = "业务类型")
	private java.lang.String productName;
	
	/** 借款金额 */
	@ApiModelProperty(value = "借款金额")
	private Double loanAmount;
	
	/** 负债比例 */
	@ApiModelProperty(value = "负债比例")
	private Double debtRatio;
	
	/** 借款成数 */
	@ApiModelProperty(value = "借款成数")
	private Double loanPoportion;
	
	/** 债务置换贷款成数 */
	@ApiModelProperty(value = "债务置换贷款成数")
	private Double productLoanPoportion;
	
	/** 房产产权人 */
	@ApiModelProperty(value = "房产产权人")
	private java.lang.String propertyOwner;
	
	/** 物业名称 */
	@ApiModelProperty(value = "物业名称")
	private java.lang.String propertyName;
	
	/**  建筑面积 */
	@ApiModelProperty(value = " 建筑面积")
	private java.lang.String area;
	
	/** assessment */
	@ApiModelProperty(value = "assessment")
	private Double assessment;
	
	/** 抵押情况 */
	@ApiModelProperty(value = "抵押情况")
	private java.lang.String mortgageSituation;
	
	/** 原贷款地点 */
	@ApiModelProperty(value = "原贷款地点")
	private java.lang.String addressOld;
	
	/** 原贷款金额 */
	@ApiModelProperty(value = "原贷款金额")
	private Double amountOld;
	
	/** 新贷款地点 */
	@ApiModelProperty(value = "新贷款地点")
	private java.lang.String addressNew;
	
	/** 新贷款金额 */
	@ApiModelProperty(value = "新贷款金额")
	private Double amountNew;
	
	/** 近6个月查询次数 */
	@ApiModelProperty(value = "近6个月查询次数")
	private java.lang.Integer monthNum;
	
	/** 近两年逾期次数 */
	@ApiModelProperty(value = "近两年逾期次数")
	private java.lang.Integer yearNum;
	
	/** 业务标准 */
	@ApiModelProperty(value = "业务标准")
	private java.lang.Integer business;
	
	/** 出款金额 */
	@ApiModelProperty(value = "出款金额")
	private Double sunMoney;
	
	/** 出款开户名 */
	@ApiModelProperty(value = "出款开户名")
	private java.lang.String loanName;
	
	/** 出款银行账户 */
	@ApiModelProperty(value = "出款银行账户")
	private java.lang.String loanAccount;
	
	/** 出款银行id */
	@ApiModelProperty(value = "出款银行id")
	private java.lang.Integer loanBankId;
	
	/** loanBankName */
	@ApiModelProperty(value = "loanBankName")
	private java.lang.String loanBankName;
	
	/** 出款支行id */
	@ApiModelProperty(value = "出款支行id")
	private java.lang.Integer loanBankSubId;
	
	/** loanBankSubName */
	@ApiModelProperty(value = "loanBankSubName")
	private java.lang.String loanBankSubName;
	
	/** 回款银行开户名 */
	@ApiModelProperty(value = "回款银行开户名")
	private java.lang.String paymentName;
	
	/** paymentAccount */
	@ApiModelProperty(value = "paymentAccount")
	private java.lang.String paymentAccount;
	
	/** paymentBankId */
	@ApiModelProperty(value = "paymentBankId")
	private java.lang.Integer paymentBankId;
	
	/** paymentBankName */
	@ApiModelProperty(value = "paymentBankName")
	private java.lang.String paymentBankName;
	
	/** paymentBankSubId */
	@ApiModelProperty(value = "paymentBankSubId")
	private java.lang.Integer paymentBankSubId;
	
	/** paymentBankSubName */
	@ApiModelProperty(value = "paymentBankSubName")
	private java.lang.String paymentBankSubName;
	
	/** 费率 */
	@ApiModelProperty(value = "费率")
	private Double rate;
	
	/** 逾期费率 */
	@ApiModelProperty(value = "逾期费率")
	private Double overdueRate;
	
	/** 其他 */
	@ApiModelProperty(value = "其他")
	private java.lang.String other;
	
	 private List<AuditFirstForeclosureDto> foreclosureAuditList;     //两个集合是新增的
	 private List<AuditFirstPaymentDto> firstPaymentAuditList;
	

	public java.lang.String getHandleName() {
		return handleName;
	}
	public void setHandleName(java.lang.String handleName) {
		this.handleName = handleName;
	}
	public List<AuditFirstForeclosureDto> getForeclosureAuditList() {
		return foreclosureAuditList;
	}
	public void setForeclosureAuditList(List<AuditFirstForeclosureDto> foreclosureAuditList) {
		this.foreclosureAuditList = foreclosureAuditList;
	}
	public List<AuditFirstPaymentDto> getFirstPaymentAuditList() {
		return firstPaymentAuditList;
	}
	public void setFirstPaymentAuditList(List<AuditFirstPaymentDto> firstPaymentAuditList) {
		this.firstPaymentAuditList = firstPaymentAuditList;
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
	public void setAuditStatus(java.lang.Integer value) {
		this.auditStatus = value;
	}	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setIsShow(java.lang.Integer value) {
		this.isShow = value;
	}	
	public java.lang.Integer getIsShow() {
		return this.isShow;
	}
	public void setHandleUid(java.lang.String value) {
		this.handleUid = value;
	}	
	public java.lang.String getHandleUid() {
		return this.handleUid;
	}
	public void setProductName(java.lang.String value) {
		this.productName = value;
	}	
	public java.lang.String getProductName() {
		return this.productName;
	}
	public void setLoanAmount(Double value) {
		this.loanAmount = value;
	}	
	public Double getLoanAmount() {
		return this.loanAmount;
	}
	public void setDebtRatio(Double value) {
		this.debtRatio = value;
	}	
	public Double getDebtRatio() {
		return this.debtRatio;
	}
	public void setLoanPoportion(Double value) {
		this.loanPoportion = value;
	}	
	public Double getLoanPoportion() {
		return this.loanPoportion;
	}
	public void setProductLoanPoportion(Double value) {
		this.productLoanPoportion = value;
	}	
	public Double getProductLoanPoportion() {
		return this.productLoanPoportion;
	}
	public void setPropertyOwner(java.lang.String value) {
		this.propertyOwner = value;
	}	
	public java.lang.String getPropertyOwner() {
		return this.propertyOwner;
	}
	public void setPropertyName(java.lang.String value) {
		this.propertyName = value;
	}	
	public java.lang.String getPropertyName() {
		return this.propertyName;
	}
	public void setArea(java.lang.String value) {
		this.area = value;
	}	
	public java.lang.String getArea() {
		return this.area;
	}
	public void setAssessment(Double value) {
		this.assessment = value;
	}	
	public Double getAssessment() {
		return this.assessment;
	}
	public void setMortgageSituation(java.lang.String value) {
		this.mortgageSituation = value;
	}	
	public java.lang.String getMortgageSituation() {
		return this.mortgageSituation;
	}
	public void setAddressOld(java.lang.String value) {
		this.addressOld = value;
	}	
	public java.lang.String getAddressOld() {
		return this.addressOld;
	}
	public void setAmountOld(Double value) {
		this.amountOld = value;
	}	
	public Double getAmountOld() {
		return this.amountOld;
	}
	public void setAddressNew(java.lang.String value) {
		this.addressNew = value;
	}	
	public java.lang.String getAddressNew() {
		return this.addressNew;
	}
	public void setAmountNew(Double value) {
		this.amountNew = value;
	}	
	public Double getAmountNew() {
		return this.amountNew;
	}
	public void setMonthNum(java.lang.Integer value) {
		this.monthNum = value;
	}	
	public java.lang.Integer getMonthNum() {
		return this.monthNum;
	}
	public void setYearNum(java.lang.Integer value) {
		this.yearNum = value;
	}	
	public java.lang.Integer getYearNum() {
		return this.yearNum;
	}
	public void setBusiness(java.lang.Integer value) {
		this.business = value;
	}	
	public java.lang.Integer getBusiness() {
		return this.business;
	}
	public void setSunMoney(Double value) {
		this.sunMoney = value;
	}	
	public Double getSunMoney() {
		return this.sunMoney;
	}
	public void setLoanName(java.lang.String value) {
		this.loanName = value;
	}	
	public java.lang.String getLoanName() {
		return this.loanName;
	}
	public void setLoanAccount(java.lang.String value) {
		this.loanAccount = value;
	}	
	public java.lang.String getLoanAccount() {
		return this.loanAccount;
	}
	public void setLoanBankId(java.lang.Integer value) {
		this.loanBankId = value;
	}	
	public java.lang.Integer getLoanBankId() {
		return this.loanBankId;
	}
	public void setLoanBankName(java.lang.String value) {
		this.loanBankName = value;
	}	
	public java.lang.String getLoanBankName() {
		return this.loanBankName;
	}
	public void setLoanBankSubId(java.lang.Integer value) {
		this.loanBankSubId = value;
	}	
	public java.lang.Integer getLoanBankSubId() {
		return this.loanBankSubId;
	}
	public void setLoanBankSubName(java.lang.String value) {
		this.loanBankSubName = value;
	}	
	public java.lang.String getLoanBankSubName() {
		return this.loanBankSubName;
	}
	public void setPaymentName(java.lang.String value) {
		this.paymentName = value;
	}	
	public java.lang.String getPaymentName() {
		return this.paymentName;
	}
	public void setPaymentAccount(java.lang.String value) {
		this.paymentAccount = value;
	}	
	public java.lang.String getPaymentAccount() {
		return this.paymentAccount;
	}
	public void setPaymentBankId(java.lang.Integer value) {
		this.paymentBankId = value;
	}	
	public java.lang.Integer getPaymentBankId() {
		return this.paymentBankId;
	}
	public void setPaymentBankName(java.lang.String value) {
		this.paymentBankName = value;
	}	
	public java.lang.String getPaymentBankName() {
		return this.paymentBankName;
	}
	public void setPaymentBankSubId(java.lang.Integer value) {
		this.paymentBankSubId = value;
	}	
	public java.lang.Integer getPaymentBankSubId() {
		return this.paymentBankSubId;
	}
	public void setPaymentBankSubName(java.lang.String value) {
		this.paymentBankSubName = value;
	}	
	public java.lang.String getPaymentBankSubName() {
		return this.paymentBankSubName;
	}
	public void setRate(Double value) {
		this.rate = value;
	}	
	public Double getRate() {
		return this.rate;
	}
	public void setOverdueRate(Double value) {
		this.overdueRate = value;
	}	
	public Double getOverdueRate() {
		return this.overdueRate;
	}
	public void setOther(java.lang.String value) {
		this.other = value;
	}	
	public java.lang.String getOther() {
		return this.other;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("auditTime",getAuditTime())
			.append("remark",getRemark())
			.append("auditStatus",getAuditStatus())
			.append("isShow",getIsShow())
			.append("handleUid",getHandleUid())
			.append("productName",getProductName())
			.append("loanAmount",getLoanAmount())
			.append("debtRatio",getDebtRatio())
			.append("loanPoportion",getLoanPoportion())
			.append("productLoanPoportion",getProductLoanPoportion())
			.append("propertyOwner",getPropertyOwner())
			.append("propertyName",getPropertyName())
			.append("area",getArea())
			.append("assessment",getAssessment())
			.append("mortgageSituation",getMortgageSituation())
			.append("addressOld",getAddressOld())
			.append("amountOld",getAmountOld())
			.append("addressNew",getAddressNew())
			.append("amountNew",getAmountNew())
			.append("monthNum",getMonthNum())
			.append("yearNum",getYearNum())
			.append("business",getBusiness())
			.append("sunMoney",getSunMoney())
			.append("loanName",getLoanName())
			.append("loanAccount",getLoanAccount())
			.append("loanBankId",getLoanBankId())
			.append("loanBankName",getLoanBankName())
			.append("loanBankSubId",getLoanBankSubId())
			.append("loanBankSubName",getLoanBankSubName())
			.append("paymentName",getPaymentName())
			.append("paymentAccount",getPaymentAccount())
			.append("paymentBankId",getPaymentBankId())
			.append("paymentBankName",getPaymentBankName())
			.append("paymentBankSubId",getPaymentBankSubId())
			.append("paymentBankSubName",getPaymentBankSubName())
			.append("rate",getRate())
			.append("overdueRate",getOverdueRate())
			.append("other",getOther())
			.toString();
	}
}

