package com.anjbo.bean.customer;

import com.anjbo.bean.BaseDto;

import java.util.Date;
import java.util.List;

public class AgencyDto extends BaseDto{
	private static final long serialVersionUID = 1L;
	/**机构*/
	private Integer id;
	/** 机构代码 */
	private Integer agencyId;
	/**机构名称*/
	private String name;
	/** 机构简称 */
	private String simName;
	/** 机构类型 */
	private Integer type;
	/** 渠道经理 */
	private String chanlMan;
	private String chanManName;
	private String chanManPhone;
	/** 联系人 */
	private String contactMan;
	/** 联系电话 */
	private String contactTel;
	/** 管理账号 */
	private String manageAccount;
	/** 密码 */
	private String managePass;
	/** 使用状态 */
	private Integer status;
	/** 备注 */
	private String remark;
	/** 最低收费标准 */
	private String chargeStandard;
	/** 是否有保证金(0，是 1，否) */
	private String isBond;
	/** 责任承担比例(单位：%) */
	private String proportionResponsibility;
	/** 保证金(单元：元) */
	private Double bond;
	/** 授信额度（万元） */
	private Double creditLimit;
	/** 关联受理员表字段 */
	/** 受理员uid */
	private String acceptUid;
	/**
	 * 机构类型
	 */
	private String agencyType;
	/**
	 * 机构类型名称
	 */
	private String agencyTypeName;
	/**
	 * 签约状态(1:未签约,2:已签约,3:签约成功)
	 */
	private int signStatus;
	/**
	 * 合作模式
	 */
	private int cooperativeModeId;
	/**
	 * 合作模式名称
	 */
	private String cooperativeMode;

	/**
	 * 签约时间

	 */
	private Date signDate;
	/**
	 * 受理经理
	 */
	private String acceptManagerUid;
	/**
	 * 受理经理名称
	 */
	private String acceptManagerName;
	/**申请时间*/
	private Date applyDate;
	/**费用支付方式*/
	List<AgencyIncomeModeDto> listIncome;
	/**机构关联产品*/
	List<AgencyProductDto> listProduct;
	/**剩余额度*/
	private Double surplusQuota;
	/**剩余额度提醒比例*/
	private Double surplusQuotaRemind;
	/**开通城市*/
	private String openCity;
	/**风险承担倍数*/
	private Double riskBearMultiple;
	/**拓展经理*/
	private String expandManagerUid;
	/**机构码*/
	private Integer agencyCode;
	/**最低留置保证金*/
	private Double minBond;

	/**
	 * 机构管理员uid

	 */
	private String accountUid;

	/**
	 * 分配机构申请的全国拓展总监uid

	 */
	private String expandChiefUid;

	public Double getMinBond() {
		return minBond;
	}

	public void setMinBond(Double minBond) {
		this.minBond = minBond;
	}
	public Integer getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(Integer agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getExpandManagerUid() {
		return expandManagerUid;
	}

	public void setExpandManagerUid(String expandManagerUid) {
		this.expandManagerUid = expandManagerUid;
	}

	public Double getRiskBearMultiple() {
		return riskBearMultiple;
	}

	public void setRiskBearMultiple(Double riskBearMultiple) {
		this.riskBearMultiple = riskBearMultiple;
	}

	public String getOpenCity() {
		return openCity;
	}
	public void setOpenCity(String openCity) {
		this.openCity = openCity;
	}

	public Double getSurplusQuota() {
		return surplusQuota;
	}

	public void setSurplusQuota(Double surplusQuota) {
		this.surplusQuota = surplusQuota;
	}

	public Double getSurplusQuotaRemind() {
		return surplusQuotaRemind;
	}

	public void setSurplusQuotaRemind(Double surplusQuotaRemind) {
		this.surplusQuotaRemind = surplusQuotaRemind;
	}

	public String getAcceptManagerUid() {
		return acceptManagerUid;
	}

	public void setAcceptManagerUid(String acceptManagerUid) {
		this.acceptManagerUid = acceptManagerUid;
	}

	public String getAcceptManagerName() {
		return acceptManagerName;
	}

	public void setAcceptManagerName(String acceptManagerName) {
		this.acceptManagerName = acceptManagerName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public List<AgencyIncomeModeDto> getListIncome() {
		return listIncome;
	}

	public void setListIncome(List<AgencyIncomeModeDto> listIncome) {
		this.listIncome = listIncome;
	}

	public List<AgencyProductDto> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<AgencyProductDto> listProduct) {
		this.listProduct = listProduct;
	}

	public String getAgencyType() {
		return agencyType;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}

	public String getAgencyTypeName() {
		return agencyTypeName;
	}

	public void setAgencyTypeName(String agencyTypeName) {
		this.agencyTypeName = agencyTypeName;
	}

	public int getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(int signStatus) {
		this.signStatus = signStatus;
	}

	public int getCooperativeModeId() {
		return cooperativeModeId;
	}

	public void setCooperativeModeId(int cooperativeModeId) {
		this.cooperativeModeId = cooperativeModeId;
	}

	public String getCooperativeMode() {
		return cooperativeMode;
	}

	public void setCooperativeMode(String cooperativeMode) {
		this.cooperativeMode = cooperativeMode;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSimName() {
		return simName;
	}
	public void setSimName(String simName) {
		this.simName = simName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getChanlMan() {
		return chanlMan;
	}
	public void setChanlMan(String chanlMan) {
		this.chanlMan = chanlMan;
	}
	public String getContactMan() {
		return contactMan;
	}
	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getManageAccount() {
		return manageAccount;
	}
	public void setManageAccount(String manageAccount) {
		this.manageAccount = manageAccount;
	}
	public String getManagePass() {
		return managePass;
	}
	public void setManagePass(String managePass) {
		this.managePass = managePass;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getChargeStandard() {
		return chargeStandard;
	}
	public void setChargeStandard(String chargeStandard) {
		this.chargeStandard = chargeStandard;
	}
	public String getIsBond() {
		return isBond;
	}
	public void setIsBond(String isBond) {
		this.isBond = isBond;
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
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public AgencyDto() {
		super();
	}
	public String getAcceptUid() {
		return acceptUid;
	}
	public void setAcceptUid(String acceptUid) {
		this.acceptUid = acceptUid;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public String getChanManName() {
		return chanManName;
	}
	public void setChanManName(String chanManName) {
		this.chanManName = chanManName;
	}

	public String getChanManPhone() {
		return chanManPhone;
	}

	public void setChanManPhone(String chanManPhone) {
		this.chanManPhone = chanManPhone;
	}
	public String getAccountUid() {
		return accountUid;
	}

	public void setAccountUid(String accountUid) {
		this.accountUid = accountUid;
	}

	public String getExpandChiefUid() {
		return expandChiefUid;
	}

	public void setExpandChiefUid(String expandChiefUid) {
		this.expandChiefUid = expandChiefUid;
	}
}
