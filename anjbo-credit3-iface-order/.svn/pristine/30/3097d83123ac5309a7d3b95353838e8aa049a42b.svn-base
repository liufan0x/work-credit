/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.process;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@ApiModel(value = "订单审批-受理经理")
public class AuditManagerDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 订单审批-受理经理 */
	@ApiModelProperty(value = "订单审批-受理经理")
	private java.lang.Integer id;
	
	private String managerAuditName;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 风控初审uid */
	@ApiModelProperty(value = "风控初审uid")
	private java.lang.String auditFirstUid;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 审批状态 1：通过  2：未通过 */
	@ApiModelProperty(value = "审批状态 1：通过  2：未通过")
	private java.lang.Integer status;
	
	/** 审批时间 */
	@ApiModelProperty(value = "审批时间")
	private java.util.Date auditTime;
	
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
	public void setAuditFirstUid(java.lang.String value) {
		this.auditFirstUid = value;
	}	
	public java.lang.String getAuditFirstUid() {
		return this.auditFirstUid;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setAuditTime(java.util.Date value) {
		this.auditTime = value;
	}	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}

	public String getManagerAuditName() {
		return managerAuditName;
	}
	public void setManagerAuditName(String managerAuditName) {
		this.managerAuditName = managerAuditName;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("auditFirstUid",getAuditFirstUid())
			.append("remark",getRemark())
			.append("status",getStatus())
			.append("auditTime",getAuditTime())
			.toString();
	}
}

