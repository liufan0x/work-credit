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
 * @Date 2018-09-20 12:22:20
 * @version 1.0
 */
@ApiModel(value = "合同分组")
public class ContractGroupDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 合同分组 */
	@ApiModelProperty(value = "合同分组")
	private java.lang.Integer id;
	
	/** 分组名称 */
	@ApiModelProperty(value = "分组名称")
	private java.lang.String name;
	
	/** 合同集合 */
	@ApiModelProperty(value = "合同集合")
	private List<ContractDto> contractList;

	/** 禁用启用（0，禁用 1,启用） */
	@ApiModelProperty(value = "禁用启用（0，禁用 1,启用）")
	private java.lang.Integer isEnable;
	
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
	public List<ContractDto> getContractList() {
		return contractList;
	}
	public void setContractList(List<ContractDto> contractList) {
		this.contractList = contractList;
	}
	public java.lang.Integer getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(java.lang.Integer isEnable) {
		this.isEnable = isEnable;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.toString();
	}
}

