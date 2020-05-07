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
@ApiModel(value = "赎楼方式")
public class ForeclosureTypeDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 赎楼方式 */
	@ApiModelProperty(value = "赎楼方式")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 赎楼类型:银行自动扣款，柜台还款 */
	@ApiModelProperty(value = "赎楼类型:银行自动扣款，柜台还款")
	private java.lang.String foreclosureType;
	
	/** 账户类型:个人,公司 */
	@ApiModelProperty(value = "账户类型:个人,公司")
	private java.lang.String accountType;
	
	/** 银行名称 */
	@ApiModelProperty(value = "银行名称")
	private java.lang.String bankName;
	
	/** 支行名称 */
	@ApiModelProperty(value = "支行名称")
	private java.lang.String bankSubName;
	
	/** 银行卡户主名 */
	@ApiModelProperty(value = "银行卡户主名")
	private java.lang.String bankCardMaster;
	
	/** 银行卡号 */
	@ApiModelProperty(value = "银行卡号")
	private java.lang.String bankNo;
	
	/** 身份证号码 */
	@ApiModelProperty(value = "身份证号码")
	private java.lang.String idCard;
	
	/** 手机银行是否关闭:0未关闭，1已关闭 */
	@ApiModelProperty(value = "手机银行是否关闭:0未关闭，1已关闭")
	private java.lang.Integer isMobleBank;
	
	/** 网银是否关闭:0未关闭，1已关闭 */
	@ApiModelProperty(value = "网银是否关闭:0未关闭，1已关闭")
	private java.lang.Integer isNetBank;
	
	/** 银行卡密码是否修改:0未修改，1已修改 */
	@ApiModelProperty(value = "银行卡密码是否修改:0未修改，1已修改")
	private java.lang.Integer isUpdateBankCardPWD;
	
	/** 公章是否已收回:0:未收回,1:已收回 */
	@ApiModelProperty(value = "公章是否已收回:0:未收回,1:已收回")
	private java.lang.Integer isReceiveCompanyStamp;
	
	/** 财务章是否已收回:0:未收回,1:已收回 */
	@ApiModelProperty(value = "财务章是否已收回:0:未收回,1:已收回")
	private java.lang.Integer isReceiveFinanceStamp;
	
	/** 私章是否已收回:0:未收回,1:已收回 */
	@ApiModelProperty(value = "私章是否已收回:0:未收回,1:已收回")
	private java.lang.Integer isReceivePrivateStamp;
	
	/** 法人身份证是否已收回:0:未收回,1:已收回 */
	@ApiModelProperty(value = "法人身份证是否已收回:0:未收回,1:已收回")
	private java.lang.Integer isReceiveIdCard;
	
	/** 营业执照是否已收回:0:未收回,1:已收回 */
	@ApiModelProperty(value = "营业执照是否已收回:0:未收回,1:已收回")
	private java.lang.Integer isReceiveBusinessLicence;
	
	/** 银行ID */
	@ApiModelProperty(value = "银行ID")
	private java.lang.Integer bankNameId;
	
	/** 支行ID */
	@ApiModelProperty(value = "支行ID")
	private java.lang.Integer bankSubNameId;
	
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
	public void setForeclosureType(java.lang.String value) {
		this.foreclosureType = value;
	}	
	public java.lang.String getForeclosureType() {
		return this.foreclosureType;
	}
	public void setAccountType(java.lang.String value) {
		this.accountType = value;
	}	
	public java.lang.String getAccountType() {
		return this.accountType;
	}
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setBankSubName(java.lang.String value) {
		this.bankSubName = value;
	}	
	public java.lang.String getBankSubName() {
		return this.bankSubName;
	}
	public void setBankCardMaster(java.lang.String value) {
		this.bankCardMaster = value;
	}	
	public java.lang.String getBankCardMaster() {
		return this.bankCardMaster;
	}
	public void setBankNo(java.lang.String value) {
		this.bankNo = value;
	}	
	public java.lang.String getBankNo() {
		return this.bankNo;
	}
	public void setIdCard(java.lang.String value) {
		this.idCard = value;
	}	
	public java.lang.String getIdCard() {
		return this.idCard;
	}
	public void setIsMobleBank(java.lang.Integer value) {
		this.isMobleBank = value;
	}	
	public java.lang.Integer getIsMobleBank() {
		return this.isMobleBank;
	}
	public void setIsNetBank(java.lang.Integer value) {
		this.isNetBank = value;
	}	
	public java.lang.Integer getIsNetBank() {
		return this.isNetBank;
	}
	public void setIsUpdateBankCardPWD(java.lang.Integer value) {
		this.isUpdateBankCardPWD = value;
	}	
	public java.lang.Integer getIsUpdateBankCardPWD() {
		return this.isUpdateBankCardPWD;
	}
	public void setIsReceiveCompanyStamp(java.lang.Integer value) {
		this.isReceiveCompanyStamp = value;
	}	
	public java.lang.Integer getIsReceiveCompanyStamp() {
		return this.isReceiveCompanyStamp;
	}
	public void setIsReceiveFinanceStamp(java.lang.Integer value) {
		this.isReceiveFinanceStamp = value;
	}	
	public java.lang.Integer getIsReceiveFinanceStamp() {
		return this.isReceiveFinanceStamp;
	}
	public void setIsReceivePrivateStamp(java.lang.Integer value) {
		this.isReceivePrivateStamp = value;
	}	
	public java.lang.Integer getIsReceivePrivateStamp() {
		return this.isReceivePrivateStamp;
	}
	public void setIsReceiveIdCard(java.lang.Integer value) {
		this.isReceiveIdCard = value;
	}	
	public java.lang.Integer getIsReceiveIdCard() {
		return this.isReceiveIdCard;
	}
	public void setIsReceiveBusinessLicence(java.lang.Integer value) {
		this.isReceiveBusinessLicence = value;
	}	
	public java.lang.Integer getIsReceiveBusinessLicence() {
		return this.isReceiveBusinessLicence;
	}
	public void setBankNameId(java.lang.Integer value) {
		this.bankNameId = value;
	}	
	public java.lang.Integer getBankNameId() {
		return this.bankNameId;
	}
	public void setBankSubNameId(java.lang.Integer value) {
		this.bankSubNameId = value;
	}	
	public java.lang.Integer getBankSubNameId() {
		return this.bankSubNameId;
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
			.append("foreclosureType",getForeclosureType())
			.append("accountType",getAccountType())
			.append("bankName",getBankName())
			.append("bankSubName",getBankSubName())
			.append("bankCardMaster",getBankCardMaster())
			.append("bankNo",getBankNo())
			.append("idCard",getIdCard())
			.append("isMobleBank",getIsMobleBank())
			.append("isNetBank",getIsNetBank())
			.append("isUpdateBankCardPwd",getIsUpdateBankCardPWD())
			.append("isReceiveCompanyStamp",getIsReceiveCompanyStamp())
			.append("isReceiveFinanceStamp",getIsReceiveFinanceStamp())
			.append("isReceivePrivateStamp",getIsReceivePrivateStamp())
			.append("isReceiveIdCard",getIsReceiveIdCard())
			.append("isReceiveBusinessLicence",getIsReceiveBusinessLicence())
			.append("bankNameId",getBankNameId())
			.append("bankSubNameId",getBankSubNameId())
			.append("remark",getRemark())
			.toString();
	}
}

