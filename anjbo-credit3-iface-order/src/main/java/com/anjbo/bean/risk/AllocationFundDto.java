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
@ApiModel(value = "资金方审批信息")
public class AllocationFundDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 资金方审批信息 */
	@ApiModelProperty(value = "资金方审批信息")
	private java.lang.Integer id;
	
	/** 资金方外键id */
	@ApiModelProperty(value = "资金方外键id")
	private java.lang.Integer fundId;
	
	/** 放款金额(万元) */
	@ApiModelProperty(value = "放款金额(万元)")
	private Double loanAmount;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 操作时间 */
	@ApiModelProperty(value = "操作时间")
	private java.util.Date auditTime;
	
	/** 审批人 */
	@ApiModelProperty(value = "审批人")
	private java.lang.String handleUid;
	
	/** 是否有105华安推送数据(1:无,2:有并成功,3有并失败) */
	@ApiModelProperty(value = "是否有105华安推送数据(1:无,2:有并成功,3有并失败)")
	private java.lang.Boolean isHuaanPush;
	
	/** 放款指令员 */
	@ApiModelProperty(value = "放款指令员")
	private java.lang.String loanDirectiveUid;
	
	/** 财务处理人 */
	@ApiModelProperty(value = "财务处理人")
	private java.lang.String financeUid;
	
	/** 是否有华融推送数据(1:无,2:有并成功,3有并失败) */
	@ApiModelProperty(value = "是否有华融推送数据(1:无,2:有并成功,3有并失败)")
	private java.lang.Boolean isHuarongPush;
	

	/** 资方编号*/
	@ApiModelProperty(value = "资方编号")
	private java.lang.String fundCode;
	/** 资方描述 */
	@ApiModelProperty(value = "资方描述")
	private java.lang.String fundDesc;
	

	public java.lang.String getFundCode() {
		return fundCode;
	}
	public void setFundCode(java.lang.String fundCode) {
		this.fundCode = fundCode;
	}
	public java.lang.String getFundDesc() {
		return fundDesc;
	}
	public void setFundDesc(java.lang.String fundDesc) {
		this.fundDesc = fundDesc;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setFundId(java.lang.Integer value) {
		this.fundId = value;
	}	
	public java.lang.Integer getFundId() {
		return this.fundId;
	}
	public void setLoanAmount(Double value) {
		this.loanAmount = value;
	}	
	public Double getLoanAmount() {
		return this.loanAmount;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
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
	public void setHandleUid(java.lang.String value) {
		this.handleUid = value;
	}	
	public java.lang.String getHandleUid() {
		return this.handleUid;
	}
	public void setIsHuaanPush(java.lang.Boolean value) {
		this.isHuaanPush = value;
	}	
	public java.lang.Boolean getIsHuaanPush() {
		return this.isHuaanPush;
	}
	public void setLoanDirectiveUid(java.lang.String value) {
		this.loanDirectiveUid = value;
	}	
	public java.lang.String getLoanDirectiveUid() {
		return this.loanDirectiveUid;
	}
	public void setFinanceUid(java.lang.String value) {
		this.financeUid = value;
	}	
	public java.lang.String getFinanceUid() {
		return this.financeUid;
	}
	public void setIsHuarongPush(java.lang.Boolean value) {
		this.isHuarongPush = value;
	}	
	public java.lang.Boolean getIsHuarongPush() {
		return this.isHuarongPush;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("fundId",getFundId())
			.append("loanAmount",getLoanAmount())
			.append("remark",getRemark())
			.append("orderNo",getOrderNo())
			.append("auditTime",getAuditTime())
			.append("handleUid",getHandleUid())
			.append("isHuaanPush",getIsHuaanPush())
			.append("loanDirectiveUid",getLoanDirectiveUid())
			.append("financeUid",getFinanceUid())
			.append("isHuarongPush",getIsHuarongPush())
			.toString();
	}
}

