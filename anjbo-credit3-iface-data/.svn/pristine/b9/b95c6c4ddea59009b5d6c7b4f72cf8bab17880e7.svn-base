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
 * @Date 2018-05-15 09:25:28
 * @version 1.0
 */
@ApiModel(value = "资源表")
public class ResourceDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 资源表 */
	@ApiModelProperty(value = "资源表")
	private java.lang.Integer id;
	
	/** 资源名称 */
	@ApiModelProperty(value = "资源名称")
	private java.lang.String resourceName;
	
	/** 资源内容 */
	@ApiModelProperty(value = "资源内容")
	private java.lang.String resourceContent;
	
	/** 资源类型(1，菜单 2，不是菜单） */
	@ApiModelProperty(value = "资源类型(1，菜单 2，不是菜单）")
	private Integer type;
	
	/** 父Id */
	@ApiModelProperty(value = "父Id")
	private java.lang.Integer pid;
	
	/** 排序 */
	@ApiModelProperty(value = "排序")
	private java.lang.Integer sort;
	
	/** 1 信贷系统/小鸽App 2 管理系统  3 云按揭系菜单 */
	@ApiModelProperty(value = "1 信贷系统/小鸽App 2 管理系统  3 云按揭系菜单")
	private Integer authType;
	
	/** 是否启用（0，启用 1，未启用） */
	@ApiModelProperty(value = "是否启用（0，启用 1，未启用）")
	private Integer isEnable;
	
	/** 平台性质：ALL所有/SELF平台自有特权/ORG机构 */
	@ApiModelProperty(value = "平台性质：ALL所有/SELF平台自有特权/ORG机构")
	private java.lang.String platformNature;
	
	/** 权限集合 */
	@ApiModelProperty(value = "权限集合")
	private List<AuthorityDto> authorityList;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setResourceName(java.lang.String value) {
		this.resourceName = value;
	}	
	public java.lang.String getResourceName() {
		return this.resourceName;
	}
	public void setResourceContent(java.lang.String value) {
		this.resourceContent = value;
	}	
	public java.lang.String getResourceContent() {
		return this.resourceContent;
	}
	public void setType(Integer value) {
		this.type = value;
	}	
	public Integer getType() {
		return this.type;
	}
	public void setPid(java.lang.Integer value) {
		this.pid = value;
	}	
	public java.lang.Integer getPid() {
		return this.pid;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setAuthType(Integer value) {
		this.authType = value;
	}	
	public Integer getAuthType() {
		return this.authType;
	}
	public void setIsEnable(Integer value) {
		this.isEnable = value;
	}	
	public Integer getIsEnable() {
		return this.isEnable;
	}
	public void setPlatformNature(java.lang.String value) {
		this.platformNature = value;
	}	
	public java.lang.String getPlatformNature() {
		return this.platformNature;
	}

	public List<AuthorityDto> getAuthorityList() {
		return authorityList;
	}
	public void setAuthorityList(List<AuthorityDto> authorityList) {
		this.authorityList = authorityList;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("resourceName",getResourceName())
			.append("resourceContent",getResourceContent())
			.append("type",getType())
			.append("pid",getPid())
			.append("sort",getSort())
			.append("authType",getAuthType())
			.append("isEnable",getIsEnable())
			.append("platformNature",getPlatformNature())
			.toString();
	}
}

