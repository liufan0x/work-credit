/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.element;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:31
 * @version 1.0
 */
@ApiModel(value = "回款方式")
public class PaymentTypeDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 回款方式 */
	@ApiModelProperty(value = "回款方式")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 回款方式:柜台转账,网银转账,POS刷卡 */
	@ApiModelProperty(value = "回款方式:柜台转账,网银转账,POS刷卡")
	private java.lang.String paymentMode;
	
	/** 账户类型:个人,公司 */
	@ApiModelProperty(value = "账户类型:个人,公司")
	private java.lang.String paymentaccountType;
	
	/** 回款开户银行 */
	@ApiModelProperty(value = "回款开户银行")
	private java.lang.String paymentBankName;
	
	/** 回款开户支行 */
	@ApiModelProperty(value = "回款开户支行")
	private java.lang.String paymentBankSubName;
	
	/** 回款银行卡户名 */
	@ApiModelProperty(value = "回款银行卡户名")
	private java.lang.String paymentBankCardName;
	
	/** 回款银行卡密码是否被核实:0未核实,1已核实 */
	@ApiModelProperty(value = "回款银行卡密码是否被核实:0未核实,1已核实")
	private java.lang.Integer paymentBankCardPwdState;
	
	/** 回款银行卡账号 */
	@ApiModelProperty(value = "回款银行卡账号")
	private java.lang.String paymentBankNumber;
	
	/** 回款身份证号码 */
	@ApiModelProperty(value = "回款身份证号码")
	private java.lang.String paymentIdCardNo;
	
	/** 回款手机银行是否关闭:0未关闭,已关闭 */
	@ApiModelProperty(value = "回款手机银行是否关闭:0未关闭,已关闭")
	private java.lang.Integer paymentmobileBankState;
	
	/** 回款网银是否关闭:0否,1是 */
	@ApiModelProperty(value = "回款网银是否关闭:0否,1是")
	private java.lang.Integer paymentOnlineBankState;
	
	/** 回款公章是否回收(0.否，1.是) */
	@ApiModelProperty(value = "回款公章是否回收(0.否，1.是)")
	private java.lang.Integer paymentOfficialSealState;
	
	/** 回款财务章是否回收(0.否,1.是) */
	@ApiModelProperty(value = "回款财务章是否回收(0.否,1.是)")
	private java.lang.Integer paymentFinanceSealState;
	
	/** 回款私章是否回收:0.否,1.是 */
	@ApiModelProperty(value = "回款私章是否回收:0.否,1.是")
	private java.lang.Integer paymentPrivateSealState;
	
	/** 回款法人身份证是否回收:0.否,1.是 */
	@ApiModelProperty(value = "回款法人身份证是否回收:0.否,1.是")
	private java.lang.Integer paymentLegalPersonIdCardState;
	
	/** 回款营业执照是否回收:0.否,1.是 */
	@ApiModelProperty(value = "回款营业执照是否回收:0.否,1.是")
	private java.lang.Integer paymentBusinessLicenseState;
	
	/** 网银登录名 */
	@ApiModelProperty(value = "网银登录名")
	private java.lang.String netBankLoginName;
	
	/** 限额 */
	@ApiModelProperty(value = "限额")
	private Long quota;
	
	/** 网银登录密码:0未核实,1已核实 */
	@ApiModelProperty(value = "网银登录密码:0未核实,1已核实")
	private java.lang.Integer isNetBankLoginPwd;
	
	/** 支付0.01元校验网银支付密码 0.未成功支付，1已成功支付  */
	@ApiModelProperty(value = "支付0.01元校验网银支付密码 0.未成功支付，1已成功支付 ")
	private java.lang.Integer verfuyNetBankPwd;
	
	/** 银行Id */
	@ApiModelProperty(value = "银行Id")
	private java.lang.Integer paymentBankNameId;
	
	/** 支行id */
	@ApiModelProperty(value = "支行id")
	private java.lang.Integer paymentBankSubNameId;
	
	/** 银行产品 */
	@ApiModelProperty(value = "银行产品")
	private java.lang.String bankProducts;
	

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
	public void setPaymentMode(java.lang.String value) {
		this.paymentMode = value;
	}	
	public java.lang.String getPaymentMode() {
		return this.paymentMode;
	}
	public void setPaymentaccountType(java.lang.String value) {
		this.paymentaccountType = value;
	}	
	public java.lang.String getPaymentaccountType() {
		return this.paymentaccountType;
	}
	public void setPaymentBankName(java.lang.String value) {
		this.paymentBankName = value;
	}	
	public java.lang.String getPaymentBankName() {
		return this.paymentBankName;
	}
	public void setPaymentBankSubName(java.lang.String value) {
		this.paymentBankSubName = value;
	}	
	public java.lang.String getPaymentBankSubName() {
		return this.paymentBankSubName;
	}
	public void setPaymentBankCardName(java.lang.String value) {
		this.paymentBankCardName = value;
	}	
	public java.lang.String getPaymentBankCardName() {
		return this.paymentBankCardName;
	}
	public void setPaymentBankCardPwdState(java.lang.Integer value) {
		this.paymentBankCardPwdState = value;
	}	
	public java.lang.Integer getPaymentBankCardPwdState() {
		return this.paymentBankCardPwdState;
	}
	public void setPaymentBankNumber(java.lang.String value) {
		this.paymentBankNumber = value;
	}	
	public java.lang.String getPaymentBankNumber() {
		return this.paymentBankNumber;
	}
	public void setPaymentIdCardNo(java.lang.String value) {
		this.paymentIdCardNo = value;
	}	
	public java.lang.String getPaymentIdCardNo() {
		return this.paymentIdCardNo;
	}
	public void setPaymentmobileBankState(java.lang.Integer value) {
		this.paymentmobileBankState = value;
	}	
	public java.lang.Integer getPaymentmobileBankState() {
		return this.paymentmobileBankState;
	}
	public void setPaymentOnlineBankState(java.lang.Integer value) {
		this.paymentOnlineBankState = value;
	}	
	public java.lang.Integer getPaymentOnlineBankState() {
		return this.paymentOnlineBankState;
	}
	public void setPaymentOfficialSealState(java.lang.Integer value) {
		this.paymentOfficialSealState = value;
	}	
	public java.lang.Integer getPaymentOfficialSealState() {
		return this.paymentOfficialSealState;
	}
	public void setPaymentFinanceSealState(java.lang.Integer value) {
		this.paymentFinanceSealState = value;
	}	
	public java.lang.Integer getPaymentFinanceSealState() {
		return this.paymentFinanceSealState;
	}
	public void setPaymentPrivateSealState(java.lang.Integer value) {
		this.paymentPrivateSealState = value;
	}	
	public java.lang.Integer getPaymentPrivateSealState() {
		return this.paymentPrivateSealState;
	}
	public void setPaymentLegalPersonIdCardState(java.lang.Integer value) {
		this.paymentLegalPersonIdCardState = value;
	}	
	public java.lang.Integer getPaymentLegalPersonIdCardState() {
		return this.paymentLegalPersonIdCardState;
	}
	public void setPaymentBusinessLicenseState(java.lang.Integer value) {
		this.paymentBusinessLicenseState = value;
	}	
	public java.lang.Integer getPaymentBusinessLicenseState() {
		return this.paymentBusinessLicenseState;
	}
	public void setNetBankLoginName(java.lang.String value) {
		this.netBankLoginName = value;
	}	
	public java.lang.String getNetBankLoginName() {
		return this.netBankLoginName;
	}
	public void setQuota(Long value) {
		this.quota = value;
	}	
	public Long getQuota() {
		return this.quota;
	}
	public void setIsNetBankLoginPwd(java.lang.Integer value) {
		this.isNetBankLoginPwd = value;
	}	
	public java.lang.Integer getIsNetBankLoginPwd() {
		return this.isNetBankLoginPwd;
	}
	public void setVerfuyNetBankPwd(java.lang.Integer value) {
		this.verfuyNetBankPwd = value;
	}	
	public java.lang.Integer getVerfuyNetBankPwd() {
		return this.verfuyNetBankPwd;
	}
	public void setPaymentBankNameId(java.lang.Integer value) {
		this.paymentBankNameId = value;
	}	
	public java.lang.Integer getPaymentBankNameId() {
		return this.paymentBankNameId;
	}
	public void setPaymentBankSubNameId(java.lang.Integer value) {
		this.paymentBankSubNameId = value;
	}	
	public java.lang.Integer getPaymentBankSubNameId() {
		return this.paymentBankSubNameId;
	}
	public void setBankProducts(java.lang.String value) {
		this.bankProducts = value;
	}	
	public java.lang.String getBankProducts() {
		return this.bankProducts;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("paymentMode",getPaymentMode())
			.append("paymentaccountType",getPaymentaccountType())
			.append("paymentBankName",getPaymentBankName())
			.append("paymentBankSubName",getPaymentBankSubName())
			.append("paymentBankCardName",getPaymentBankCardName())
			.append("paymentBankCardPwdState",getPaymentBankCardPwdState())
			.append("paymentBankNumber",getPaymentBankNumber())
			.append("paymentIdCardNo",getPaymentIdCardNo())
			.append("paymentmobileBankState",getPaymentmobileBankState())
			.append("paymentOnlineBankState",getPaymentOnlineBankState())
			.append("paymentOfficialSealState",getPaymentOfficialSealState())
			.append("paymentFinanceSealState",getPaymentFinanceSealState())
			.append("paymentPrivateSealState",getPaymentPrivateSealState())
			.append("paymentLegalPersonIdCardState",getPaymentLegalPersonIdCardState())
			.append("paymentBusinessLicenseState",getPaymentBusinessLicenseState())
			.append("netBankLoginName",getNetBankLoginName())
			.append("quota",getQuota())
			.append("isNetBankLoginPwd",getIsNetBankLoginPwd())
			.append("verfuyNetBankPwd",getVerfuyNetBankPwd())
			.append("paymentBankNameId",getPaymentBankNameId())
			.append("paymentBankSubNameId",getPaymentBankSubNameId())
			.append("bankProducts",getBankProducts())
			.toString();
	}
}

