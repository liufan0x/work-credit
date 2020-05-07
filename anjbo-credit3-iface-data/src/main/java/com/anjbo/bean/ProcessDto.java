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
 * @Date 2018-05-03 09:58:09
 * @version 1.0
 */
@ApiModel(value = "流程节点")
public class ProcessDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 流程节点 */
	@ApiModelProperty(value = "流程节点")
	private java.lang.Integer id;
	
	/** 产品Id */
	@ApiModelProperty(value = "产品Id")
	private java.lang.Integer productId;
	
	/** 流程Id */
	@ApiModelProperty(value = "流程Id")
	private java.lang.String processId;
	
	/** 流程名称 */
	@ApiModelProperty(value = "流程名称")
	private java.lang.String processName;
	
	/** 流程所属模块 */
	@ApiModelProperty(value = "流程所属模块")
	private java.lang.String processModular;
	
	/** 流程注释 */
	@ApiModelProperty(value = "流程注释")
	private java.lang.String processDescribe;
	
	/** pc链接 */
	@ApiModelProperty(value = "pc链接")
	private java.lang.String pcUrl;
	
	/** app链接 */
	@ApiModelProperty(value = "app链接")
	private java.lang.String appUrl;
	
	/** 排序 */
	@ApiModelProperty(value = "排序")
	private java.lang.Integer sort;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 是否在机构显示：0否1是 */
	@ApiModelProperty(value = "是否在机构显示：0否1是")
	private Integer showAgency;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setProductId(java.lang.Integer value) {
		this.productId = value;
	}	
	public java.lang.Integer getProductId() {
		return this.productId;
	}
	public void setProcessId(java.lang.String value) {
		this.processId = value;
	}	
	public java.lang.String getProcessId() {
		return this.processId;
	}
	public void setProcessName(java.lang.String value) {
		this.processName = value;
	}	
	public java.lang.String getProcessName() {
		return this.processName;
	}
	public void setProcessModular(java.lang.String value) {
		this.processModular = value;
	}	
	public java.lang.String getProcessModular() {
		return this.processModular;
	}
	public void setProcessDescribe(java.lang.String value) {
		this.processDescribe = value;
	}	
	public java.lang.String getProcessDescribe() {
		return this.processDescribe;
	}
	public void setPcUrl(java.lang.String value) {
		this.pcUrl = value;
	}	
	public java.lang.String getPcUrl() {
		return this.pcUrl;
	}
	public void setAppUrl(java.lang.String value) {
		this.appUrl = value;
	}	
	public java.lang.String getAppUrl() {
		return this.appUrl;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setShowAgency(Integer value) {
		this.showAgency = value;
	}	
	public Integer getShowAgency() {
		return this.showAgency;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("productId",getProductId())
			.append("processId",getProcessId())
			.append("processName",getProcessName())
			.append("processModular",getProcessModular())
			.append("processDescribe",getProcessDescribe())
			.append("pcUrl",getPcUrl())
			.append("appUrl",getAppUrl())
			.append("sort",getSort())
			.append("remark",getRemark())
			.append("showAgency",getShowAgency())
			.toString();
	}
}

