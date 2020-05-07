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
 * @Date 2018-06-25 16:16:14
 * @version 1.0
 */
@ApiModel(value = "报备修改记录")
public class PreparationEditrecordDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 报备修改记录 */
	@ApiModelProperty(value = "报备修改记录")
	private java.lang.Integer id;
	
	/** startVal */
	@ApiModelProperty(value = "startVal")
	private java.lang.String startVal;
	
	/** endVal */
	@ApiModelProperty(value = "endVal")
	private java.lang.String endVal;
	
	/** 字段名称 */
	@ApiModelProperty(value = "字段名称")
	private java.lang.String colName;
	
	/** 对应实体类名称 */
	@ApiModelProperty(value = "对应实体类名称")
	private java.lang.String beanColumn;
	
	/** orderNo */
	@ApiModelProperty(value = "orderNo")
	private java.lang.String orderNo;
	
	/** 出款报备id */
	@ApiModelProperty(value = "出款报备id")
	private java.lang.Integer reportId;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setStartVal(java.lang.String value) {
		this.startVal = value;
	}	
	public java.lang.String getStartVal() {
		return this.startVal;
	}
	public void setEndVal(java.lang.String value) {
		this.endVal = value;
	}	
	public java.lang.String getEndVal() {
		return this.endVal;
	}
	public void setColName(java.lang.String value) {
		this.colName = value;
	}	
	public java.lang.String getColName() {
		return this.colName;
	}
	public void setBeanColumn(java.lang.String value) {
		this.beanColumn = value;
	}	
	public java.lang.String getBeanColumn() {
		return this.beanColumn;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setReportId(java.lang.Integer value) {
		this.reportId = value;
	}	
	public java.lang.Integer getReportId() {
		return this.reportId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("startVal",getStartVal())
			.append("endVal",getEndVal())
			.append("colName",getColName())
			.append("beanColumn",getBeanColumn())
			.append("orderNo",getOrderNo())
			.append("reportId",getReportId())
			.toString();
	}
}

