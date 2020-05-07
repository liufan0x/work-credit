/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-29 09:50:29
 * @version 1.0
 */
@ApiModel(value = "配置列表基础字段")
public class PageListDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 配置列表基础字段 */
	@ApiModelProperty(value = "配置列表基础字段")
	private java.lang.Integer id;
	
	/** 物理表名 */
	@ApiModelProperty(value = "物理表名")
	private String tblDataName;
	
	/** 产品Code */
	@ApiModelProperty(value = "产品Code")
	private java.lang.String productCode;
	
	/** cityCode */
	@ApiModelProperty(value = "cityCode")
	private java.lang.String cityCode;
	
	/** 城市名称 */
	@ApiModelProperty(value = "城市名称")
	private java.lang.String applyCityName;
	
	/** 城市code */
	@ApiModelProperty(value = "城市code")
	private java.lang.String applyCity;
	
	/** 选择产品名称 */
	@ApiModelProperty(value = "选择产品名称")
	private java.lang.String applyProductName;
	
	/** 选择产品code */
	@ApiModelProperty(value = "选择产品code")
	private java.lang.String applyProduct;
	
	/** 订单号 */
	@ApiModelProperty(value = "订单号")
	private java.lang.String orderNo;
	
	/** 机构名称 */
	@ApiModelProperty(value = "机构名称")
	private java.lang.String agencyName;
	
	/** 联系人电话 */
	@ApiModelProperty(value = "联系人电话")
	private java.lang.String contactsPhone;
	
	/** 机构联系人 */
	@ApiModelProperty(value = "机构联系人")
	private java.lang.String contactsName;
	
	/** 拓展经理uid */
	@ApiModelProperty(value = "拓展经理uid")
	private java.lang.String expandManagerUid;
	
	/** 拓展经理名称 */
	@ApiModelProperty(value = "拓展经理名称")
	private java.lang.String expandManagerName;
	
	/** 渠道经理uid */
	@ApiModelProperty(value = "渠道经理uid")
	private java.lang.String channelManagerUid;
	
	/** 渠道经理名称 */
	@ApiModelProperty(value = "渠道经理名称")
	private java.lang.String channelManagerName;
	
	/** 尽调经理uid */
	@ApiModelProperty(value = "尽调经理uid")
	private java.lang.String investigationManagerUid;
	
	/** 尽调经理 */
	@ApiModelProperty(value = "尽调经理")
	private java.lang.String investigationManagerName;
	
	/** 机构状态 */
	@ApiModelProperty(value = "机构状态")
	private java.lang.String state;
	
	/** 操作 */
	@ApiModelProperty(value = "操作")
	private java.lang.String operate;
	
	/** 节点id */
	@ApiModelProperty(value = "节点id")
	private java.lang.String processId;
	
	/** 来源 */
	@ApiModelProperty(value = "来源")
	private java.lang.String source;
	
	/** 上一节点处理时间 */
	@ApiModelProperty(value = "上一节点处理时间")
	private java.util.Date previousHandleTime;
	
	/** 当前处理人 */
	@ApiModelProperty(value = "当前处理人")
	private java.lang.String previousHandler;
	
	/** 上一节点处理人 */
	@ApiModelProperty(value = "上一节点处理人")
	private java.lang.String previousHandlerUid;
	
	/** 当前处理人uid */
	@ApiModelProperty(value = "当前处理人uid")
	private java.lang.String currentHandlerUid;
	
	/** 当前处理人 */
	@ApiModelProperty(value = "当前处理人")
	private java.lang.String currentHandler;
	
	/** 机构申请时间 */
	@ApiModelProperty(value = "机构申请时间")
	private java.util.Date applyDate;
	
	/** 费用支付方式(1:费用前置,2:费用后置) */
	@ApiModelProperty(value = "费用支付方式(1:费用前置,2:费用后置)")
	private java.lang.String incomeMode;
	
	/** 费用支付方式值 */
	@ApiModelProperty(value = "费用支付方式值")
	private java.lang.String incomeModeValue;
	
	/** 授信额度 */
	@ApiModelProperty(value = "授信额度")
	private Long creditLimit;
	
	/** 合作模式 */
	@ApiModelProperty(value = "合作模式")
	private java.lang.String cooperativeMode;
	
	/** 合作模式 */
	@ApiModelProperty(value = "合作模式")
	private java.lang.Integer cooperativeModeId;
	
	/** 签约申请城市 */
	@ApiModelProperty(value = "签约申请城市")
	private java.lang.String finalApplyCity;
	
	/** 机构类型 */
	@ApiModelProperty(value = "机构类型")
	private java.lang.String agencyTypeName;
	
	/** 机构类型 */
	@ApiModelProperty(value = "机构类型")
	private java.lang.String agencyType;
	
	/** 签约申请城市名称 */
	@ApiModelProperty(value = "签约申请城市名称")
	private java.lang.String finalApplyCityName;
	
	/** 机构码 */
	@ApiModelProperty(value = "机构码")
	private java.lang.Integer agencyCode;
	
	/** 是否有保证金 */
	@ApiModelProperty(value = "是否有保证金")
	private java.lang.Boolean isBond;
	
	/** 最低收费标准(元) */
	@ApiModelProperty(value = "最低收费标准(元)")
	private Long chargeStandard;
	
	/** isBondName */
	@ApiModelProperty(value = "isBondName")
	private java.lang.String isBondName;
	
	/** 测试账号uid */
	@ApiModelProperty(value = "测试账号uid")
	private java.lang.String accountUid;
	
	/** 机构id关联机构表中的id字段 */
	@ApiModelProperty(value = "机构id关联机构表中的id字段")
	private java.lang.Integer agencyId;
	
	/** 受理经理 */
	@ApiModelProperty(value = "受理经理")
	private java.lang.String acceptManagerName;
	
	/** acceptManagerUid */
	@ApiModelProperty(value = "acceptManagerUid")
	private java.lang.String acceptManagerUid;
	
	/** 测试账号有效期 */
	@ApiModelProperty(value = "测试账号有效期")
	private java.util.Date indateEnd;
	
	/** 测试账号启动状态(1:开启,2:不开启) */
	@ApiModelProperty(value = "测试账号启动状态(1:开启,2:不开启)")
	private java.lang.Integer isOpen;
	
	/** 测试账号有效期开始日期 */
	@ApiModelProperty(value = "测试账号有效期开始日期")
	private java.util.Date indateStart;
	
	/** 分配机构申请的全国拓展总监uid */
	@ApiModelProperty(value = "分配机构申请的全国拓展总监uid")
	private java.lang.String expandChiefUid;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setProductCode(java.lang.String value) {
		this.productCode = value;
	}	
	public java.lang.String getProductCode() {
		return this.productCode;
	}
	public void setCityCode(java.lang.String value) {
		this.cityCode = value;
	}	
	public java.lang.String getCityCode() {
		return this.cityCode;
	}
	public void setApplyCityName(java.lang.String value) {
		this.applyCityName = value;
	}	
	public java.lang.String getApplyCityName() {
		return this.applyCityName;
	}
	public void setApplyCity(java.lang.String value) {
		this.applyCity = value;
	}	
	public java.lang.String getApplyCity() {
		return this.applyCity;
	}
	public void setApplyProductName(java.lang.String value) {
		this.applyProductName = value;
	}	
	public java.lang.String getApplyProductName() {
		return this.applyProductName;
	}
	public void setApplyProduct(java.lang.String value) {
		this.applyProduct = value;
	}	
	public java.lang.String getApplyProduct() {
		return this.applyProduct;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setAgencyName(java.lang.String value) {
		this.agencyName = value;
	}	
	public java.lang.String getAgencyName() {
		return this.agencyName;
	}
	public void setContactsPhone(java.lang.String value) {
		this.contactsPhone = value;
	}	
	public java.lang.String getContactsPhone() {
		return this.contactsPhone;
	}
	public void setContactsName(java.lang.String value) {
		this.contactsName = value;
	}	
	public java.lang.String getContactsName() {
		return this.contactsName;
	}
	public void setExpandManagerUid(java.lang.String value) {
		this.expandManagerUid = value;
	}	
	public java.lang.String getExpandManagerUid() {
		return this.expandManagerUid;
	}
	public void setExpandManagerName(java.lang.String value) {
		this.expandManagerName = value;
	}	
	public java.lang.String getExpandManagerName() {
		return this.expandManagerName;
	}
	public void setChannelManagerUid(java.lang.String value) {
		this.channelManagerUid = value;
	}	
	public java.lang.String getChannelManagerUid() {
		return this.channelManagerUid;
	}
	public void setChannelManagerName(java.lang.String value) {
		this.channelManagerName = value;
	}	
	public java.lang.String getChannelManagerName() {
		return this.channelManagerName;
	}
	public void setInvestigationManagerUid(java.lang.String value) {
		this.investigationManagerUid = value;
	}	
	public java.lang.String getInvestigationManagerUid() {
		return this.investigationManagerUid;
	}
	public void setInvestigationManagerName(java.lang.String value) {
		this.investigationManagerName = value;
	}	
	public java.lang.String getInvestigationManagerName() {
		return this.investigationManagerName;
	}
	public void setState(java.lang.String value) {
		this.state = value;
	}	
	public java.lang.String getState() {
		return this.state;
	}
	public void setOperate(java.lang.String value) {
		this.operate = value;
	}	
	public java.lang.String getOperate() {
		return this.operate;
	}
	public void setProcessId(java.lang.String value) {
		this.processId = value;
	}	
	public java.lang.String getProcessId() {
		return this.processId;
	}
	public void setSource(java.lang.String value) {
		this.source = value;
	}	
	public java.lang.String getSource() {
		return this.source;
	}
	public void setPreviousHandleTime(java.util.Date value) {
		this.previousHandleTime = value;
	}	
	public java.util.Date getPreviousHandleTime() {
		return this.previousHandleTime;
	}
	public void setPreviousHandler(java.lang.String value) {
		this.previousHandler = value;
	}	
	public java.lang.String getPreviousHandler() {
		return this.previousHandler;
	}
	public void setPreviousHandlerUid(java.lang.String value) {
		this.previousHandlerUid = value;
	}	
	public java.lang.String getPreviousHandlerUid() {
		return this.previousHandlerUid;
	}
	public void setCurrentHandlerUid(java.lang.String value) {
		this.currentHandlerUid = value;
	}	
	public java.lang.String getCurrentHandlerUid() {
		return this.currentHandlerUid;
	}
	public void setCurrentHandler(java.lang.String value) {
		this.currentHandler = value;
	}	
	public java.lang.String getCurrentHandler() {
		return this.currentHandler;
	}
	public void setApplyDate(java.util.Date value) {
		this.applyDate = value;
	}	
	public java.util.Date getApplyDate() {
		return this.applyDate;
	}
	public void setIncomeMode(java.lang.String value) {
		this.incomeMode = value;
	}	
	public java.lang.String getIncomeMode() {
		return this.incomeMode;
	}
	public void setIncomeModeValue(java.lang.String value) {
		this.incomeModeValue = value;
	}	
	public java.lang.String getIncomeModeValue() {
		return this.incomeModeValue;
	}
	public void setCreditLimit(Long value) {
		this.creditLimit = value;
	}	
	public Long getCreditLimit() {
		return this.creditLimit;
	}
	public void setCooperativeMode(java.lang.String value) {
		this.cooperativeMode = value;
	}	
	public java.lang.String getCooperativeMode() {
		return this.cooperativeMode;
	}
	public void setCooperativeModeId(java.lang.Integer value) {
		this.cooperativeModeId = value;
	}	
	public java.lang.Integer getCooperativeModeId() {
		return this.cooperativeModeId;
	}
	public void setFinalApplyCity(java.lang.String value) {
		this.finalApplyCity = value;
	}	
	public java.lang.String getFinalApplyCity() {
		return this.finalApplyCity;
	}
	public void setAgencyTypeName(java.lang.String value) {
		this.agencyTypeName = value;
	}	
	public java.lang.String getAgencyTypeName() {
		return this.agencyTypeName;
	}
	public void setAgencyType(java.lang.String value) {
		this.agencyType = value;
	}	
	public java.lang.String getAgencyType() {
		return this.agencyType;
	}
	public void setFinalApplyCityName(java.lang.String value) {
		this.finalApplyCityName = value;
	}	
	public java.lang.String getFinalApplyCityName() {
		return this.finalApplyCityName;
	}
	public void setAgencyCode(java.lang.Integer value) {
		this.agencyCode = value;
	}	
	public java.lang.Integer getAgencyCode() {
		return this.agencyCode;
	}
	public void setIsBond(java.lang.Boolean value) {
		this.isBond = value;
	}	
	public java.lang.Boolean getIsBond() {
		return this.isBond;
	}
	public void setChargeStandard(Long value) {
		this.chargeStandard = value;
	}	
	public Long getChargeStandard() {
		return this.chargeStandard;
	}
	public void setIsBondName(java.lang.String value) {
		this.isBondName = value;
	}	
	public java.lang.String getIsBondName() {
		return this.isBondName;
	}
	public void setAccountUid(java.lang.String value) {
		this.accountUid = value;
	}	
	public java.lang.String getAccountUid() {
		return this.accountUid;
	}
	public void setAgencyId(java.lang.Integer value) {
		this.agencyId = value;
	}	
	public java.lang.Integer getAgencyId() {
		return this.agencyId;
	}
	public void setAcceptManagerName(java.lang.String value) {
		this.acceptManagerName = value;
	}	
	public java.lang.String getAcceptManagerName() {
		return this.acceptManagerName;
	}
	public void setAcceptManagerUid(java.lang.String value) {
		this.acceptManagerUid = value;
	}	
	public java.lang.String getAcceptManagerUid() {
		return this.acceptManagerUid;
	}
	public void setIndateEnd(java.util.Date value) {
		this.indateEnd = value;
	}	
	public java.util.Date getIndateEnd() {
		return this.indateEnd;
	}
	public void setIsOpen(java.lang.Integer value) {
		this.isOpen = value;
	}	
	public java.lang.Integer getIsOpen() {
		return this.isOpen;
	}
	public void setIndateStart(java.util.Date value) {
		this.indateStart = value;
	}	
	public java.util.Date getIndateStart() {
		return this.indateStart;
	}
	public void setExpandChiefUid(java.lang.String value) {
		this.expandChiefUid = value;
	}	
	public java.lang.String getExpandChiefUid() {
		return this.expandChiefUid;
	}
	public String getTblDataName() {
		return tblDataName;
	}
	public void setTblDataName(String tblDataName) {
		this.tblDataName = tblDataName;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("productCode",getProductCode())
			.append("cityCode",getCityCode())
			.append("applyCityName",getApplyCityName())
			.append("applyCity",getApplyCity())
			.append("applyProductName",getApplyProductName())
			.append("applyProduct",getApplyProduct())
			.append("orderNo",getOrderNo())
			.append("agencyName",getAgencyName())
			.append("contactsPhone",getContactsPhone())
			.append("contactsName",getContactsName())
			.append("expandManagerUid",getExpandManagerUid())
			.append("expandManagerName",getExpandManagerName())
			.append("channelManagerUid",getChannelManagerUid())
			.append("channelManagerName",getChannelManagerName())
			.append("investigationManagerUid",getInvestigationManagerUid())
			.append("investigationManagerName",getInvestigationManagerName())
			.append("state",getState())
			.append("operate",getOperate())
			.append("processId",getProcessId())
			.append("source",getSource())
			.append("previousHandleTime",getPreviousHandleTime())
			.append("previousHandler",getPreviousHandler())
			.append("previousHandlerUid",getPreviousHandlerUid())
			.append("currentHandlerUid",getCurrentHandlerUid())
			.append("currentHandler",getCurrentHandler())
			.append("applyDate",getApplyDate())
			.append("incomeMode",getIncomeMode())
			.append("incomeModeValue",getIncomeModeValue())
			.append("creditLimit",getCreditLimit())
			.append("cooperativeMode",getCooperativeMode())
			.append("cooperativeModeId",getCooperativeModeId())
			.append("finalApplyCity",getFinalApplyCity())
			.append("agencyTypeName",getAgencyTypeName())
			.append("agencyType",getAgencyType())
			.append("finalApplyCityName",getFinalApplyCityName())
			.append("agencyCode",getAgencyCode())
			.append("isBond",getIsBond())
			.append("chargeStandard",getChargeStandard())
			.append("isBondName",getIsBondName())
			.append("accountUid",getAccountUid())
			.append("agencyId",getAgencyId())
			.append("acceptManagerName",getAcceptManagerName())
			.append("acceptManagerUid",getAcceptManagerUid())
			.append("indateEnd",getIndateEnd())
			.append("isOpen",getIsOpen())
			.append("indateStart",getIndateStart())
			.append("expandChiefUid",getExpandChiefUid())
			.toString();
	}
}

