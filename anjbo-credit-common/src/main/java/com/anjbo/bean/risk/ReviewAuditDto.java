package com.anjbo.bean.risk;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 复核审批
 * @author Administrator
 *
 */
public class ReviewAuditDto extends BaseDto{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    private Double  rate  ;///费率
    private Double  overdueRate  ;///逾期费率
    private String allocationFundUid; //推送金融机构uid
    private Double loanAmont;
    private int borrowingDays;
    private int type; //1：上报首席: 2：推送金融
    
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Double getLoanAmont() {
		return loanAmont;
	}

	public void setLoanAmont(Double loanAmont) {
		this.loanAmont = loanAmont;
	}

	public int getBorrowingDays() {
		return borrowingDays;
	}

	public void setBorrowingDays(int borrowingDays) {
		this.borrowingDays = borrowingDays;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(Double overdueRate) {
		this.overdueRate = overdueRate;
	}

	public String getAllocationFundUid() {
		return allocationFundUid;
	}

	public void setAllocationFundUid(String allocationFundUid) {
		this.allocationFundUid = allocationFundUid;
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
