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
 * @Date 2018-06-27 21:54:08
 * @version 1.0
 */
@ApiModel(value = "id")
public class AdministrationDivideDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** id */
	@ApiModelProperty(value = "id")
	private java.lang.Integer id;
	
	/** 区域划分代码 */
	@ApiModelProperty(value = "区域划分代码")
	private java.lang.String code;
	
	/** 区域名称 */
	@ApiModelProperty(value = "区域名称")
	private java.lang.String name;
	
	/** 所属上级区域代码 */
	@ApiModelProperty(value = "所属上级区域代码")
	private java.lang.String parentCode;
	
	/** 区域级别(1:省级,2:地级,3:县级) */
	@ApiModelProperty(value = "区域级别(1:省级,2:地级,3:县级)")
	private java.lang.Integer grade;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setCode(java.lang.String value) {
		this.code = value;
	}	
	public java.lang.String getCode() {
		return this.code;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}	
	public java.lang.String getName() {
		return this.name;
	}
	public void setParentCode(java.lang.String value) {
		this.parentCode = value;
	}	
	public java.lang.String getParentCode() {
		return this.parentCode;
	}
	public void setGrade(java.lang.Integer value) {
		this.grade = value;
	}	
	public java.lang.Integer getGrade() {
		return this.grade;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("code",getCode())
			.append("name",getName())
			.append("parentCode",getParentCode())
			.append("grade",getGrade())
			.toString();
	}
}

