package com.anjbo.bean.risk;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 终审
 * @author Administrator
 *
 */
public class FinalAuditDto extends BaseDto{

	
    private Integer id;

    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_risk_audit_final.orderNo
     *
     * @mbggenerated 2017-06-08
     */
    private String orderNo;

    /**
     *审批时间
     *
     * This field corresponds to the database column tbl_risk_audit_final.auditTime
     *
     * @mbggenerated 2017-06-08
     */
    private Date auditTime;

    /**
     *0:不展示1:展示
     *
     * This field corresponds to the database column tbl_risk_audit_final.isShow
     *
     * @mbggenerated 2017-06-08
     */
    private Integer isShow;

    /**
     *备注（如不通过原因或者其他）
     *
     * This field corresponds to the database column tbl_risk_audit_final.remark
     *
     * @mbggenerated 2017-06-08
     */
    private String remark;
    /**
     * 审批状态  1:通过,2不通过(退回),3上报首席风险官
     */
    private int auditStatus;
    /**
     * 审批人uid
     */
    private String handleUid;
    /**
     * 处理人名称
     */
    private String handleName;
    /**
     * 下一节点处理人
     */
    private String nextHandleUid;
    private String paymentType;  //还款方式1:凭抵押回执放款 2凭抵押状态放款 3凭他项权利证放款
    private String officerUid; //首席风险官uid
    private int officerUidType;//1:首席风险官 2：法务uid 
    private String allocationFundUid; //推送金融机构uid
    
    public String getAllocationFundUid() {
		return allocationFundUid;
	}

	public void setAllocationFundUid(String allocationFundUid) {
		this.allocationFundUid = allocationFundUid;
	}

	public int getOfficerUidType() {
		return officerUidType;
	}

	public void setOfficerUidType(int officerUidType) {
		this.officerUidType = officerUidType;
	}

	public String getOfficerUid() {
		return officerUid;
	}

	public void setOfficerUid(String officerUid) {
		this.officerUid = officerUid;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getHandleUid() {
		return handleUid;
	}

	public void setHandleUid(String handleUid) {
		this.handleUid = handleUid;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public String getNextHandleUid() {
		return nextHandleUid;
	}

	public void setNextHandleUid(String nextHandleUid) {
		this.nextHandleUid = nextHandleUid;
	}
}
