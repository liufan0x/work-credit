/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.element;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:30
 * @version 1.0
 */
@ApiModel(value = "要件退还")
public class DocumentsReturnDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 要件退还 */
	@ApiModelProperty(value = "要件退还")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 退回时间 */
	@ApiModelProperty(value = "退回时间")
	@JsonFormat( pattern="yyyy-MM-dd")
	private java.util.Date returnTime;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 处理人 */
	@ApiModelProperty(value = "处理人")
	private java.lang.String handleUid;
	
	/** 退还图片 */
	@ApiModelProperty(value = "退还图片")
	private java.lang.String returnImgUrl;
	
	/** 退回操作人 */
	@ApiModelProperty(value = "退回操作人")
	private java.lang.String returnHandleName;
	

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
	public void setReturnTime(java.util.Date value) {
		this.returnTime = value;
	}	
	public java.util.Date getReturnTime() {
		return this.returnTime;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setHandleUid(java.lang.String value) {
		this.handleUid = value;
	}	
	public java.lang.String getHandleUid() {
		return this.handleUid;
	}
	public void setReturnImgUrl(java.lang.String value) {
		this.returnImgUrl = value;
	}	
	public java.lang.String getReturnImgUrl() {
		return this.returnImgUrl;
	}
	public void setReturnHandleName(java.lang.String value) {
		this.returnHandleName = value;
	}	
	public java.lang.String getReturnHandleName() {
		return this.returnHandleName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("returnTime",getReturnTime())
			.append("remark",getRemark())
			.append("handleUid",getHandleUid())
			.append("returnImgUrl",getReturnImgUrl())
			.append("returnHandleName",getReturnHandleName())
			.toString();
	}
}

