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
 * @Date 2018-09-20 20:08:34
 * @version 1.0
 */
@ApiModel(value = "合同列表")
public class ContractListRecordDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;

	/** 操作人 */
	@ApiModelProperty(value = "操作人")
	private String createName;
	
	/** 合同列表 */
	@ApiModelProperty(value = "合同列表")
	private java.lang.Integer id;
	
	/** 合同列表Id */
	@ApiModelProperty(value = "合同列表Id")
	private java.lang.Integer contractListId;
	
	/** 操作类型（2：预览，3：下载，4：打印） */
	@ApiModelProperty(value = "操作类型（2：预览，3：下载，4：打印）")
	private java.lang.Integer type;
	
	/** 合同名称 */
	@ApiModelProperty(value = "合同名称")
	private java.lang.String contractNames;
	
	/** 合同文件地址逗号隔开 */
	@ApiModelProperty(value = "合同文件地址逗号隔开")
	private java.lang.String filePath;

	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setContractListId(java.lang.Integer value) {
		this.contractListId = value;
	}	
	public java.lang.Integer getContractListId() {
		return this.contractListId;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setContractNames(java.lang.String value) {
		this.contractNames = value;
	}	
	public java.lang.String getContractNames() {
		return this.contractNames;
	}
	public void setFilePath(java.lang.String value) {
		this.filePath = value;
	}	
	public java.lang.String getFilePath() {
		return this.filePath;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("contractListId",getContractListId())
			.append("type",getType())
			.append("contractNames",getContractNames())
			.append("filePath",getFilePath())
			.toString();
	}
}

