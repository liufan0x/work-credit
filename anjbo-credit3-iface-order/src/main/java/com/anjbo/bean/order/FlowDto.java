/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-10 16:09:44
 * @version 1.0
 */
@ApiModel(value = "订单流水表")
public class FlowDto extends BaseDto{
	
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
	/** 当前流程Name */
	@ApiModelProperty(value = "当前流程Name")
	private java.lang.String currentProcessName;
	
	/** 下一个流程Id */
	@ApiModelProperty(value = "下一个流程Id")
	private java.lang.String nextProcessId;

	/** 下一个流程Name */
	@ApiModelProperty(value = "下一个流程Name")
	private java.lang.String nextProcessName;
	
	/** 处理人 */
	@ApiModelProperty(value = "处理人")
	private java.lang.String handleUid;

	/** 处理人Name */
	@ApiModelProperty(value = "处理人name")
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
	
	/** 操作用时（分钟） */
	@ApiModelProperty(value = "操作用时（分钟）")
	private java.lang.Integer timeNum;
	
	/** 1:是，2:否 */
	@ApiModelProperty(value = "1:是，2:否")
	private java.lang.Integer isNewWalkProcess;
	
	/** 机构不展示节点 */
	public static final String[] arrayNodeWithout = {
		/*指派还款专员*/"repaymentMember"
	};
	/** 机构不展示详情节点 */
	public static final String[] arrayNodeDetailWithout = {
		/*分配资金*/"allocationFund",
		/*资料推送*/"fundDocking",
		/*核实利息（原收利息）   */"lendingHarvest",		
		/*收利息   */"isLendingHarvest",		
		/*核实后置费用(原扣回后置费用)*/"backExpenses",
		/*扣回后置费用*/"isBackExpenses",
		/*收罚息          */"pay",
		/*返佣             */"rebate"
	};
	/** 机构必要详情节点 */
	public static final String[] arrayNodeDetailNeed = {
		/*分配订单*/"managerAudit",
		/*资料审核*/"dataAudit",
		/*法务审批*/"auditJustice",
		/*要件校验*/"element",
		/*放款       */"lending",
		/*回款       */"receivableFor",
		/*回款（首期）*/"receivableForFirst",
		/*回款（尾期）*/"receivableForEnd",
		/*要件退还       */"elementReturn"
	};
	/**风控审批节点*/
	public static final String ORDER_NODE_RISK = "风控审批";
	
	public static final String[] arrayRiskName = {
			"风控初审",
			"风控终审",
			"首席风险官审批"
		};
	
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
	public java.lang.String getNextProcessName() {
		return nextProcessName;
	}
	public void setNextProcessName(java.lang.String nextProcessName) {
		this.nextProcessName = nextProcessName;
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
	public void setTimeNum(java.lang.Integer value) {
		this.timeNum = value;
	}	
	public java.lang.String getCurrentProcessName() {
		return currentProcessName;
	}
	public void setCurrentProcessName(java.lang.String currentProcessName) {
		this.currentProcessName = currentProcessName;
	}
	public java.lang.Integer getTimeNum() {
		return this.timeNum;
	}
	public void setIsNewWalkProcess(java.lang.Integer value) {
		this.isNewWalkProcess = value;
	}	
	public java.lang.Integer getIsNewWalkProcess() {
		return this.isNewWalkProcess;
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
			.append("currentProcessId",getCurrentProcessId())
			.append("nextProcessId",getNextProcessId())
			.append("handleUid",getHandleUid())
			.append("handleTime",getHandleTime())
			.append("backReason",getBackReason())
			.append("returnType",getReturnType())
			.append("timeNum",getTimeNum())
			.append("isNewWalkProcess",getIsNewWalkProcess())
			.toString();
	}
}

