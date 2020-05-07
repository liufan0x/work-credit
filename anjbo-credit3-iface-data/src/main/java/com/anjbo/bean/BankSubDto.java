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
 * @Date 2018-05-02 18:13:44
 * @version 1.0
 */
@ApiModel(value = "支行")
public class BankSubDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 支行 */
	@ApiModelProperty(value = "支行")
	private java.lang.Integer id;

	/** name */
	@ApiModelProperty(value = "name")
	private java.lang.String name;

	/** 所属银行ID(关联tbl_fc_bank表id) */
	@ApiModelProperty(value = "所属银行ID(关联tbl_fc_bank表id)")
	private java.lang.Integer pid;
	

	/** 银行名称 */
	@ApiModelProperty(value = "银行名称")
	private String bankName;

	/** 排序 */
	@ApiModelProperty(value = "排序")
	private java.lang.Integer sort;


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

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("pid",getPid())
			.append("sort",getSort())
			.toString();
	}
}

