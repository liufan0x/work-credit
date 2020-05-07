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
 * @Date 2018-05-09 12:25:15
 * @version 1.0
 */
@ApiModel(value = "角色权限关系表")
public class RoleAuthorityDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 角色权限关系表 */
	@ApiModelProperty(value = "角色权限关系表")
	private java.lang.Integer id;
	
	/** 角色Id（tbl_fc_system_role） */
	@ApiModelProperty(value = "角色Id（tbl_fc_system_role）")
	private java.lang.Integer roleId;
	
	/** 权限Id（tbl_fc_system_authority） */
	@ApiModelProperty(value = "权限Id（tbl_fc_system_authority）")
	private java.lang.String authorityId;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setRoleId(java.lang.Integer value) {
		this.roleId = value;
	}	
	public java.lang.Integer getRoleId() {
		return this.roleId;
	}
	public void setAuthorityId(java.lang.String value) {
		this.authorityId = value;
	}	
	public java.lang.String getAuthorityId() {
		return this.authorityId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("roleId",getRoleId())
			.append("authorityId",getAuthorityId())
			.toString();
	}
}

