/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-03 09:58:09
 * @version 1.0
 */
@ApiModel(value = "产品")
public class ProductDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 产品 */
	@ApiModelProperty(value = "产品")
	private java.lang.Integer id;
	
	/** 产品代号 */
	@ApiModelProperty(value = "产品代号")
	private java.lang.String productCode;
	
	/** 产品名称 */
	@ApiModelProperty(value = "产品名称")
	private java.lang.String productName;
	
	/** 城市Code */
	@ApiModelProperty(value = "城市Code")
	private java.lang.String cityCode;
	
	/** 城市Name */
	@ApiModelProperty(value = "城市Name")
	private java.lang.String cityName;
	
	/** 产品描述 */
	@ApiModelProperty(value = "产品描述")
	private java.lang.String productDescribe;
	
	/** 产品类型（1：基础产品，2：关联产品） */
	@ApiModelProperty(value = "产品类型（1：基础产品，2：关联产品）")
	private java.lang.Boolean type;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 产品流程 **/
	private List<ProcessDto> processDtos;
	

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
	public void setProductName(java.lang.String value) {
		this.productName = value;
	}	
	public java.lang.String getProductName() {
		return this.productName;
	}
	public void setCityCode(java.lang.String value) {
		this.cityCode = value;
	}	
	public java.lang.String getCityCode() {
		return this.cityCode;
	}
	public void setProductDescribe(java.lang.String value) {
		this.productDescribe = value;
	}	
	public java.lang.String getProductDescribe() {
		return this.productDescribe;
	}
	public void setType(java.lang.Boolean value) {
		this.type = value;
	}	
	public java.lang.Boolean getType() {
		return this.type;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public java.lang.String getCityName() {
		return cityName;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	public List<ProcessDto> getProcessDtos() {
		return processDtos;
	}
	public void setProcessDtos(List<ProcessDto> processDtos) {
		this.processDtos = processDtos;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("productCode",getProductCode())
			.append("productName",getProductName())
			.append("cityCode",getCityCode())
			.append("productDescribe",getProductDescribe())
			.append("type",getType())
			.append("remark",getRemark())
			.toString();
	}
	
}

