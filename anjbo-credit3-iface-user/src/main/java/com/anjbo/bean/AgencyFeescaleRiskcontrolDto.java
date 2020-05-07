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
@ApiModel(value = "id")
public class AgencyFeescaleRiskcontrolDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** id */
	@ApiModelProperty(value = "id")
	private java.lang.Integer id;
	
	/** 风控等级 */
	@ApiModelProperty(value = "风控等级")
	private java.lang.Integer grade;

	/** 风控等级Name */
	@ApiModelProperty(value = "风控等级Name")
	private String gradeName;
	
	/** 收费标准id */
	@ApiModelProperty(value = "收费标准id")
	private java.lang.Integer feescaleid;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;

	/** 收费设置集合 */
	@ApiModelProperty(value = "收费设置集合")
	private List<AgencyFeescaleSectionDto> agencyFeescaleSectionDtos;
	
	public List<AgencyFeescaleSectionDto> getAgencyFeescaleSectionDtos() {
		return agencyFeescaleSectionDtos;
	}
	public void setAgencyFeescaleSectionDtos(List<AgencyFeescaleSectionDto> agencyFeescaleSectionDtos) {
		this.agencyFeescaleSectionDtos = agencyFeescaleSectionDtos;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setGrade(java.lang.Integer value) {
		this.grade = value;
	}	
	public java.lang.Integer getGrade() {
		return this.grade;
	}
	public void setFeescaleid(java.lang.Integer value) {
		this.feescaleid = value;
	}	
	public java.lang.Integer getFeescaleid() {
		return this.feescaleid;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("grade",getGrade())
			.append("feescaleid",getFeescaleid())
			.append("remark",getRemark())
			.toString();
	}
}

