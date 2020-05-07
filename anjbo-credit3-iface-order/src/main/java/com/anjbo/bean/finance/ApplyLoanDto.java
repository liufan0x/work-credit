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
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@ApiModel(value = "待申请放款的订单")
public class ApplyLoanDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 待申请放款的订单 */
	@ApiModelProperty(value = "待申请放款的订单")
	private java.lang.Integer id;
	
	/** 最后更新时间 */
	@ApiModelProperty(value = "最后更新时间")
	private java.util.Date lastUpdateTime;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 客户姓名 */
	@ApiModelProperty(value = "客户姓名")
	private java.lang.String borrowerName;
	
	/** 借款天数 */
	@ApiModelProperty(value = "借款天数")
	private java.lang.Integer borrowingDays;
	
	/** 借款金额 */
	@ApiModelProperty(value = "借款金额")
	private Double loanAmount;
	
	/** 放款银行 */
	@ApiModelProperty(value = "放款银行")
	private java.lang.String lendingBankId;
	
	/** 放款银行 */
	@ApiModelProperty(value = "放款银行")
	private String lendingBank;
	
	/** 开户银行 */
	@ApiModelProperty(value = "开户银行")
	private java.lang.String lendingBankSubId;

	/** 开户银行 */
	@ApiModelProperty(value = "开户银行")
	private java.lang.String lendingBankSub;
	
	/** 银行卡户名 */
	@ApiModelProperty(value = "银行卡户名")
	private java.lang.String bankName;
	
	/** 银行卡账号 */
	@ApiModelProperty(value = "银行卡账号")
	private java.lang.String bankAccount;
	
	/** 已收取费用图 */
	@ApiModelProperty(value = "已收取费用图")
	private java.lang.String chargesReceivedImg;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 出款账户图片 */
	@ApiModelProperty(value = "出款账户图片")
	private java.lang.String payAccountImg;
	
	/** 抵押回执照片/抵押状态截图 */
	@ApiModelProperty(value = "抵押回执照片/抵押状态截图")
	private java.lang.String mortgageImg;
	
	/** 1:抵押回执照片 2:抵押状态截图 */
	@ApiModelProperty(value = "1:抵押回执照片 2:抵押状态截图")
	private java.lang.Integer mortgageImgType;
	

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
	public void setBorrowerName(java.lang.String value) {
		this.borrowerName = value;
	}	
	public java.lang.String getBorrowerName() {
		return this.borrowerName;
	}
	public void setBorrowingDays(java.lang.Integer value) {
		this.borrowingDays = value;
	}	
	public java.lang.Integer getBorrowingDays() {
		return this.borrowingDays;
	}
	public void setLoanAmount(Double value) {
		this.loanAmount = value;
	}	
	public Double getLoanAmount() {
		return this.loanAmount;
	}
	public void setLendingBankId(java.lang.String value) {
		this.lendingBankId = value;
	}	
	public java.lang.String getLendingBankId() {
		return this.lendingBankId;
	}
	public void setLendingBankSubId(java.lang.String value) {
		this.lendingBankSubId = value;
	}	
	public java.lang.String getLendingBankSubId() {
		return this.lendingBankSubId;
	}
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setBankAccount(java.lang.String value) {
		this.bankAccount = value;
	}	
	public java.lang.String getBankAccount() {
		return this.bankAccount;
	}
	public void setChargesReceivedImg(java.lang.String value) {
		this.chargesReceivedImg = value;
	}	
	public java.lang.String getChargesReceivedImg() {
		return this.chargesReceivedImg;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setPayAccountImg(java.lang.String value) {
		this.payAccountImg = value;
	}	
	public java.lang.String getPayAccountImg() {
		return this.payAccountImg;
	}
	public void setMortgageImg(java.lang.String value) {
		this.mortgageImg = value;
	}	
	public java.lang.String getMortgageImg() {
		return this.mortgageImg;
	}
	public void setMortgageImgType(java.lang.Integer value) {
		this.mortgageImgType = value;
	}	
	public java.lang.Integer getMortgageImgType() {
		return this.mortgageImgType;
	}
	public String getLendingBank() {
		return lendingBank;
	}
	public void setLendingBank(String lendingBank) {
		this.lendingBank = lendingBank;
	}
	public java.lang.String getLendingBankSub() {
		return lendingBankSub;
	}
	public void setLendingBankSub(java.lang.String lendingBankSub) {
		this.lendingBankSub = lendingBankSub;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("lastUpdateTime",getLastUpdateTime())
			.append("orderNo",getOrderNo())
			.append("borrowerName",getBorrowerName())
			.append("borrowingDays",getBorrowingDays())
			.append("loanAmount",getLoanAmount())
			.append("lendingBankId",getLendingBankId())
			.append("lendingBankSubId",getLendingBankSubId())
			.append("bankName",getBankName())
			.append("bankAccount",getBankAccount())
			.append("chargesReceivedImg",getChargesReceivedImg())
			.append("remark",getRemark())
			.append("payAccountImg",getPayAccountImg())
			.append("mortgageImg",getMortgageImg())
			.append("mortgageImgType",getMortgageImgType())
			.toString();
	}
}

