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
 * @Date 2018-06-14 20:21:45
 * @version 1.0
 */
@ApiModel(value = "风控工具-风险名单查询")
public class RisklistDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 风控工具-风险名单查询 */
	@ApiModelProperty(value = "风控工具-风险名单查询")
	private java.lang.Integer id;
	
	/** 订单号 */
	@ApiModelProperty(value = "订单号")
	private java.lang.String orderNo;
	
	/** 姓名 */
	@ApiModelProperty(value = "姓名")
	private java.lang.String name;
	
	/** 身份证号 */
	@ApiModelProperty(value = "身份证号")
	private java.lang.String identity;
	
	/** 手机号 */
	@ApiModelProperty(value = "手机号")
	private java.lang.String phone;
	
	/** 风险名单等级 */
	@ApiModelProperty(value = "风险名单等级")
	private java.lang.String blackLevel;
	
	/** 风险名单原因 */
	@ApiModelProperty(value = "风险名单原因")
	private java.lang.String blackReason;
	
	/** 风险名单详情 */
	@ApiModelProperty(value = "风险名单详情")
	private java.lang.String blackDetails;
	

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
	public void setName(java.lang.String value) {
		this.name = value;
	}	
	public java.lang.String getName() {
		return this.name;
	}
	public void setIdentity(java.lang.String value) {
		this.identity = value;
	}	
	public java.lang.String getIdentity() {
		return this.identity;
	}
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setBlackLevel(java.lang.String value) {
		this.blackLevel = value;
	}	
	public java.lang.String getBlackLevel() {
		return this.blackLevel;
	}
	public void setBlackReason(java.lang.String value) {
		this.blackReason = value;
	}	
	public java.lang.String getBlackReason() {
		return this.blackReason;
	}
	public void setBlackDetails(java.lang.String value) {
		this.blackDetails = value;
	}	
	public java.lang.String getBlackDetails() {
		return this.blackDetails;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("name",getName())
			.append("identity",getIdentity())
			.append("phone",getPhone())
			.append("blackLevel",getBlackLevel())
			.append("blackReason",getBlackReason())
			.append("blackDetails",getBlackDetails())
			.toString();
	}
}

