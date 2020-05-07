package com.anjbo.bean.risk;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 分配资金方
 * @author huanglj
 *
 */
public class AllocationFundDto extends BaseDto{

    private Integer id;

    /**
     *资金方外键id
     *
     * This field corresponds to the database column tbl_risk_fund_detail.fundId
     *
     * @mbggenerated 2017-06-09
     */
    private Integer fundId;

    /**
     *放款金额(万元)
     *
     * This field corresponds to the database column tbl_risk_fund_detail.loanAmount
     *
     * @mbggenerated 2017-06-09
     */
    private Double loanAmount;

    /**
     *操作时间
     *
     * This field corresponds to the database column tbl_risk_fund_detail.auditTime
     *
     * @mbggenerated 2017-06-09
     */
    private Date auditTime;
    /**
     * 订单编号
     */
    private String orderNo;


	/**
     *备注(如驳回请求的原因) 
     *
     * This field corresponds to the database column tbl_risk_fund_detail.remark
     *
     * @mbggenerated 2017-06-09
     */
    private String remark;
    /**
     * 资金方代号
     */
    private String fundCode;
    /**
     * 审批人uid
     */
    private String handleUid;
    /**
     * 处理人名称
     */
    private String handleName;
    
    /**
     * 是否有105华安推送数据(1:无,2:有并成功,3有并失败)
     */
    private int isHuaanPush;
    /**
     * 放款指令员
     */
    private String loanDirectiveUid;
    /**
     * 下一节点处理人
     */
    private String nextHandleUid;
    /**
     * 财务uid
     */
    private String financeUid;
    /**
     * 是否有华融推送数据(1:无,2:有并成功,3有并失败)
     */
    private int isHuarongPush;
    
    private double dayRate;
    
    private double overdueRate;
    /**
     * 资金方描述
     */
    private String fundDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
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

	public int getIsHuaanPush() {
		return isHuaanPush;
	}

	public void setIsHuaanPush(int isHuaanPush) {
		this.isHuaanPush = isHuaanPush;
	}

	public String getLoanDirectiveUid() {
		return loanDirectiveUid;
	}

	public void setLoanDirectiveUid(String loanDirectiveUid) {
		this.loanDirectiveUid = loanDirectiveUid;
	}

	public String getNextHandleUid() {
		return nextHandleUid;
	}

	public void setNextHandleUid(String nextHandleUid) {
		this.nextHandleUid = nextHandleUid;
	}

	public String getFinanceUid() {
		return financeUid;
	}

	public void setFinanceUid(String financeUid) {
		this.financeUid = financeUid;
	}

	public int getIsHuarongPush() {
		return isHuarongPush;
	}

	public void setIsHuarongPush(int isHuarongPush) {
		this.isHuarongPush = isHuarongPush;
	}

	public double getDayRate() {
		return dayRate;
	}

	public void setDayRate(double dayRate) {
		this.dayRate = dayRate;
	}

	public double getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(double overdueRate) {
		this.overdueRate = overdueRate;
	}

	public String getFundDesc() {
		return fundDesc;
	}

	public void setFundDesc(String fundDesc) {
		this.fundDesc = fundDesc;
	}

}
