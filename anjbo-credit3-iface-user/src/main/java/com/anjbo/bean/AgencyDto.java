/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:45
 * @version 1.0
 */
@ApiModel(value = "机构")
public class AgencyDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 机构 */
	@ApiModelProperty(value = "机构")
	private java.lang.Integer id;
	
	/** 机构名称 */
	@ApiModelProperty(value = "机构名称")
	private java.lang.String name;
	
	/** 机构简称 */
	@ApiModelProperty(value = "机构简称")
	private java.lang.String simName;
	
	/** 机构类型，关联tbl_fc_system_agency_type */
	@ApiModelProperty(value = "机构类型，关联tbl_fc_system_agency_type")
	private java.lang.Integer type;
	
	/** 渠道经理，关联用户表 tbl_fc_system_user */
	@ApiModelProperty(value = "渠道经理，关联用户表 tbl_fc_system_user")
	private java.lang.String chanlMan;
	
	/** 渠道经理 */
	@ApiModelProperty(value = "渠道经理")
	private java.lang.String chanManName;
	
	/** 联系人 */
	@ApiModelProperty(value = "联系人")
	private java.lang.String contactMan;
	
	/** 联系电话 */
	@ApiModelProperty(value = "联系电话")
	private java.lang.String contactTel;
	
	/** 管理账号（联系人首字母+5位数字随机数） */
	@ApiModelProperty(value = "管理账号（联系人首字母+5位数字随机数）")
	private java.lang.String manageAccount;
	
	/** 密码 */
	@ApiModelProperty(value = "密码")
	private java.lang.String managePass;
	
	/** 使用状态，0禁用，1可用 */
	@ApiModelProperty(value = "使用状态，0禁用，1可用")
	private Integer status;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 最低收费标准 */
	@ApiModelProperty(value = "最低收费标准")
	private Double chargeStandard;
	
	/** 是否有保证金(0，是 1，否) */
	@ApiModelProperty(value = "是否有保证金(0，是 1，否)")
	private java.lang.String isBond;
	
	/** 责任承担比例(单位：%) */
	@ApiModelProperty(value = "责任承担比例(单位：%)")
	private String proportionResponsibility;
	
	/** 保证金(单元：元) */
	@ApiModelProperty(value = "保证金(单元：元)")
	private Double bond;
	
	/** 授信额度（万元） */
	@ApiModelProperty(value = "授信额度（万元）")
	private Double creditLimit;
	
	/** 风险承担倍数 */
	@ApiModelProperty(value = "风险承担倍数")
	private java.lang.Double riskBearMultiple;
	
	/** 开通城市 */
	@ApiModelProperty(value = "开通城市")
	private java.lang.String openCity;
	
	/** 剩余额度提醒比例/% */
	@ApiModelProperty(value = "剩余额度提醒比例/%")
	private java.lang.Double surplusQuotaRemind;
	
	/** 剩余额度 */
	@ApiModelProperty(value = "剩余额度")
	private Double surplusQuota;
	
	/** 申请时间 */
	@ApiModelProperty(value = "申请时间")
	private java.util.Date applyDate;
	
	/** 拓展经理 */
	@ApiModelProperty(value = "拓展经理")
	private java.lang.String expandManagerUid;
	
	/** 受理经理uid */
	@ApiModelProperty(value = "受理经理uid")
	private java.lang.String acceptManagerUid;
	
	/** 签约时间 */
	@ApiModelProperty(value = "签约时间")
	private java.util.Date signDate;
	
	/** 合作模式(1.兜底,2非兜底) */
	@ApiModelProperty(value = "合作模式(1.兜底,2非兜底)")
	private java.lang.Integer cooperativeModeId;

	/** 合作模式名称 */
	@ApiModelProperty(value = "合作模式名称")
	private String cooperativeMode;
	
	/** 签约状态(1:未签约,2:已签约,3:已解约) */
	@ApiModelProperty(value = "签约状态(1:未签约,2:已签约,3:已解约)")
	private int signStatus;
	
	/** 机构类型 */
	@ApiModelProperty(value = "机构类型")
	private java.lang.String agencyType;
	
	/** 机构类型名称 */
	@ApiModelProperty(value = "机构类型名称")
	private java.lang.String typeName;
	
	/** 机构码 */
	@ApiModelProperty(value = "机构码")
	private java.lang.Integer agencyCode;
	
	/** 最低留置保证金 */
	@ApiModelProperty(value = "最低留置保证金")
	private Double minBond;
	
	/** 机构管理员uid */
	@ApiModelProperty(value = "机构管理员uid")
	private java.lang.String accountUid;
	
	/** 分配机构申请的全国拓展总监uid */
	@ApiModelProperty(value = "分配机构申请的全国拓展总监uid")
	private java.lang.String expandChiefUid;
	

	/**开始签约时间(主要用于列表检索)*/
	@ApiModelProperty(value = "开始签约时间(主要用于列表检索)")
	private Date startSignDate;
	
	/**结束签约时间(主要用于列表检索)*/
	@ApiModelProperty(value = "结束签约时间(主要用于列表检索)")
	private Date endSignDate;
	
	/**关联受理员集合*/
	@ApiModelProperty(value = "关联受理员集合")
	private List<AgencyAcceptDto> agencyAcceptDtos;
	
	/**支付方式集合*/
	@ApiModelProperty(value = "支付方式集合")
	private List<AgencyIncomeModeDto> agencyIncomeModeDtos;

	/**机构关联产品集合*/
	@ApiModelProperty(value = "机构关联产品集合")
	private List<AgencyProductDto> agencyProductDtos;
	
	/**订单号(tbl_sm_list中的订单号*/
	@ApiModelProperty(value = "订单号(tbl_sm_list中的订单号)")
	private String orderNo;
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}	
	public java.lang.String getName() {
		return this.name;
	}
	public void setSimName(java.lang.String value) {
		this.simName = value;
	}	
	public java.lang.String getSimName() {
		return this.simName;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setChanlMan(java.lang.String value) {
		this.chanlMan = value;
	}	
	public java.lang.String getChanlMan() {
		return this.chanlMan;
	}
	public void setContactMan(java.lang.String value) {
		this.contactMan = value;
	}	
	public java.lang.String getContactMan() {
		return this.contactMan;
	}
	public void setContactTel(java.lang.String value) {
		this.contactTel = value;
	}	
	public java.lang.String getContactTel() {
		return this.contactTel;
	}
	public void setManageAccount(java.lang.String value) {
		this.manageAccount = value;
	}	
	public java.lang.String getManageAccount() {
		return this.manageAccount;
	}
	public void setManagePass(java.lang.String value) {
		this.managePass = value;
	}	
	public java.lang.String getManagePass() {
		return this.managePass;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}	
	public Integer getStatus() {
		return this.status;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public Double getChargeStandard() {
		return chargeStandard;
	}
	public void setChargeStandard(Double chargeStandard) {
		this.chargeStandard = chargeStandard;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setIsBond(java.lang.String value) {
		this.isBond = value;
	}	
	public java.lang.String getIsBond() {
		return this.isBond;
	}
	public String getProportionResponsibility() {
		return proportionResponsibility;
	}
	public void setProportionResponsibility(String proportionResponsibility) {
		this.proportionResponsibility = proportionResponsibility;
	}
	public Double getBond() {
		return bond;
	}
	public void setBond(Double bond) {
		this.bond = bond;
	}
	public void setRiskBearMultiple(java.lang.Double value) {
		this.riskBearMultiple = value;
	}	
	public java.lang.Double getRiskBearMultiple() {
		return this.riskBearMultiple;
	}
	public void setOpenCity(java.lang.String value) {
		this.openCity = value;
	}	
	public java.lang.String getOpenCity() {
		return this.openCity;
	}
	public void setSurplusQuotaRemind(java.lang.Double value) {
		this.surplusQuotaRemind = value;
	}	
	public java.lang.Double getSurplusQuotaRemind() {
		return this.surplusQuotaRemind;
	}
	public Double getSurplusQuota() {
		return surplusQuota;
	}
	public void setSurplusQuota(Double surplusQuota) {
		this.surplusQuota = surplusQuota;
	}
	public void setApplyDate(java.util.Date value) {
		this.applyDate = value;
	}	
	public java.util.Date getApplyDate() {
		return this.applyDate;
	}
	public void setExpandManagerUid(java.lang.String value) {
		this.expandManagerUid = value;
	}	
	public java.lang.String getExpandManagerUid() {
		return this.expandManagerUid;
	}
	public void setAcceptManagerUid(java.lang.String value) {
		this.acceptManagerUid = value;
	}	
	public java.lang.String getAcceptManagerUid() {
		return this.acceptManagerUid;
	}
	public void setSignDate(java.util.Date value) {
		this.signDate = value;
	}	
	public java.util.Date getSignDate() {
		return this.signDate;
	}
	public void setCooperativeModeId(java.lang.Integer value) {
		this.cooperativeModeId = value;
	}	
	public java.lang.Integer getCooperativeModeId() {
		return this.cooperativeModeId;
	}
	public int getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(int signStatus) {
		this.signStatus = signStatus;
	}
	public void setAgencyType(java.lang.String value) {
		this.agencyType = value;
	}	
	public java.lang.String getAgencyType() {
		return this.agencyType;
	}
	public void setAgencyCode(java.lang.Integer value) {
		this.agencyCode = value;
	}	
	public java.lang.Integer getAgencyCode() {
		return this.agencyCode;
	}
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Double getMinBond() {
		return minBond;
	}
	public void setMinBond(Double minBond) {
		this.minBond = minBond;
	}
	public void setAccountUid(java.lang.String value) {
		this.accountUid = value;
	}	
	public java.lang.String getAccountUid() {
		return this.accountUid;
	}
	public void setExpandChiefUid(java.lang.String value) {
		this.expandChiefUid = value;
	}	
	public java.lang.String getExpandChiefUid() {
		return this.expandChiefUid;
	}
	public java.lang.String getTypeName() {
		return typeName;
	}
	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}
	public String getCooperativeMode() {
		return cooperativeMode;
	}
	public void setCooperativeMode(String cooperativeMode) {
		this.cooperativeMode = cooperativeMode;
	}
	
	public Date getStartSignDate() {
		return startSignDate;
	}
	public void setStartSignDate(Date startSignDate) {
		this.startSignDate = startSignDate;
	}
	public Date getEndSignDate() {
		return endSignDate;
	}
	public void setEndSignDate(Date endSignDate) {
		this.endSignDate = endSignDate;
	}
	public java.lang.String getChanManName() {
		return chanManName;
	}
	public void setChanManName(java.lang.String chanManName) {
		this.chanManName = chanManName;
	}
	public List<AgencyAcceptDto> getAgencyAcceptDtos() {
		return agencyAcceptDtos;
	}
	public void setAgencyAcceptDtos(List<AgencyAcceptDto> agencyAcceptDtos) {
		this.agencyAcceptDtos = agencyAcceptDtos;
	}
	public List<AgencyIncomeModeDto> getAgencyIncomeModeDtos() {
		return agencyIncomeModeDtos;
	}
	public void setAgencyIncomeModeDtos(List<AgencyIncomeModeDto> agencyIncomeModeDtos) {
		this.agencyIncomeModeDtos = agencyIncomeModeDtos;
	}
	public List<AgencyProductDto> getAgencyProductDtos() {
		return agencyProductDtos;
	}
	public void setAgencyProductDtos(List<AgencyProductDto> agencyProductDtos) {
		this.agencyProductDtos = agencyProductDtos;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("simName",getSimName())
			.append("type",getType())
			.append("chanlMan",getChanlMan())
			.append("contactMan",getContactMan())
			.append("contactTel",getContactTel())
			.append("manageAccount",getManageAccount())
			.append("managePass",getManagePass())
			.append("status",getStatus())
			.append("remark",getRemark())
			.append("chargeStandard",getChargeStandard())
			.append("isBond",getIsBond())
			.append("proportionResponsibility",getProportionResponsibility())
			.append("bond",getBond())
			.append("creditLimit",getCreditLimit())
			.append("riskBearMultiple",getRiskBearMultiple())
			.append("openCity",getOpenCity())
			.append("surplusQuotaRemind",getSurplusQuotaRemind())
			.append("surplusQuota",getSurplusQuota())
			.append("applyDate",getApplyDate())
			.append("expandManagerUid",getExpandManagerUid())
			.append("acceptManagerUid",getAcceptManagerUid())
			.append("signDate",getSignDate())
			.append("cooperativeModeId",getCooperativeModeId())
			.append("signStatus",getSignStatus())
			.append("agencyType",getAgencyType())
			.append("agencyCode",getAgencyCode())
			.append("minBond",getMinBond())
			.append("accountUid",getAccountUid())
			.append("expandChiefUid",getExpandChiefUid())
			.toString();
	}
}

