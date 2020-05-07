/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:45
 * @version 1.0
 */
@ApiModel(value = "担保人信息")
public class BaseCustomerGuaranteeDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 担保人信息 */
	@ApiModelProperty(value = "担保人信息")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 姓名 */
	@ApiModelProperty(value = "姓名")
	private java.lang.String guaranteeName;
	
	/** 手机号码 */
	@ApiModelProperty(value = "手机号码")
	private java.lang.String guaranteePhone;
	
	/** 与借款人关系 */
	@ApiModelProperty(value = "与借款人关系")
	private java.lang.String guaranteeRelationship;
	
	/** 证件类型 */
	@ApiModelProperty(value = "证件类型")
	private java.lang.String guaranteeCardType;
	
	/** 证件号码 */
	@ApiModelProperty(value = "证件号码")
	private java.lang.String guaranteeCardNumber;
	
	/** 担保方式 */
	@ApiModelProperty(value = "担保方式")
	private java.lang.String guaranteeType;
	
	/** 产权证类型 */
	@ApiModelProperty(value = "产权证类型")
	private java.lang.String guaranteePropertyType;
	
	/** 建筑面积 */
	@ApiModelProperty(value = "建筑面积")
	private Long guaranteeApropertyrchitectureSize;
	
	/** 是否产权人(是,否) */
	@ApiModelProperty(value = "是否产权人(是,否)")
	private java.lang.String guaranteeIsPropertyProple;
	
	/** 是否完成1:是,2:否 */
	@ApiModelProperty(value = "是否完成1:是,2:否")
	private java.lang.Integer isFinish;
	

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
	public void setGuaranteeName(java.lang.String value) {
		this.guaranteeName = value;
	}	
	public java.lang.String getGuaranteeName() {
		return this.guaranteeName;
	}
	public void setGuaranteePhone(java.lang.String value) {
		this.guaranteePhone = value;
	}	
	public java.lang.String getGuaranteePhone() {
		return this.guaranteePhone;
	}
	public void setGuaranteeRelationship(java.lang.String value) {
		this.guaranteeRelationship = value;
	}	
	public java.lang.String getGuaranteeRelationship() {
		return this.guaranteeRelationship;
	}
	public void setGuaranteeCardType(java.lang.String value) {
		this.guaranteeCardType = value;
	}	
	public java.lang.String getGuaranteeCardType() {
		return this.guaranteeCardType;
	}
	public void setGuaranteeCardNumber(java.lang.String value) {
		this.guaranteeCardNumber = value;
	}	
	public java.lang.String getGuaranteeCardNumber() {
		return this.guaranteeCardNumber;
	}
	public void setGuaranteeType(java.lang.String value) {
		this.guaranteeType = value;
	}	
	public java.lang.String getGuaranteeType() {
		return this.guaranteeType;
	}
	public void setGuaranteePropertyType(java.lang.String value) {
		this.guaranteePropertyType = value;
	}	
	public java.lang.String getGuaranteePropertyType() {
		return this.guaranteePropertyType;
	}
	public void setGuaranteeApropertyrchitectureSize(Long value) {
		this.guaranteeApropertyrchitectureSize = value;
	}	
	public Long getGuaranteeApropertyrchitectureSize() {
		return this.guaranteeApropertyrchitectureSize;
	}
	public void setGuaranteeIsPropertyProple(java.lang.String value) {
		this.guaranteeIsPropertyProple = value;
	}	
	public java.lang.String getGuaranteeIsPropertyProple() {
		return this.guaranteeIsPropertyProple;
	}
	public void setIsFinish(java.lang.Integer value) {
		this.isFinish = value;
	}	
	public java.lang.Integer getIsFinish() {
		return this.isFinish;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("guaranteeName",getGuaranteeName())
			.append("guaranteePhone",getGuaranteePhone())
			.append("guaranteeRelationship",getGuaranteeRelationship())
			.append("guaranteeCardType",getGuaranteeCardType())
			.append("guaranteeCardNumber",getGuaranteeCardNumber())
			.append("guaranteeType",getGuaranteeType())
			.append("guaranteePropertyType",getGuaranteePropertyType())
			.append("guaranteeApropertyrchitectureSize",getGuaranteeApropertyrchitectureSize())
			.append("guaranteeIsPropertyProple",getGuaranteeIsPropertyProple())
			.append("isFinish",getIsFinish())
			.toString();
	}
}

