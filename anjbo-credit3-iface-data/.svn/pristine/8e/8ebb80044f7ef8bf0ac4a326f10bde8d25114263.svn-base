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
 * @Date 2018-05-02 18:13:43
 * @version 1.0
 */
@ApiModel(value = "银行")
public class BankDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 银行 */
	@ApiModelProperty(value = "银行")
	private java.lang.Integer id;

	/** 银行名称 */
	@ApiModelProperty(value = "银行名称")
	private java.lang.String name;

	/** 排序 */
	@ApiModelProperty(value = "排序")
	private java.lang.Integer sort;

	/** 排序 */
	@ApiModelProperty(value = "排序")
	private List<BankSubDto> subBankDtos;
	

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
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}	
	public java.lang.Integer getSort() {
		return this.sort;
	}	
	public List<BankSubDto> getSubBankDtos() {
		return subBankDtos;
	}
	public void setSubBankDtos(List<BankSubDto> subBankDtos) {
		this.subBankDtos = subBankDtos;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("sort",getSort())
			.toString();
	}
}

