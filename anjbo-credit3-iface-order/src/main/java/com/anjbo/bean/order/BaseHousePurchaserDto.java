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
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@ApiModel(value = "买房人信息")
public class BaseHousePurchaserDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 买房人信息 */
	@ApiModelProperty(value = "买房人信息")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 买房人姓名 */
	@ApiModelProperty(value = "买房人姓名")
	private java.lang.String buyName;
	
	/** 手机号码 */
	@ApiModelProperty(value = "手机号码")
	private java.lang.String buyPhoneNumber;
	
	/** 婚姻状态 */
	@ApiModelProperty(value = "婚姻状态")
	private java.lang.String buyMarriageState;
	
	/** 证件号码 */
	@ApiModelProperty(value = "证件号码")
	private java.lang.String buyCardNumber;
	
	/** 证件类型 */
	@ApiModelProperty(value = "证件类型")
	private java.lang.String buyCardType;
	
	/** 列表顺序 */
	@ApiModelProperty(value = "列表顺序")
	private java.lang.Integer sort;
	
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
	public void setBuyName(java.lang.String value) {
		this.buyName = value;
	}	
	public java.lang.String getBuyName() {
		return this.buyName;
	}
	public void setBuyPhoneNumber(java.lang.String value) {
		this.buyPhoneNumber = value;
	}	
	public java.lang.String getBuyPhoneNumber() {
		return this.buyPhoneNumber;
	}
	public void setBuyMarriageState(java.lang.String value) {
		this.buyMarriageState = value;
	}	
	public java.lang.String getBuyMarriageState() {
		return this.buyMarriageState;
	}
	public void setBuyCardNumber(java.lang.String value) {
		this.buyCardNumber = value;
	}	
	public java.lang.String getBuyCardNumber() {
		return this.buyCardNumber;
	}
	public void setBuyCardType(java.lang.String value) {
		this.buyCardType = value;
	}	
	public java.lang.String getBuyCardType() {
		return this.buyCardType;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}	
	public java.lang.Integer getSort() {
		return this.sort;
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
			.append("buyName",getBuyName())
			.append("buyPhoneNumber",getBuyPhoneNumber())
			.append("buyMarriageState",getBuyMarriageState())
			.append("buyCardNumber",getBuyCardNumber())
			.append("buyCardType",getBuyCardType())
			.append("sort",getSort())
			.append("isFinish",getIsFinish())
			.toString();
	}
}

