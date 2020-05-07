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
 * @Date 2018-05-09 17:56:47
 * @version 1.0
 */
@ApiModel(value = "机构类型表")
public class AgencyTypeDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 机构类型表 */
	@ApiModelProperty(value = "机构类型表")
	private java.lang.Integer id;
	
	/** 机构类型名称 */
	@ApiModelProperty(value = "机构类型名称")
	private java.lang.String name;
	
	/** 机构类型描述 */
	@ApiModelProperty(value = "机构类型描述")
	private java.lang.String describe;
	
	/** 是否启用（0，启用 1，未启用） */
	@ApiModelProperty(value = "是否启用（0，启用 1，未启用）")
	private Integer isEnable;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}	
	public java.lang.String getName() {
		return this.name;
	}
	public void setDescribe(java.lang.String value) {
		this.describe = value;
	}	
	public java.lang.String getDescribe() {
		return this.describe;
	}
	public void setIsEnable(Integer value) {
		this.isEnable = value;
	}	
	public Integer getIsEnable() {
		return this.isEnable;
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
			.append("name",getName())
			.append("describe",getDescribe())
			.append("isEnable",getIsEnable())
			.append("remark",getRemark())
			.toString();
	}
}

