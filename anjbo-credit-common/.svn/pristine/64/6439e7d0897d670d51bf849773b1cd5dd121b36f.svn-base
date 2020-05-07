/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.anjbo.bean.BaseDto;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-01-19 14:18:56
 * @version 1.0
 */
public class OrderBaseCustomerShareholderDto extends BaseDto{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private int isFinish;
	//columns START
	/** 订单编号 */	
	private java.lang.String orderNo;
	/** 股东姓名 */	
	private java.lang.String shareholderName;
	/** 出资方式 */	
	private java.lang.String contributionMethod;
	/** 出资额 */	
	private double contributionAmount;
	/** 出资比例 */	
	private double contributionProportion;
	//columns END
	
	public OrderBaseCustomerShareholderDto() {}
	public OrderBaseCustomerShareholderDto(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}	
	public void setShareholderName(java.lang.String value) {
		this.shareholderName = value;
	}	
	public java.lang.String getShareholderName() {
		return this.shareholderName;
	}	
	public void setContributionMethod(java.lang.String value) {
		this.contributionMethod = value;
	}	
	public java.lang.String getContributionMethod() {
		return this.contributionMethod;
	}	
	public void setContributionAmount(double value) {
		this.contributionAmount = value;
	}	
	public double getContributionAmount() {
		return this.contributionAmount;
	}	
	public void setContributionProportion(double value) {
		this.contributionProportion = value;
	}	
	public double getContributionProportion() {
		return this.contributionProportion;
	}	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ORDER_NO",getOrderNo())
			.append("SHAREHOLDER_NAME",getShareholderName())
			.append("CONTRIBUTION_METHOD",getContributionMethod())
			.append("CONTRIBUTION_AMOUNT",getContributionAmount())
			.append("CONTRIBUTION_PROPORTION",getContributionProportion())
			.append("CREATE_UID",getCreateUid())
			.append("CREATE_TIME",getCreateTime())
			.append("UPDATE_UID",getUpdateUid())
			.append("UPDATE_TIME",getUpdateTime())
			.toString();
	}
	public int getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(int isFinish) {
		this.isFinish = isFinish;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}

