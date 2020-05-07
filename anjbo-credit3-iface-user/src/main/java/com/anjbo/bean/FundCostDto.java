/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean;

import java.util.List;

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
@ApiModel(value = "合作资金方业务产品")
public class FundCostDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 合作资金方业务产品 */
	@ApiModelProperty(value = "合作资金方业务产品")
	private java.lang.Integer id;
	
	/** 资金方ID，关联表 tbl_customer_fund */
	@ApiModelProperty(value = "资金方ID，关联表 tbl_customer_fund")
	private java.lang.Integer fundId;
	
	/** 业务品种，关联表 tbl_product_base */
	@ApiModelProperty(value = "业务品种，关联表 tbl_product_base")
	private java.lang.Integer productId;
	
	/** 产品name */
	@ApiModelProperty(value = "产品name")
	private String productName;
	
	/** 日费率 */
	@ApiModelProperty(value = "日费率")
	private java.lang.Double dayRate;
	
	/** 逾期费率 */
	@ApiModelProperty(value = "逾期费率")
	private java.lang.Double overdueRate;
	
	/** 是否有逾期费率(0无1有) */
	@ApiModelProperty(value = "是否有逾期费率(0无1有)")
	private Integer overdueRateHas;
	
	/** 风险拨备 */
	@ApiModelProperty(value = "风险拨备")
	private java.lang.Double riskProvision;
	
	/** 是否有风险拨备(0无1有) */
	@ApiModelProperty(value = "是否有风险拨备(0无1有)")
	private Integer riskProvisionHas;
	
	/** 是否有优惠(0无1有) */
	@ApiModelProperty(value = "是否有优惠(0无1有)")
	private Integer discountHas;

	/** 优惠 */
	@ApiModelProperty(value = "优惠")
	private String discountHasStr;
	
	/** 合作资金方业务产品优惠 */
	@ApiModelProperty(value = "合作资金方业务产品优惠")
	private List<FundCostDiscountDto> fundCostDiscountDtos;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setFundId(java.lang.Integer value) {
		this.fundId = value;
	}	
	public java.lang.Integer getFundId() {
		return this.fundId;
	}
	public void setProductId(java.lang.Integer value) {
		this.productId = value;
	}	
	public java.lang.Integer getProductId() {
		return this.productId;
	}
	public void setDayRate(java.lang.Double value) {
		this.dayRate = value;
	}	
	public java.lang.Double getDayRate() {
		return this.dayRate;
	}
	public void setOverdueRate(java.lang.Double value) {
		this.overdueRate = value;
	}	
	public java.lang.Double getOverdueRate() {
		return this.overdueRate;
	}
	public void setOverdueRateHas(Integer value) {
		this.overdueRateHas = value;
	}	
	public Integer getOverdueRateHas() {
		return this.overdueRateHas;
	}
	public void setRiskProvision(java.lang.Double value) {
		this.riskProvision = value;
	}	
	public java.lang.Double getRiskProvision() {
		return this.riskProvision;
	}
	public void setRiskProvisionHas(Integer value) {
		this.riskProvisionHas = value;
	}	
	public Integer getRiskProvisionHas() {
		return this.riskProvisionHas;
	}
	public void setDiscountHas(Integer value) {
		this.discountHas = value;
	}	
	public Integer getDiscountHas() {
		return this.discountHas;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public List<FundCostDiscountDto> getFundCostDiscountDtos() {
		return fundCostDiscountDtos;
	}
	public void setFundCostDiscountDtos(List<FundCostDiscountDto> fundCostDiscountDtos) {
		this.fundCostDiscountDtos = fundCostDiscountDtos;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getDiscountHasStr() {
		return discountHasStr;
	}
	public void setDiscountHasStr(String discountHasStr) {
		this.discountHasStr = discountHasStr;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("fundId",getFundId())
			.append("productId",getProductId())
			.append("dayRate",getDayRate())
			.append("overdueRate",getOverdueRate())
			.append("overdueRateHas",getOverdueRateHas())
			.append("riskProvision",getRiskProvision())
			.append("riskProvisionHas",getRiskProvisionHas())
			.append("discountHas",getDiscountHas())
			.append("remark",getRemark())
			.toString();
	}
}

