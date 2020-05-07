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
 * @Date 2018-09-20 12:22:20
 * @version 1.0
 */
@ApiModel(value = "合同信息")
public class ContractDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 合同信息 */
	@ApiModelProperty(value = "合同信息")
	private java.lang.Integer id;
	
	/** 分组Id */
	@ApiModelProperty(value = "分组Id")
	private java.lang.Integer groupId;
	
	/** 合同名称 */
	@ApiModelProperty(value = "合同名称")
	private java.lang.String name;
	
	/** 普通模板 */
	@ApiModelProperty(value = "普通模板")
	private java.lang.String path;
	
	/** 无文本模板 */
	@ApiModelProperty(value = "无文本模板")
	private java.lang.String noTextPath;
	
	/** 字段Ids */
	@ApiModelProperty(value = "字段Ids")
	private java.lang.String fieldIds;
	
	/** 排序 */
	@ApiModelProperty(value = "排序")
	private java.lang.Integer sort;
	
	/** 禁用启用（0，禁用 1,启用） */
	@ApiModelProperty(value = "禁用启用（0，禁用 1,启用）")
	private java.lang.Integer isEnable;
	

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
	public void setPath(java.lang.String value) {
		this.path = value;
	}	
	public java.lang.String getPath() {
		return this.path;
	}
	public void setNoTextPath(java.lang.String value) {
		this.noTextPath = value;
	}	
	public java.lang.String getNoTextPath() {
		return this.noTextPath;
	}
	public void setFieldIds(java.lang.String value) {
		this.fieldIds = value;
	}	
	public java.lang.String getFieldIds() {
		return this.fieldIds;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setIsEnable(java.lang.Integer value) {
		this.isEnable = value;
	}	
	public java.lang.Integer getIsEnable() {
		return this.isEnable;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("groupId",getGroupId())
			.append("name",getName())
			.append("path",getPath())
			.append("noTextPath",getNoTextPath())
			.append("fieldIds",getFieldIds())
			.append("sort",getSort())
			.append("isEnable",getIsEnable())
			.toString();
	}
}

