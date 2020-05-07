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
@ApiModel(value = "用户权限表")
public class UserAuthorityDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 用户权限表 */
	@ApiModelProperty(value = "用户权限表")
	private java.lang.Integer id;
	
	/** 用户Uid */
	@ApiModelProperty(value = "用户Uid")
	private java.lang.String uid;
	
	/** 权限Id */
	@ApiModelProperty(value = "权限Id")
	private java.lang.String authorityId;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setUid(java.lang.String value) {
		this.uid = value;
	}	
	public java.lang.String getUid() {
		return this.uid;
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
			.append("uid",getUid())
			.append("authorityId",getAuthorityId())
			.toString();
	}
}

