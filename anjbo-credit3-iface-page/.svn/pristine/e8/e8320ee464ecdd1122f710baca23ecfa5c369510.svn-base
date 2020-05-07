/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-28 20:20:06
 * @version 1.0
 */
@ApiModel(value = "订单流水表")
public class PageFlowDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 订单流水表 */
	@ApiModelProperty(value = "订单流水表")
	private java.lang.Integer id;
	
	/** 订单号 */
	@ApiModelProperty(value = "订单号")
	private java.lang.String orderNo;
	
	/** 当前流程Id */
	@ApiModelProperty(value = "当前流程Id")
	private java.lang.String currentProcessId;

	/** 当前流程名称 */
	@ApiModelProperty(value = "当前流程名称")
	private String currentProcessName;
	
	/** 下一个流程Id */
	@ApiModelProperty(value = "下一个流程Id")
	private java.lang.String nextProcessId;

	/** 下一个流程名称 */
	@ApiModelProperty(value = "下一个流程名称")
	private String nextProcessName;
	
	/** 处理人 */
	@ApiModelProperty(value = "处理人")
	private java.lang.String handleUid;

	/** 处理人 */
	@ApiModelProperty(value = "处理人")
	private java.lang.String handleName;
	
	/** 处理时间 */
	@ApiModelProperty(value = "处理时间")
	private java.util.Date handleTime;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String backReason;
	
	/** 退回类型(1:补充资料，2：拒单 3：系统修改 4：其他) */
	@ApiModelProperty(value = "退回类型(1:补充资料，2：拒单 3：系统修改 4：其他)")
	private java.lang.String returnType;
	

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
	public void setCurrentProcessId(java.lang.String value) {
		this.currentProcessId = value;
	}	
	public java.lang.String getCurrentProcessId() {
		return this.currentProcessId;
	}
	public void setNextProcessId(java.lang.String value) {
		this.nextProcessId = value;
	}	
	public java.lang.String getNextProcessId() {
		return this.nextProcessId;
	}
	public void setHandleUid(java.lang.String value) {
		this.handleUid = value;
	}	
	public java.lang.String getHandleUid() {
		return this.handleUid;
	}
	public void setHandleTime(java.util.Date value) {
		this.handleTime = value;
	}	
	public java.util.Date getHandleTime() {
		return this.handleTime;
	}
	public void setBackReason(java.lang.String value) {
		this.backReason = value;
	}	
	public java.lang.String getBackReason() {
		return this.backReason;
	}
	public void setReturnType(java.lang.String value) {
		this.returnType = value;
	}	
	public java.lang.String getReturnType() {
		return this.returnType;
	}
	public java.lang.String getHandleName() {
		return handleName;
	}
	public void setHandleName(java.lang.String handleName) {
		this.handleName = handleName;
	}
	public String getCurrentProcessName() {
		return currentProcessName;
	}
	public void setCurrentProcessName(String currentProcessName) {
		this.currentProcessName = currentProcessName;
	}
	public String getNextProcessName() {
		return nextProcessName;
	}
	public void setNextProcessName(String nextProcessName) {
		this.nextProcessName = nextProcessName;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("currentProcessId",getCurrentProcessId())
			.append("nextProcessId",getNextProcessId())
			.append("handleUid",getHandleUid())
			.append("handleTime",getHandleTime())
			.append("backReason",getBackReason())
			.append("returnType",getReturnType())
			.toString();
	}
}

