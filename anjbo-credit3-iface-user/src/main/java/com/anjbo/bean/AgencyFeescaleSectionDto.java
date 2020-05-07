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
public class AgencyFeescaleSectionDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** id */
	@ApiModelProperty(value = "id")
	private java.lang.Integer id;
	
	/** 收费标准Id */
	@ApiModelProperty(value = "收费标准Id")
	private java.lang.Integer feescaleid;
	
	/** 风控配置Id */
	@ApiModelProperty(value = "风控配置Id")
	private java.lang.Integer raskcontrolid;
	
	/** 收费设置节点 */
	@ApiModelProperty(value = "收费设置节点")
	private Long section;
	
	/** 金额区间最大值 */
	@ApiModelProperty(value = "金额区间最大值")
	private Long maxSection;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;

	/** 收费详细集合 */
	@ApiModelProperty(value = "收费详细集合")
	private List<AgencyFeescaleDetailDto> agencyFeescaleDetailDtos;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setFeescaleid(java.lang.Integer value) {
		this.feescaleid = value;
	}	
	public java.lang.Integer getFeescaleid() {
		return this.feescaleid;
	}
	public void setRaskcontrolid(java.lang.Integer value) {
		this.raskcontrolid = value;
	}	
	public java.lang.Integer getRaskcontrolid() {
		return this.raskcontrolid;
	}
	public void setSection(Long value) {
		this.section = value;
	}	
	public Long getSection() {
		return this.section;
	}
	public void setMaxSection(Long value) {
		this.maxSection = value;
	}	
	public Long getMaxSection() {
		return this.maxSection;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public List<AgencyFeescaleDetailDto> getAgencyFeescaleDetailDtos() {
		return agencyFeescaleDetailDtos;
	}
	public void setAgencyFeescaleDetailDtos(List<AgencyFeescaleDetailDto> agencyFeescaleDetailDtos) {
		this.agencyFeescaleDetailDtos = agencyFeescaleDetailDtos;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("feescaleid",getFeescaleid())
			.append("raskcontrolid",getRaskcontrolid())
			.append("section",getSection())
			.append("maxSection",getMaxSection())
			.append("remark",getRemark())
			.toString();
	}
}

