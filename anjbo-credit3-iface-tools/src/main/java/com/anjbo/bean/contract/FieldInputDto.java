/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.contract;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 12:22:21
 * @version 1.0
 */
@ApiModel(value = "字段输入框")
public class FieldInputDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 字段输入框 */
	@ApiModelProperty(value = "字段输入框")
	private java.lang.Integer id;
	
	/** 字段Id */
	@ApiModelProperty(value = "字段Id")
	private java.lang.Integer fieldId;
	
	/** 代号或文本 */
	@ApiModelProperty(value = "代号或文本")
	private java.lang.String value;
	
	/** 代号值 */
	@ApiModelProperty(value = "代号值")
	private String modelValue;
	
	/** 字段来源 */
	@ApiModelProperty(value = "字段来源")
	private String source;
	
	/** 字段来源 */
	@ApiModelProperty(value = "字段来源")
	private String pSource;
	
	/** 字段分组Id */
	@ApiModelProperty(value = "字段分组Id（主要用于删除分组）")
	private java.lang.Integer groupId;
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setFieldId(java.lang.Integer value) {
		this.fieldId = value;
	}	
	public java.lang.Integer getFieldId() {
		return this.fieldId;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}	
	public java.lang.String getValue() {
		return this.value;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getModelValue() {
		return modelValue;
	}
	public void setModelValue(String modelValue) {
		this.modelValue = modelValue;
	}
	public String getpSource() {
		return pSource;
	}
	public void setpSource(String pSource) {
		this.pSource = pSource;
	}
	public java.lang.Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(java.lang.Integer groupId) {
		this.groupId = groupId;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("fieldId",getFieldId())
			.append("value",getValue())
			.toString();
	}
}

