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
 * @Date 2018-05-09 17:56:46
 * @version 1.0
 */
@ApiModel(value = "ID")
public class AgencyFeescaleDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** ID */
	@ApiModelProperty(value = "ID")
	private java.lang.Integer id;
	
	/** 机构id(旧的为机构类型id) */
	@ApiModelProperty(value = "机构id(旧的为机构类型id)")
	private java.lang.Integer agencyTypeId;
	
	/** 产品ID */
	@ApiModelProperty(value = "产品ID")
	private java.lang.Integer productionid;
	
	/** 服务费 */
	@ApiModelProperty(value = "服务费")
	private Double servicefee;
	
	/** 手续费 */
	@ApiModelProperty(value = "手续费")
	private Double counterfee;
	
	/** 其他费用 */
	@ApiModelProperty(value = "其他费用")
	private Double otherfee;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 收费标准(旧的为风控等级)id */
	@ApiModelProperty(value = "收费标准(旧的为风控等级)id")
	private java.lang.Integer riskGradeId;
	
	/** 收费标准(旧的为风控等级)Name */
	@ApiModelProperty(value = "收费标准(旧的为风控等级)Name")
	private String riskGradeName;

	/** 风控等级集合 */
	@ApiModelProperty(value = "风控等级集合")
	private List<AgencyFeescaleRiskcontrolDto> agencyFeescaleRiskcontrolDtos;
	
	public List<AgencyFeescaleRiskcontrolDto> getAgencyFeescaleRiskcontrolDtos() {
		return agencyFeescaleRiskcontrolDtos;
	}
	public void setAgencyFeescaleRiskcontrolDtos(List<AgencyFeescaleRiskcontrolDto> agencyFeescaleRiskcontrolDtos) {
		this.agencyFeescaleRiskcontrolDtos = agencyFeescaleRiskcontrolDtos;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setAgencyTypeId(java.lang.Integer value) {
		this.agencyTypeId = value;
	}	
	public java.lang.Integer getAgencyTypeId() {
		return this.agencyTypeId;
	}
	public void setProductionid(java.lang.Integer value) {
		this.productionid = value;
	}	
	public java.lang.Integer getProductionid() {
		return this.productionid;
	}
	public Double getServicefee() {
		return servicefee;
	}
	public void setServicefee(Double servicefee) {
		this.servicefee = servicefee;
	}
	public Double getCounterfee() {
		return counterfee;
	}
	public void setCounterfee(Double counterfee) {
		this.counterfee = counterfee;
	}
	public Double getOtherfee() {
		return otherfee;
	}
	public void setOtherfee(Double otherfee) {
		this.otherfee = otherfee;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setRiskGradeId(java.lang.Integer value) {
		this.riskGradeId = value;
	}	
	public java.lang.Integer getRiskGradeId() {
		return this.riskGradeId;
	}
	public String getRiskGradeName() {
		return riskGradeName;
	}
	public void setRiskGradeName(String riskGradeName) {
		this.riskGradeName = riskGradeName;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("agencyTypeId",getAgencyTypeId())
			.append("productionid",getProductionid())
			.append("servicefee",getServicefee())
			.append("counterfee",getCounterfee())
			.append("otherfee",getOtherfee())
			.append("remark",getRemark())
			.append("riskGradeId",getRiskGradeId())
			.toString();
	}
}

