/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.contract;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 18:11:02
 * @version 1.0
 */
@ApiModel(value = "合同列表")
public class ContractListDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 合同列表 */
	@ApiModelProperty(value = "合同列表")
	private java.lang.Integer id;
	
	/** 订单所属机构 */
	@ApiModelProperty(value = "订单所属机构")
	private java.lang.Integer agencyId;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 操作类型*/
	@ApiModelProperty(value = "操作类型")
	private java.lang.Integer type;
	
	
	/** 模板类型*/
	@ApiModelProperty(value = "模板类型")
	private java.lang.Integer fl;
	
	/** 城市Code */
	@ApiModelProperty(value = "城市Code")
	private java.lang.String cityCode;
	
	/** 城市名称 */
	@ApiModelProperty(value = "城市名称")
	private java.lang.String cityName;
	
	/** 产品编码 */
	@ApiModelProperty(value = "产品编码")
	private java.lang.String productCode;
	
	/** 产品名称 */
	@ApiModelProperty(value = "产品名称")
	private java.lang.String productName;
	
	/** 客户姓名 */
	@ApiModelProperty(value = "客户姓名")
	private java.lang.String customerName;
	
	/** 借款金额（万元） */
	@ApiModelProperty(value = "借款金额（万元）")
	private Long borrowingAmount;
	
	/** 借款期限（天） */
	@ApiModelProperty(value = "借款期限（天）")
	private java.lang.Integer borrowingDay;
	
	/** 关联订单号 */
	@ApiModelProperty(value = "关联订单号")
	private java.lang.String relationOrderNo;
	
	/** 数据 */
	@ApiModelProperty(value = "数据")
	private String data;
	
	/** 数据Map */
	@ApiModelProperty(value = "数据Map")
	private Map<String, String> jsonOject;
	
	/** 合同ids */
	@ApiModelProperty(value = "合同ids")
	private java.lang.String contractIds;

	/** 创建开始时间 */
	@ApiModelProperty(value = "创建开始时间")
	private String createTimeStart;

	/** 创建结束时间 */
	@ApiModelProperty(value = "创建结束时间")
	private String createTimeEnd;
	
	/** 模板地址 */
	@ApiModelProperty(value = "模板地址")
	private String paths;
	
	@ApiModelProperty(value = "合同名称")
	private String contractNames;

	public java.lang.Integer getFl() {
		return fl;
	}
	public void setFl(java.lang.Integer fl) {
		this.fl = fl;
	}
	
	public String getContractNames() {
		return contractNames;
	}
	public void setContractNames(String contractNames) {
		this.contractNames = contractNames;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setAgencyId(java.lang.Integer value) {
		this.agencyId = value;
	}	
	public java.lang.Integer getAgencyId() {
		return this.agencyId;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	
	public java.lang.Integer getType() {
		return type;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public void setCityCode(java.lang.String value) {
		this.cityCode = value;
	}	
	public java.lang.String getCityCode() {
		return this.cityCode;
	}
	public void setCityName(java.lang.String value) {
		this.cityName = value;
	}	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setProductCode(java.lang.String value) {
		this.productCode = value;
	}	
	public java.lang.String getProductCode() {
		return this.productCode;
	}
	public void setProductName(java.lang.String value) {
		this.productName = value;
	}	
	public java.lang.String getProductName() {
		return this.productName;
	}
	public void setCustomerName(java.lang.String value) {
		this.customerName = value;
	}	
	public java.lang.String getCustomerName() {
		return this.customerName;
	}
	public void setBorrowingAmount(Long value) {
		this.borrowingAmount = value;
	}	
	public Long getBorrowingAmount() {
		return this.borrowingAmount;
	}
	public void setBorrowingDay(java.lang.Integer value) {
		this.borrowingDay = value;
	}	
	public java.lang.Integer getBorrowingDay() {
		return this.borrowingDay;
	}
	public void setRelationOrderNo(java.lang.String value) {
		this.relationOrderNo = value;
	}	
	public java.lang.String getRelationOrderNo() {
		return this.relationOrderNo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setContractIds(java.lang.String value) {
		this.contractIds = value;
	}	
	public java.lang.String getContractIds() {
		return this.contractIds;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Map<String, String> getJsonOject() {
		return jsonOject;
	}
	public void setJsonOject(Map<String, String> jsonOject) {
		this.jsonOject = jsonOject;
	}
	public String getPaths() {
		return paths;
	}
	public void setPaths(String paths) {
		this.paths = paths;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("agencyId",getAgencyId())
			.append("orderNo",getOrderNo())
			.append("cityCode",getCityCode())
			.append("cityName",getCityName())
			.append("productCode",getProductCode())
			.append("productName",getProductName())
			.append("customerName",getCustomerName())
			.append("borrowingAmount",getBorrowingAmount())
			.append("borrowingDay",getBorrowingDay())
			.append("relationOrderNo",getRelationOrderNo())
			.append("data",getData())
			.append("contractIds",getContractIds())
			.toString();
	}
}

