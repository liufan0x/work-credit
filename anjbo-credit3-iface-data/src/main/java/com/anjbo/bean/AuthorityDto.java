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
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@ApiModel(value = "权限表")
public class AuthorityDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 权限表 */
	@ApiModelProperty(value = "权限表")
	private java.lang.Integer id;
	
	/** 权限名称 */
	@ApiModelProperty(value = "权限名称")
	private java.lang.String name;
	
	/** processId */
	@ApiModelProperty(value = "processId")
	private java.lang.Integer processId;
	
	/** 是否启用（0，启用 1，未启用） */
	@ApiModelProperty(value = "是否启用（0，启用 1，未启用）")
	private Integer isEnable;
	
	/** 0基本权限 1产品节点  */
	@ApiModelProperty(value = "0基本权限 1产品节点 ")
	private Integer type;
	
	/** 资源Id */
	@ApiModelProperty(value = "资源Id")
	private java.lang.Integer resourceId;
	
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
	public void setProcessId(java.lang.Integer value) {
		this.processId = value;
	}	
	public java.lang.Integer getProcessId() {
		return this.processId;
	}
	public void setIsEnable(Integer value) {
		this.isEnable = value;
	}	
	public Integer getIsEnable() {
		return this.isEnable;
	}
	public void setType(Integer value) {
		this.type = value;
	}	
	public Integer getType() {
		return this.type;
	}
	public void setResourceId(java.lang.Integer value) {
		this.resourceId = value;
	}	
	public java.lang.Integer getResourceId() {
		return this.resourceId;
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
			.append("processId",getProcessId())
			.append("isEnable",getIsEnable())
			.append("type",getType())
			.append("resourceId",getResourceId())
			.append("remark",getRemark())
			.toString();
	}
}

