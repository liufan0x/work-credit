/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2019 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.risk;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2019-01-11 18:08:27
 * @version 1.0
 */
@ApiModel(value = "询价表")
public class RiskEnquiryDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 询价表 */
	@ApiModelProperty(value = "询价表")
	private java.lang.Integer id;
	
	/** 订单号 */
	@ApiModelProperty(value = "订单号")
	private java.lang.String orderNo;
	
	/** 询价查询得到的id号，用于查询询价记录，外键 */
	@ApiModelProperty(value = "询价查询得到的id号，用于查询询价记录，外键")
	private java.lang.String enquiryId;
	
	/** 面积 */
	@ApiModelProperty(value = "面积")
	private java.lang.String area;
	
	/** 楼栋id */
	@ApiModelProperty(value = "楼栋id")
	private java.lang.String buildingId;
	
	/** 楼栋 */
	@ApiModelProperty(value = "楼栋")
	private java.lang.String buildingName;
	
	/** 房产名称id */
	@ApiModelProperty(value = "房产名称id")
	private java.lang.String propertyId;
	
	/** 房产名称 */
	@ApiModelProperty(value = "房产名称")
	private java.lang.String propertyName;
	
	/** 登记价格 */
	@ApiModelProperty(value = "登记价格")
	private Long registerPrice;
	
	/** 房号id */
	@ApiModelProperty(value = "房号id")
	private java.lang.String roomId;
	
	/** 房号 */
	@ApiModelProperty(value = "房号")
	private java.lang.String roomName;
	
	/** 权力人(单位/个人)(非交易类） */
	@ApiModelProperty(value = "权力人(单位/个人)(非交易类）")
	private java.lang.String obligee;
	
	/** 购房期限(0:未满两年,1满两年)(非交易类) */
	@ApiModelProperty(value = "购房期限(0:未满两年,1满两年)(非交易类)")
	private java.lang.String range;
	
	/** 是否消费贷(0:无消费贷,1:有消费贷)(非交易类) */
	@ApiModelProperty(value = "是否消费贷(0:无消费贷,1:有消费贷)(非交易类)")
	private java.lang.Integer consumerloans;
	
	/** 成交价格(非交易类) */
	@ApiModelProperty(value = "成交价格(非交易类)")
	private Long transactionprice;
	
	/** 评估总值/元 */
	@ApiModelProperty(value = "评估总值/元")
	private Long totalPrice;
	
	/** 评估净值/元 */
	@ApiModelProperty(value = "评估净值/元")
	private Long netPrice;
	
	/** 首套房最高可贷金额/万元 */
	@ApiModelProperty(value = "首套房最高可贷金额/万元")
	private Long maxLoanPrice;
	

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
	public void setEnquiryId(java.lang.String value) {
		this.enquiryId = value;
	}	
	public java.lang.String getEnquiryId() {
		return this.enquiryId;
	}
	public void setArea(java.lang.String value) {
		this.area = value;
	}	
	public java.lang.String getArea() {
		return this.area;
	}
	public void setBuildingId(java.lang.String value) {
		this.buildingId = value;
	}	
	public java.lang.String getBuildingId() {
		return this.buildingId;
	}
	public void setBuildingName(java.lang.String value) {
		this.buildingName = value;
	}	
	public java.lang.String getBuildingName() {
		return this.buildingName;
	}
	public void setPropertyId(java.lang.String value) {
		this.propertyId = value;
	}	
	public java.lang.String getPropertyId() {
		return this.propertyId;
	}
	public void setPropertyName(java.lang.String value) {
		this.propertyName = value;
	}	
	public java.lang.String getPropertyName() {
		return this.propertyName;
	}
	public void setRegisterPrice(Long value) {
		this.registerPrice = value;
	}	
	public Long getRegisterPrice() {
		return this.registerPrice;
	}
	public void setRoomId(java.lang.String value) {
		this.roomId = value;
	}	
	public java.lang.String getRoomId() {
		return this.roomId;
	}
	public void setRoomName(java.lang.String value) {
		this.roomName = value;
	}	
	public java.lang.String getRoomName() {
		return this.roomName;
	}
	public void setObligee(java.lang.String value) {
		this.obligee = value;
	}	
	public java.lang.String getObligee() {
		return this.obligee;
	}
	public void setRange(java.lang.String value) {
		this.range = value;
	}	
	public java.lang.String getRange() {
		return this.range;
	}
	public void setConsumerloans(java.lang.Integer value) {
		this.consumerloans = value;
	}	
	public java.lang.Integer getConsumerloans() {
		return this.consumerloans;
	}
	public void setTransactionprice(Long value) {
		this.transactionprice = value;
	}	
	public Long getTransactionprice() {
		return this.transactionprice;
	}
	public void setTotalPrice(Long value) {
		this.totalPrice = value;
	}	
	public Long getTotalPrice() {
		return this.totalPrice;
	}
	public void setNetPrice(Long value) {
		this.netPrice = value;
	}	
	public Long getNetPrice() {
		return this.netPrice;
	}
	public void setMaxLoanPrice(Long value) {
		this.maxLoanPrice = value;
	}	
	public Long getMaxLoanPrice() {
		return this.maxLoanPrice;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("enquiryId",getEnquiryId())
			.append("area",getArea())
			.append("buildingId",getBuildingId())
			.append("buildingName",getBuildingName())
			.append("propertyId",getPropertyId())
			.append("propertyName",getPropertyName())
			.append("registerPrice",getRegisterPrice())
			.append("roomId",getRoomId())
			.append("roomName",getRoomName())
			.append("obligee",getObligee())
			.append("range",getRange())
			.append("consumerloans",getConsumerloans())
			.append("transactionprice",getTransactionprice())
			.append("totalPrice",getTotalPrice())
			.append("netPrice",getNetPrice())
			.append("maxLoanPrice",getMaxLoanPrice())
			.toString();
	}
}

