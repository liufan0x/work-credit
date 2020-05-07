/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.contract;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 12:22:20
 * @version 1.0
 */
@ApiModel(value = "字段信息")
public class FieldDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 字段信息 */
	@ApiModelProperty(value = "字段信息")
	private java.lang.Integer id;
	
	/** 字段分组Id */
	@ApiModelProperty(value = "字段分组Id")
	private java.lang.Integer groupId;
	
	/** 字段名称 */
	@ApiModelProperty(value = "字段名称")
	private java.lang.String name;
	
	/** 是否从既定文字开始（1，否。2，是） */
	@ApiModelProperty(value = "是否从既定文字开始（1，否。2，是）")
	private java.lang.Integer type;

	/** 输入框集合 */
	@ApiModelProperty(value = "输入框集合")
	private List<FieldInputDto> inputs;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setGroupId(java.lang.Integer value) {
		this.groupId = value;
	}	
	public java.lang.Integer getGroupId() {
		return this.groupId;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}	
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}	
	public java.lang.Integer getType() {
		return this.type;
	}
	public List<FieldInputDto> getInputs() {
		return inputs;
	}
	public void setInputs(List<FieldInputDto> inputs) {
		this.inputs = inputs;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("groupId",getGroupId())
			.append("name",getName())
			.append("type",getType())
			.toString();
	}
}

