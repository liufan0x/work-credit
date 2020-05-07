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
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@ApiModel(value = "风控-终审")
public class AuditFinalDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 风控-终审 */
	@ApiModelProperty(value = "风控-终审")
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
	
	/** 审批状态(1:通过,2不通过(退回),3上报首席风险官4复核审批) */
	@ApiModelProperty(value = "审批状态(1:通过,2不通过(退回),3上报首席风险官4复核审批)")
	private java.lang.Integer auditStatus;
	
	/** 审批人 */
	@ApiModelProperty(value = "审批人")
	private java.lang.String handleUid;
	/** 审批人名称 */
	@ApiModelProperty(value = "审批人名称")
	private java.lang.String handleName;
	
	/** 还款方式1:凭抵押回执放款 2凭抵押状态放款 3凭他项权利证放款 */
	@ApiModelProperty(value = "还款方式1:凭抵押回执放款 2凭抵押状态放款 3凭他项权利证放款")
	private java.lang.String paymentType;
	
	/** 首席风险官uid */
	@ApiModelProperty(value = "首席风险官uid")
	private java.lang.String officerUid;
	
	/** uid类型： 1首席风险官 2：法务uid */
	@ApiModelProperty(value = "uid类型： 1首席风险官 2：法务uid")
	private java.lang.Integer officerUidType;
	
	/** 推送金融机构uid */
	@ApiModelProperty(value = "推送金融机构uid")
	private java.lang.String allocationFundUid;
	

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
	public void setPaymentType(java.lang.String value) {
		this.paymentType = value;
	}	
	public java.lang.String getPaymentType() {
		return this.paymentType;
	}
	public void setOfficerUid(java.lang.String value) {
		this.officerUid = value;
	}	
	public java.lang.String getOfficerUid() {
		return this.officerUid;
	}
	public void setOfficerUidType(java.lang.Integer value) {
		this.officerUidType = value;
	}	
	public java.lang.Integer getOfficerUidType() {
		return this.officerUidType;
	}
	public void setAllocationFundUid(java.lang.String value) {
		this.allocationFundUid = value;
	}	
	public java.lang.String getAllocationFundUid() {
		return this.allocationFundUid;
	}

	public java.lang.String getHandleName() {
		return handleName;
	}
	public void setHandleName(java.lang.String handleName) {
		this.handleName = handleName;
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
			.append("paymentType",getPaymentType())
			.append("officerUid",getOfficerUid())
			.append("officerUidType",getOfficerUidType())
			.append("allocationFundUid",getAllocationFundUid())
			.toString();
	}
}

