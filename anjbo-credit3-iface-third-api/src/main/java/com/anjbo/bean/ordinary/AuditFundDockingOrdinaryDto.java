/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.ordinary;

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
@ApiModel(value = "订单审批-资料推送-资方借款信息")
public class AuditFundDockingOrdinaryDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 订单审批-资料推送-资方借款信息 */
	@ApiModelProperty(value = "订单审批-资料推送-资方借款信息")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 资金方外键id */
	@ApiModelProperty(value = "资金方外键id")
	private java.lang.Integer fundId;
	
	/** 客户姓名 */
	@ApiModelProperty(value = "客户姓名")
	private java.lang.String borrowerName;
	
	/** 客户手机号码 */
	@ApiModelProperty(value = "客户手机号码")
	private java.lang.String phoneNumber;
	
	/** 原贷款是否银行(1:是,2:否) */
	@ApiModelProperty(value = "原贷款是否银行(1:是,2:否)")
	private java.lang.Integer isOldLoanBank;
	
	/** 原贷款银行名称/原贷款地点 */
	@ApiModelProperty(value = "原贷款银行名称/原贷款地点")
	private java.lang.String oldLoanBankName;
	
	/** 新贷款支行 */
	@ApiModelProperty(value = "新贷款支行")
	private java.lang.String oldLoanBankSubName;
	
	/** 原贷款银行ID */
	@ApiModelProperty(value = "原贷款银行ID")
	private java.lang.Integer oldLoanBankNameId;
	
	/** 原贷款支行ID */
	@ApiModelProperty(value = "原贷款支行ID")
	private java.lang.Integer oldLoanBankSubNameId;
	
	/** 新贷款是否银行(1:是,2:否) */
	@ApiModelProperty(value = "新贷款是否银行(1:是,2:否)")
	private java.lang.Integer isLoanBank;
	
	/** 新贷款银行/新贷款地点 */
	@ApiModelProperty(value = "新贷款银行/新贷款地点")
	private java.lang.String loanBankName;
	
	/** 新贷款支行 */
	@ApiModelProperty(value = "新贷款支行")
	private java.lang.String loanSubBankName;
	
	/** 新贷款银行ID */
	@ApiModelProperty(value = "新贷款银行ID")
	private java.lang.Integer loanBankNameId;
	
	/** 新贷款支行ID */
	@ApiModelProperty(value = "新贷款支行ID")
	private java.lang.Integer loanSubBankNameId;
	
	/** 借款天数 */
	@ApiModelProperty(value = "借款天数")
	private java.lang.Integer borrowingDays;
	
	/** 借款金额 */
	@ApiModelProperty(value = "借款金额")
	private Long loanAmount;
	
	/** 是否一次性回款:0:初始化,1:是,2:否(非交易类没有是否一次性回款) */
	@ApiModelProperty(value = "是否一次性回款:0:初始化,1:是,2:否(非交易类没有是否一次性回款)")
	private java.lang.Integer isOnePay;
	
	/** 回款时间 */
	@ApiModelProperty(value = "回款时间")
	private java.lang.String receivableForTime;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
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
	public void setFundId(java.lang.Integer value) {
		this.fundId = value;
	}	
	public java.lang.Integer getFundId() {
		return this.fundId;
	}
	public void setBorrowerName(java.lang.String value) {
		this.borrowerName = value;
	}	
	public java.lang.String getBorrowerName() {
		return this.borrowerName;
	}
	public void setPhoneNumber(java.lang.String value) {
		this.phoneNumber = value;
	}	
	public java.lang.String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setIsOldLoanBank(java.lang.Integer value) {
		this.isOldLoanBank = value;
	}	
	public java.lang.Integer getIsOldLoanBank() {
		return this.isOldLoanBank;
	}
	public void setOldLoanBankName(java.lang.String value) {
		this.oldLoanBankName = value;
	}	
	public java.lang.String getOldLoanBankName() {
		return this.oldLoanBankName;
	}
	public void setOldLoanBankSubName(java.lang.String value) {
		this.oldLoanBankSubName = value;
	}	
	public java.lang.String getOldLoanBankSubName() {
		return this.oldLoanBankSubName;
	}
	public void setOldLoanBankNameId(java.lang.Integer value) {
		this.oldLoanBankNameId = value;
	}	
	public java.lang.Integer getOldLoanBankNameId() {
		return this.oldLoanBankNameId;
	}
	public void setOldLoanBankSubNameId(java.lang.Integer value) {
		this.oldLoanBankSubNameId = value;
	}	
	public java.lang.Integer getOldLoanBankSubNameId() {
		return this.oldLoanBankSubNameId;
	}
	public void setIsLoanBank(java.lang.Integer value) {
		this.isLoanBank = value;
	}	
	public java.lang.Integer getIsLoanBank() {
		return this.isLoanBank;
	}
	public void setLoanBankName(java.lang.String value) {
		this.loanBankName = value;
	}	
	public java.lang.String getLoanBankName() {
		return this.loanBankName;
	}
	public void setLoanSubBankName(java.lang.String value) {
		this.loanSubBankName = value;
	}	
	public java.lang.String getLoanSubBankName() {
		return this.loanSubBankName;
	}
	public void setLoanBankNameId(java.lang.Integer value) {
		this.loanBankNameId = value;
	}	
	public java.lang.Integer getLoanBankNameId() {
		return this.loanBankNameId;
	}
	public void setLoanSubBankNameId(java.lang.Integer value) {
		this.loanSubBankNameId = value;
	}	
	public java.lang.Integer getLoanSubBankNameId() {
		return this.loanSubBankNameId;
	}
	public void setBorrowingDays(java.lang.Integer value) {
		this.borrowingDays = value;
	}	
	public java.lang.Integer getBorrowingDays() {
		return this.borrowingDays;
	}
	public void setLoanAmount(Long value) {
		this.loanAmount = value;
	}	
	public Long getLoanAmount() {
		return this.loanAmount;
	}
	public void setIsOnePay(java.lang.Integer value) {
		this.isOnePay = value;
	}	
	public java.lang.Integer getIsOnePay() {
		return this.isOnePay;
	}
	public void setReceivableForTime(java.lang.String value) {
		this.receivableForTime = value;
	}	
	public java.lang.String getReceivableForTime() {
		return this.receivableForTime;
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
			.append("fundId",getFundId())
			.append("borrowerName",getBorrowerName())
			.append("phoneNumber",getPhoneNumber())
			.append("isOldLoanBank",getIsOldLoanBank())
			.append("oldLoanBankName",getOldLoanBankName())
			.append("oldLoanBankSubName",getOldLoanBankSubName())
			.append("oldLoanBankNameId",getOldLoanBankNameId())
			.append("oldLoanBankSubNameId",getOldLoanBankSubNameId())
			.append("isLoanBank",getIsLoanBank())
			.append("loanBankName",getLoanBankName())
			.append("loanSubBankName",getLoanSubBankName())
			.append("loanBankNameId",getLoanBankNameId())
			.append("loanSubBankNameId",getLoanSubBankNameId())
			.append("borrowingDays",getBorrowingDays())
			.append("loanAmount",getLoanAmount())
			.append("isOnePay",getIsOnePay())
			.append("receivableForTime",getReceivableForTime())
			.append("remark",getRemark())
			.toString();
	}
}

