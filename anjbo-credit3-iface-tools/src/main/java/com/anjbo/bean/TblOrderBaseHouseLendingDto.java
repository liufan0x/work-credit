/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-12-03 10:54:30
 * @version 1.0
 */
@ApiModel(value = "id")
public class TblOrderBaseHouseLendingDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** id */
	@ApiModelProperty(value = "id")
	private java.lang.Long id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 放款银行 */
	@ApiModelProperty(value = "放款银行")
	private java.lang.Integer lendingBankId;
	
	/** 放款银支行 */
	@ApiModelProperty(value = "放款银支行")
	private java.lang.Integer lendingBankBranchId;
	
	/** 放款卡照片 */
	@ApiModelProperty(value = "放款卡照片")
	private java.lang.String lendingImgs;
	
	/** 银行卡账号 */
	@ApiModelProperty(value = "银行卡账号")
	private java.lang.String bankAccount;
	
	/** 银行卡户名 */
	@ApiModelProperty(value = "银行卡户名")
	private java.lang.String bankUserName;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 是否完成1:是,2:否 */
	@ApiModelProperty(value = "是否完成1:是,2:否")
	private java.lang.Integer isFinish;
	
	/** 放款银行名称 */
	@ApiModelProperty(value = "放款银行名称")
	private java.lang.String lendingBankName;
	
	/** 放款支行名称 */
	@ApiModelProperty(value = "放款支行名称")
	private java.lang.String lendingBankBranchName;
	
	/** 放款卡银行预留手机号 */
	@ApiModelProperty(value = "放款卡银行预留手机号")
	private java.lang.String lendingPhoneNumber;
	
	/** 回款银行 */
	@ApiModelProperty(value = "回款银行")
	private java.lang.Integer paymentBankId;
	
	/** 回款银行支行 */
	@ApiModelProperty(value = "回款银行支行")
	private java.lang.Integer paymentBankBranchId;
	
	/** 回款银行名称 */
	@ApiModelProperty(value = "回款银行名称")
	private java.lang.String paymentBankName;
	
	/** 回款支行名称 */
	@ApiModelProperty(value = "回款支行名称")
	private java.lang.String paymentBankBranchName;
	
	/** 回款卡照片 */
	@ApiModelProperty(value = "回款卡照片")
	private java.lang.String paymentImgs;
	
	/** 回款银行卡账号 */
	@ApiModelProperty(value = "回款银行卡账号")
	private java.lang.String paymentBankAccount;
	
	/** 持卡人姓名 */
	@ApiModelProperty(value = "持卡人姓名")
	private java.lang.String paymentBankUserName;
	
	/** 回款卡银行预留手机号 */
	@ApiModelProperty(value = "回款卡银行预留手机号")
	private java.lang.String paymentPhoneNumber;
	
	/** 回款卡备注 */
	@ApiModelProperty(value = "回款卡备注")
	private java.lang.String paymentRemark;
	private int repaymentType;
	private Long receivedPrincipalTotal;
	private Long receivedInterestTotal;
	private Long receivedDefaultInterestTotal;

	public void setId(java.lang.Long value) {
		this.id = value;
	}	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setLendingBankId(java.lang.Integer value) {
		this.lendingBankId = value;
	}	
	public java.lang.Integer getLendingBankId() {
		return this.lendingBankId;
	}
	public void setLendingBankBranchId(java.lang.Integer value) {
		this.lendingBankBranchId = value;
	}	
	public java.lang.Integer getLendingBankBranchId() {
		return this.lendingBankBranchId;
	}
	public void setLendingImgs(java.lang.String value) {
		this.lendingImgs = value;
	}	
	public java.lang.String getLendingImgs() {
		return this.lendingImgs;
	}
	public void setBankAccount(java.lang.String value) {
		this.bankAccount = value;
	}	
	public java.lang.String getBankAccount() {
		return this.bankAccount;
	}
	public void setBankUserName(java.lang.String value) {
		this.bankUserName = value;
	}	
	public java.lang.String getBankUserName() {
		return this.bankUserName;
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
	public void setLendingBankName(java.lang.String value) {
		this.lendingBankName = value;
	}	
	public java.lang.String getLendingBankName() {
		return this.lendingBankName;
	}
	public void setLendingBankBranchName(java.lang.String value) {
		this.lendingBankBranchName = value;
	}	
	public java.lang.String getLendingBankBranchName() {
		return this.lendingBankBranchName;
	}
	public void setLendingPhoneNumber(java.lang.String value) {
		this.lendingPhoneNumber = value;
	}	
	public java.lang.String getLendingPhoneNumber() {
		return this.lendingPhoneNumber;
	}
	public void setPaymentBankId(java.lang.Integer value) {
		this.paymentBankId = value;
	}	
	public java.lang.Integer getPaymentBankId() {
		return this.paymentBankId;
	}
	public void setPaymentBankBranchId(java.lang.Integer value) {
		this.paymentBankBranchId = value;
	}	
	public java.lang.Integer getPaymentBankBranchId() {
		return this.paymentBankBranchId;
	}
	public void setPaymentBankName(java.lang.String value) {
		this.paymentBankName = value;
	}	
	public java.lang.String getPaymentBankName() {
		return this.paymentBankName;
	}
	public void setPaymentBankBranchName(java.lang.String value) {
		this.paymentBankBranchName = value;
	}	
	public java.lang.String getPaymentBankBranchName() {
		return this.paymentBankBranchName;
	}
	public void setPaymentImgs(java.lang.String value) {
		this.paymentImgs = value;
	}	
	public java.lang.String getPaymentImgs() {
		return this.paymentImgs;
	}
	public void setPaymentBankAccount(java.lang.String value) {
		this.paymentBankAccount = value;
	}	
	public java.lang.String getPaymentBankAccount() {
		return this.paymentBankAccount;
	}
	public void setPaymentBankUserName(java.lang.String value) {
		this.paymentBankUserName = value;
	}	
	public java.lang.String getPaymentBankUserName() {
		return this.paymentBankUserName;
	}
	public void setPaymentPhoneNumber(java.lang.String value) {
		this.paymentPhoneNumber = value;
	}	
	public java.lang.String getPaymentPhoneNumber() {
		return this.paymentPhoneNumber;
	}
	public void setPaymentRemark(java.lang.String value) {
		this.paymentRemark = value;
	}	
	public java.lang.String getPaymentRemark() {
		return this.paymentRemark;
	}

	public int getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(int repaymentType) {
		this.repaymentType = repaymentType;
	}
	public Long getReceivedPrincipalTotal() {
		return receivedPrincipalTotal;
	}
	public void setReceivedPrincipalTotal(Long receivedPrincipalTotal) {
		this.receivedPrincipalTotal = receivedPrincipalTotal;
	}
	public Long getReceivedInterestTotal() {
		return receivedInterestTotal;
	}
	public void setReceivedInterestTotal(Long receivedInterestTotal) {
		this.receivedInterestTotal = receivedInterestTotal;
	}
	public Long getReceivedDefaultInterestTotal() {
		return receivedDefaultInterestTotal;
	}
	public void setReceivedDefaultInterestTotal(Long receivedDefaultInterestTotal) {
		this.receivedDefaultInterestTotal = receivedDefaultInterestTotal;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("lendingBankId",getLendingBankId())
			.append("lendingBankBranchId",getLendingBankBranchId())
			.append("lendingImgs",getLendingImgs())
			.append("bankAccount",getBankAccount())
			.append("bankUserName",getBankUserName())
			.append("remark",getRemark())
			.append("isFinish",getIsFinish())
			.append("lendingBankName",getLendingBankName())
			.append("lendingBankBranchName",getLendingBankBranchName())
			.append("lendingPhoneNumber",getLendingPhoneNumber())
			.append("paymentBankId",getPaymentBankId())
			.append("paymentBankBranchId",getPaymentBankBranchId())
			.append("paymentBankName",getPaymentBankName())
			.append("paymentBankBranchName",getPaymentBankBranchName())
			.append("paymentImgs",getPaymentImgs())
			.append("paymentBankAccount",getPaymentBankAccount())
			.append("paymentBankUserName",getPaymentBankUserName())
			.append("paymentPhoneNumber",getPaymentPhoneNumber())
			.append("paymentRemark",getPaymentRemark())
			.append("repaymentType",getRepaymentType())
			.append("receivedPrincipalTotal",getReceivedPrincipalTotal())
			.append("receivedInterestTotal",getReceivedInterestTotal())
			.append("receivedDefaultInterestTotal",getReceivedDefaultInterestTotal())
			.toString();
	}
}

