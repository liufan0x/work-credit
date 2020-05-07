package com.anjbo.bean.customer;

import com.anjbo.bean.BaseDto;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
  * 机构 [实体类]
  * @Description: tbl_customer_agency
  * @author 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
public class CustomerAgencyDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = 2810546279621017205L;
	/**
	*字段名：id:
	*名称：机构
	*/
	private int id;
	
	/**
	*字段名：name:
	*名称：机构名称
	*/
	private String name;

	/**
	*字段名：simName:

	*名称：机构简称
	*/
	private String simName;
	
	/**
	*字段名：type:
	*名称：机构类型，关联tbl_fc_system_agency_type
	*/
	private int type;
	
	/**
	*字段名：chanlMan:
	*名称：渠道经理，关联用户表 tbl_fc_system_user
	*/
	private String chanlMan;
	private String chanManName;

	/**
	*字段名：contactMan:
	*名称：联系人
	*/
	private String contactMan;
	
	/**
	*字段名：contactTel:
	*名称：联系电话
	*/
	private String contactTel;
	
	/**
	*字段名：manageAccount:
	*名称：管理账号（联系人首字母+5位数字随机数）
	*/
	private String manageAccount;
	
	/**
	*字段名：managePass:
	*名称：密码
	*/
	private String managePass;
	
	/**
	*字段名：status:
	*名称：使用状态，0禁用，1可用
	*/
	private int status;
	
	/**
	*字段名：createTime:
	*名称：创建时间
	*/
	private Date createTime;
	
	/**
	*字段名：updateTime:
	*名称：最后更新时间
	*/
	private Date updateTime;
	
	/**
	*字段名：createUid:
	*名称：创建人uid
	*/
	private String createUid;
	
	/**
	*字段名：updateUid:
	*名称：updateUid
	*/
	private String updateUid;
	
	/**
	*字段名：remark:
	*名称：备注
	*/
	private String remark;
	
	/**
	*字段名：chargeStandard:
	*名称：最低收费标准
	*/
	private double chargeStandard;
	
	/**
	*字段名：isBond:
	*名称：是否有保证金(0，是 1，否)
	*/
	private String isBond;
	
	/**
	*字段名：proportionResponsibility:
	*名称：责任承担比例(单位：%)
	*/
	private double proportionResponsibility;
	
	/**
	*字段名：bond:
	*名称：保证金(单元：元)
	*/
	private double bond;
	
	/**
	*字段名：creditLimit:
	*名称：授信额度（万元）
	*/
	private double creditLimit;
	
	/**状态扩展**/
	private int statusExtension=-1;
	
	/**机构类型名称**/
	private String typeName;
	/**机构码*/
	private Integer agencyId;
	/**机构类型*/
	private String agencyType;
	/**签约状态(1:未签约,2:已签约,3:已解约)*/
	private Integer signStatus;
	/**合作模式*/
	private Integer cooperativeModeId;
	/**合作模式名称*/
	private String cooperativeMode;

	/**签约时间*/
	private Date signDate;
	/**签约时间*/
	private String signDateStr;

	/**受理经理*/
	private String acceptManagerUid;
	/**受理经理名称*/
	private String acceptManagerName;

	private List<CustomerAgencyAcceptDto> customerAgencyAcceptDtos;
	/**费用支付方式*/
	List<AgencyIncomeModeDto> listIncome;
	/**机构关联产品*/
	List<AgencyProductDto> listProduct;
	/**申请时间*/
	private Date applyDate;
	/**申请时间*/
	private String applyDateStr;

	/**剩余额度*/
	private Double surplusQuota;
	/**剩余额度提醒比例*/
	private Double surplusQuotaRemind;

	/**开通城市*/
	private String openCity;
	/**开始签约时间(主要用于列表检索)*/
	private Date startSignDate;
	/**结束签约时间(主要用于列表检索)*/
	private Date endSignDate;
	/**风险承担倍数*/
	private Double riskBearMultiple;

	/**机构码*/
	private Integer agencyCode;
	/**拓展经理*/
	private String expandManagerUid;

	private String expandManagerName;
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
	/**
	 * uids拼接(主要用于列表查询)
	 */
	private String uids;
	/**
	 * 机构id拼接(机构关联受理员的机构id拼接主要用于列表查询)
	 */
	private String ids;

	public Double getMinBond() {
		return minBond;
	}

	public void setMinBond(Double minBond) {
		this.minBond = minBond;
	}

	public String getChanManName() {
		return chanManName;
	}

	public void setChanManName(String chanManName) {
		this.chanManName = chanManName;
	}
	public String getExpandManagerUid() {
		return expandManagerUid;
	}

	public void setExpandManagerUid(String expandManagerUid) {
		this.expandManagerUid = expandManagerUid;
	}

	public String getExpandManagerName() {
		return expandManagerName;
	}

	public void setExpandManagerName(String expandManagerName) {
		this.expandManagerName = expandManagerName;
	}

	public Integer getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(Integer agencyCode) {
		this.agencyCode = agencyCode;
	}
	public Double getRiskBearMultiple() {
		return riskBearMultiple;
	}

	public void setRiskBearMultiple(Double riskBearMultiple) {
		this.riskBearMultiple = riskBearMultiple;
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

	/**id: 机构*/
	public int getId() {
		return id;
	}

	/**id: 机构*/
	public void setId(int id) {
		this.id = id;
	}
	
	/**name: 机构名称*/
	public String getName() {
		return name;
	}

	/**name: 机构名称*/
	public void setName(String name) {
		this.name = name;
	}
	
	/**simName: 机构简称*/
	public String getSimName() {
		return simName;
	}

	/**simName: 机构简称*/
	public void setSimName(String simName) {
		this.simName = simName;
	}
	
	/**type: 机构类型，关联tbl_fc_system_agency_type*/
	public int getType() {
		return type;
	}

	/**type: 机构类型，关联tbl_fc_system_agency_type*/
	public void setType(int type) {
		this.type = type;
	}
	
	/**chanlMan: 渠道经理，关联用户表 tbl_fc_system_user*/
	public String getChanlMan() {
		return chanlMan;
	}

	/**chanlMan: 渠道经理，关联用户表 tbl_fc_system_user*/
	public void setChanlMan(String chanlMan) {
		this.chanlMan = chanlMan;
	}
	
	/**contactMan: 联系人*/
	public String getContactMan() {
		return contactMan;
	}

	/**contactMan: 联系人*/
	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}
	
	/**contactTel: 联系电话*/
	public String getContactTel() {
		return contactTel;
	}

	/**contactTel: 联系电话*/
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	
	/**manageAccount: 管理账号（联系人首字母+5位数字随机数）*/
	public String getManageAccount() {
		return manageAccount;
	}

	/**manageAccount: 管理账号（联系人首字母+5位数字随机数）*/
	public void setManageAccount(String manageAccount) {
		this.manageAccount = manageAccount;
	}
	
	/**managePass: 密码*/
	public String getManagePass() {
		return managePass;
	}

	/**managePass: 密码*/
	public void setManagePass(String managePass) {
		this.managePass = managePass;
	}
	
	/**status: 使用状态，0禁用，1可用*/
	public int getStatus() {
		return status;
	}

	/**status: 使用状态，0禁用，1可用*/
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**createTime: 创建时间*/
	public Date getCreateTime() {
		return createTime;
	}

	/**createTime: 创建时间*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**updateTime: 最后更新时间*/
	public Date getUpdateTime() {
		return updateTime;
	}

	/**updateTime: 最后更新时间*/
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**createUid: 创建人uid*/
	public String getCreateUid() {
		return createUid;
	}

	/**createUid: 创建人uid*/
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	
	/**updateUid: updateUid*/
	public String getUpdateUid() {
		return updateUid;
	}

	/**updateUid: updateUid*/
	public void setUpdateUid(String updateUid) {
		this.updateUid = updateUid;
	}
	
	/**remark: 备注*/
	public String getRemark() {
		return remark;
	}

	/**remark: 备注*/
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**chargeStandard: 最低收费标准*/
	public double getChargeStandard() {
		return chargeStandard;
	}

	/**chargeStandard: 最低收费标准*/
	public void setChargeStandard(double chargeStandard) {
		this.chargeStandard = chargeStandard;
	}
	
	/**isBond: 是否有保证金(0，是 1，否)*/
	public String getIsBond() {
		return isBond;
	}

	/**isBond: 是否有保证金(0，是 1，否)*/
	public void setIsBond(String isBond) {
		this.isBond = isBond;
	}
	
	/**proportionResponsibility: 责任承担比例(单位：%)*/
	public double getProportionResponsibility() {
		return proportionResponsibility;
	}

	/**proportionResponsibility: 责任承担比例(单位：%)*/
	public void setProportionResponsibility(double proportionResponsibility) {
		this.proportionResponsibility = proportionResponsibility;
	}
	
	/**bond: 保证金(单元：元)*/
	public double getBond() {
		return bond;
	}

	/**bond: 保证金(单元：元)*/
	public void setBond(double bond) {
		this.bond = bond;
	}
	
	/**creditLimit: 授信额度（万元）*/
	public double getCreditLimit() {
		return creditLimit;
	}

	/**creditLimit: 授信额度（万元）*/
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	

	public List<CustomerAgencyAcceptDto> getCustomerAgencyAcceptDtos() {
		return customerAgencyAcceptDtos;
	}

	public void setCustomerAgencyAcceptDtos(
			List<CustomerAgencyAcceptDto> customerAgencyAcceptDtos) {
		this.customerAgencyAcceptDtos = customerAgencyAcceptDtos;
	}

	public CustomerAgencyDto(){
	
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyType() {
		return agencyType;
	}

	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}

	public Integer getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}

	public Integer getCooperativeModeId() {
		return cooperativeModeId;
	}

	public void setCooperativeModeId(Integer cooperativeModeId) {
		this.cooperativeModeId = cooperativeModeId;
	}

	public String getCooperativeMode() {
		return cooperativeMode;
	}

	public void setCooperativeMode(String cooperativeMode) {
		this.cooperativeMode = cooperativeMode;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getStatusExtension() {
		return statusExtension;
	}

	public void setStatusExtension(int statusExtension) {
		this.statusExtension = statusExtension;
	}

	public String getApplyDateStr() {
		if(null!=this.applyDate){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");
			applyDateStr = format.format(this.applyDate);
		}
		return applyDateStr;
	}

	public void setApplyDateStr(String applyDateStr) {
		this.applyDateStr = applyDateStr;
	}
	public String getSignDateStr() {
		if(null!=this.signDate){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			signDateStr = format.format(this.signDate);
		}
		return signDateStr;
	}

	public void setSignDateStr(String signDateStr) {
		this.signDateStr = signDateStr;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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

	public String getUids() {
		return uids;
	}

	public void setUids(String uids) {
		this.uids = uids;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}