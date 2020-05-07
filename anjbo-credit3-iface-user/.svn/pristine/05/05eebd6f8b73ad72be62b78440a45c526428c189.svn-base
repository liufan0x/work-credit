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
 * @Date 2018-05-09 17:56:45
 * @version 1.0
 */
@ApiModel(value = "机构受理员关联表")
public class AgencyAcceptDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 机构受理员关联表 */
	@ApiModelProperty(value = "机构受理员关联表")
	private java.lang.Integer id;
	
	/** 机构id */
	@ApiModelProperty(value = "机构id")
	private java.lang.Integer agencyId;
	
	/** 受理员uid */
	@ApiModelProperty(value = "受理员uid")
	private java.lang.String acceptUid;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setAgencyId(java.lang.Integer value) {
		this.agencyId = value;
	}	
	public java.lang.Integer getAgencyId() {
		return this.agencyId;
	}
	public void setAcceptUid(java.lang.String value) {
		this.acceptUid = value;
	}	
	public java.lang.String getAcceptUid() {
		return this.acceptUid;
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
			.append("agencyId",getAgencyId())
			.append("acceptUid",getAcceptUid())
			.append("remark",getRemark())
			.toString();
	}
}

