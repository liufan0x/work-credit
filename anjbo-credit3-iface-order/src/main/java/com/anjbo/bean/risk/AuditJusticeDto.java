/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.risk;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@ApiModel(value = "法务审批")
public class AuditJusticeDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 法务审批 */
	@ApiModelProperty(value = "法务审批")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 审批时间 */
	@ApiModelProperty(value = "审批时间")
	private java.util.Date auditTime;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 0:不展示1:展示 */
	@ApiModelProperty(value = "0:不展示1:展示")
	private java.lang.Integer isShow;
	
	/** 审批状态(1:通过,2不通过(退回)) */
	@ApiModelProperty(value = "审批状态(1:通过,2不通过(退回))")
	private java.lang.Integer auditStatus;
	
	/** 审批人 */
	@ApiModelProperty(value = "审批人")
	private java.lang.String handleUid;
	
	/** 审批人名称 */
	@ApiModelProperty(value = "审批人名称")
	private String handleName;
	
	public String getHandleName() {
		return handleName;
	}
	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}
	

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
	public void setAuditTime(java.util.Date value) {
		this.auditTime = value;
	}	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setIsShow(java.lang.Integer value) {
		this.isShow = value;
	}	
	public java.lang.Integer getIsShow() {
		return this.isShow;
	}
	public void setAuditStatus(java.lang.Integer value) {
		this.auditStatus = value;
	}	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setHandleUid(java.lang.String value) {
		this.handleUid = value;
	}	
	public java.lang.String getHandleUid() {
		return this.handleUid;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("auditTime",getAuditTime())
			.append("remark",getRemark())
			.append("isShow",getIsShow())
			.append("auditStatus",getAuditStatus())
			.append("handleUid",getHandleUid())
			.toString();
	}
}

