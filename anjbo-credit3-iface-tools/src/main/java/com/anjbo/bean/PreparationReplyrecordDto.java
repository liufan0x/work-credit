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
@ApiModel(value = "报备修改记录回复")
public class PreparationReplyrecordDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 报备修改记录回复 */
	@ApiModelProperty(value = "报备修改记录回复")
	private java.lang.Integer id;
	
	/** orderNo */
	@ApiModelProperty(value = "orderNo")
	private java.lang.String orderNo;
	
	/** 回复内容 */
	@ApiModelProperty(value = "回复内容")
	private java.lang.String replyContent;
	
	/** reportId */
	@ApiModelProperty(value = "reportId")
	private java.lang.Integer reportId;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setReplyContent(java.lang.String value) {
		this.replyContent = value;
	}	
	public java.lang.String getReplyContent() {
		return this.replyContent;
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
			.append("orderNo",getOrderNo())
			.append("replyContent",getReplyContent())
			.append("reportId",getReportId())
			.toString();
	}
}

