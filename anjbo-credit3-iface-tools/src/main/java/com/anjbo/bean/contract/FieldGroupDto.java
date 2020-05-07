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
 * @Date 2018-09-20 12:22:21
 * @version 1.0
 */
@ApiModel(value = "字段分组")
public class FieldGroupDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 字段分组 */
	@ApiModelProperty(value = "字段分组")
	private java.lang.Integer id;
	
	/** 分组名称 */
	@ApiModelProperty(value = "分组名称")
	private java.lang.String name;

	/** 排序 */
	@ApiModelProperty(value = "排序")
	private java.lang.Integer sort;
	
	/** 字段集合 */
	@ApiModelProperty(value = "字段集合")
	private List<FieldDto> fileList;

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
	public List<FieldDto> getFileList() {
		return fileList;
	}
	public void setFileList(List<FieldDto> fileList) {
		this.fileList = fileList;
	}
	public java.lang.Integer getSort() {
		return sort;
	}
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.toString();
	}
}

