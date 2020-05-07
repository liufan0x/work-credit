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
 * @Date 2018-05-09 17:56:47
 * @version 1.0
 */
@ApiModel(value = "id")
public class AgencyProductDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** id */
	@ApiModelProperty(value = "id")
	private java.lang.Integer id;
	
	/** 产品code */
	@ApiModelProperty(value = "产品code")
	private java.lang.String productCode;
	
	/** 产品名称 */
	@ApiModelProperty(value = "产品名称")
	private java.lang.String productName;
	
	/** 产品所属城市 */
	@ApiModelProperty(value = "产品所属城市")
	private java.lang.String cityCode;
	
	/** 所属城市名称 */
	@ApiModelProperty(value = "所属城市名称")
	private java.lang.String cityName;
	
	/** 机构id */
	@ApiModelProperty(value = "机构id")
	private java.lang.Integer agencyId;
	
	/** 使用状态，0禁用，1启用 */
	@ApiModelProperty(value = "使用状态，0禁用，1启用")
	private java.lang.Boolean status;
	
	/** 关联tbl_customer_agency_feescale表 */
	@ApiModelProperty(value = "关联tbl_customer_agency_feescale表")
	private java.lang.Integer feescaleId;
	

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
	public void setCityName(java.lang.String value) {
		this.cityName = value;
	}	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setAgencyId(java.lang.Integer value) {
		this.agencyId = value;
	}	
	public java.lang.Integer getAgencyId() {
		return this.agencyId;
	}
	public void setStatus(java.lang.Boolean value) {
		this.status = value;
	}	
	public java.lang.Boolean getStatus() {
		return this.status;
	}
	public void setFeescaleId(java.lang.Integer value) {
		this.feescaleId = value;
	}	
	public java.lang.Integer getFeescaleId() {
		return this.feescaleId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("productCode",getProductCode())
			.append("productName",getProductName())
			.append("cityCode",getCityCode())
			.append("cityName",getCityName())
			.append("agencyId",getAgencyId())
			.append("status",getStatus())
			.append("feescaleId",getFeescaleId())
			.toString();
	}
}

