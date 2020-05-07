package com.anjbo.bean.order;

import java.util.Date;

import com.anjbo.bean.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL) 
public class OrderFlowDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;

	/** 订单流水表 **/
	private String id;
	
	/** 订单编号 **/
	private String orderNo;
	
	/** 当前流程Id **/
	private String currentProcessId;
	
	/** 当前流程名称 **/
	private String currentProcessName;
	
	/** 下一个流程Id **/
	private String nextProcessId;
	
	/** 下一个流程名称 **/
	private String nextProcessName;
	
	/** 处理人Uid **/
	private String handleUid;
	
	/** 处理人 **/
	private String handleName;
	
	/** 处理时间 **/
	private Date handleTime;
	
	/** 处理时间Str **/
	private String handleTimeStr;
	
	/** 退回原因 **/
	private String backReason;
	
	private String handleTimeStart;
	private String handleTimeEnd;
	/**退回类型*/
	private String returnType;
	/**系统提交时间*/
	private String createTimeStr;
	/** 是否重新走流程（1：是，2：否） */
	private int isNewWalkProcess;
	
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

	public String getHandleTimeStart() {
		return handleTimeStart;
	}

	public void setHandleTimeStart(String handleTimeStart) {
		this.handleTimeStart = handleTimeStart;
	}

	public String getHandleTimeEnd() {
		return handleTimeEnd;
	}

	public void setHandleTimeEnd(String handleTimeEnd) {
		this.handleTimeEnd = handleTimeEnd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCurrentProcessId() {
		return currentProcessId;
	}

	public void setCurrentProcessId(String currentProcessId) {
		this.currentProcessId = currentProcessId;
	}

	public String getNextProcessId() {
		return nextProcessId;
	}

	public void setNextProcessId(String nextProcessId) {
		this.nextProcessId = nextProcessId;
	}

	public String getHandleUid() {
		return handleUid;
	}

	public void setHandleUid(String handleUid) {
		this.handleUid = handleUid;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
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

	public String getHandleTimeStr() {
		return handleTimeStr;
	}

	public void setHandleTimeStr(String handleTimeStr) {
		this.handleTimeStr = handleTimeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public int getIsNewWalkProcess() {
		return isNewWalkProcess;
	}

	public void setIsNewWalkProcess(int isNewWalkProcess) {
		this.isNewWalkProcess = isNewWalkProcess;
	}
	
}
