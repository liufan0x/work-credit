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
 * @Date 2018-05-09 17:56:48
 * @version 1.0
 */
@ApiModel(value = "合作资金方成本优惠表")
public class FundCostDiscountDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 合作资金方成本优惠表 */
	@ApiModelProperty(value = "合作资金方成本优惠表")
	private java.lang.Integer id;
	
	/** 产品ID，关联表 tbl_customer_fund_cost */
	@ApiModelProperty(value = "产品ID，关联表 tbl_customer_fund_cost")
	private java.lang.Integer fundCostId;
	
	/** 满足金额 */
	@ApiModelProperty(value = "满足金额")
	private Long money;
	
	/** 费率（单位：%/天） */
	@ApiModelProperty(value = "费率（单位：%/天）")
	private Long rate;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setFundCostId(java.lang.Integer value) {
		this.fundCostId = value;
	}	
	public java.lang.Integer getFundCostId() {
		return this.fundCostId;
	}
	public void setMoney(Long value) {
		this.money = value;
	}	
	public Long getMoney() {
		return this.money;
	}
	public void setRate(Long value) {
		this.rate = value;
	}	
	public Long getRate() {
		return this.rate;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("fundCostId",getFundCostId())
			.append("money",getMoney())
			.append("rate",getRate())
			.append("remark",getRemark())
			.toString();
	}
}

