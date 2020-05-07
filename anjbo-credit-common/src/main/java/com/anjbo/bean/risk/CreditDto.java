package com.anjbo.bean.risk;

import java.util.Date;

import com.anjbo.bean.BaseDto;


/**
 * 征信
 * @author huanglj
 *
 */
public class CreditDto extends BaseDto{
    
    private Integer id;

    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_risk_credit.orderNo
     *
     * @mbggenerated 2017-06-08
     */
    private String orderNo;

    /**
     *信用卡年限
     *
     * This field corresponds to the database column tbl_risk_credit.creditCardYears
     *
     * @mbggenerated 2017-06-08
     */
    private String creditCardYears;

    /**
     *个人贷款记录年限
     *
     * This field corresponds to the database column tbl_risk_credit.loanRecordYears
     *
     * @mbggenerated 2017-06-08
     */
    private String loanRecordYears;

    /**
     *个人贷款,信用卡累计有效违约率
     *
     * This field corresponds to the database column tbl_risk_credit.violationProportion
     *
     * @mbggenerated 2017-06-08
     */
    private String violationProportion;

    /**
     *所有房产评估总值(万元)
     *
     * This field corresponds to the database column tbl_risk_credit.allHouseWorth
     *
     * @mbggenerated 2017-06-08
     */
    private Double allHouseWorth;

    /**
     *房产套数
     *
     * This field corresponds to the database column tbl_risk_credit.allHouseNumber
     *
     * @mbggenerated 2017-06-08
     */
    private Integer allHouseNumber;

    /**
     *信用卡半年月均透支额(万元)
     *
     * This field corresponds to the database column tbl_risk_credit.creditCardOverdraft
     *
     * @mbggenerated 2017-06-08
     */
    private Double creditCardOverdraft;

    /**
     *授信总额（万元）
     *
     * This field corresponds to the database column tbl_risk_credit.creditQuota
     *
     * @mbggenerated 2017-06-08
     */
    private Double creditQuota;

    /**
     *已用额度(万元)
     *
     * This field corresponds to the database column tbl_risk_credit.useQuota
     *
     * @mbggenerated 2017-06-08
     */
    private Double useQuota;

    /**
     *征信总负债(万元)
     *
     * This field corresponds to the database column tbl_risk_credit.creditLiabilities
     *
     * @mbggenerated 2017-06-08
     */
    private Double creditLiabilities;

    /**
     *负债比例
     *
     * This field corresponds to the database column tbl_risk_credit.liabilitiesProportion
     *
     * @mbggenerated 2017-06-08
     */
    private Double liabilitiesProportion;

    /**
     *借款成数
     *
     * This field corresponds to the database column tbl_risk_credit.loanPercentage
     *
     * @mbggenerated 2017-06-08
     */
    private Double loanPercentage;

    /**
     *赎楼成数
     *
     * This field corresponds to the database column tbl_risk_credit.foreclosurePercentage
     *
     * @mbggenerated 2017-06-08
     */
    private Double foreclosurePercentage;

    /**
     *征信报告逾期数
     *
     * This field corresponds to the database column tbl_risk_credit.creditOverdueNumber
     *
     * @mbggenerated 2017-06-08
     */
    private Integer creditOverdueNumber;

    /**
     *近半年征信查询次数
     *
     * This field corresponds to the database column tbl_risk_credit.latelyHalfYearSelectNumber
     *
     * @mbggenerated 2017-06-08
     */
    private Integer latelyHalfYearSelectNumber;

    /**
     *2年内金额2000以上有逾期次数
     *
     * This field corresponds to the database column tbl_risk_credit.latelyTwoYearMoneyOverdueNumber
     *
     * @mbggenerated 2017-06-08
     */
    private Integer latelyTwoYearMoneyOverdueNumber;

    /**
     *原贷款是否银行(是,不限)
     *
     * This field corresponds to the database column tbl_risk_credit.oldLoanIsBank
     *
     * @mbggenerated 2017-06-08
     */
    private Integer oldLoanIsBank;

    /**
     *新贷款是否银行(是,不限)
     *
     * This field corresponds to the database column tbl_risk_credit.newLoanIsBank
     *
     * @mbggenerated 2017-06-08
     */
    private Integer newLoanIsBank;

    /**
     *是否公司产权(是,否)
     *
     * This field corresponds to the database column tbl_risk_credit.isCompanyProperty
     *
     * @mbggenerated 2017-06-08
     */
    private String isCompanyProperty;

    /**
     *产权抵押情况
     *
     * This field corresponds to the database column tbl_risk_credit.propertyMortgage
     *
     * @mbggenerated 2017-06-08
     */
    private String propertyMortgage;
    /**
     * 是否需要创建日志流水
     */
    private boolean createCreditLog;
    /**
     * 备注
     */
    private String remark;
    
    /**是否完成1:是,2:否**/
    private int isFinish;
    
    //=====风控模型计算需用到======
    /** 赎楼客户姓名 */
	private String borrowerName;
	/** 身份证 */
	private String idCardNo;
	/** 赎楼金额 */
	private Double loanAmount;
	/** 赎楼房产评估总值 */
	private Double houseWorth;
	/** 是否诉讼 */
	private Integer isLitigation;
	/** 初审处理人id */
	private String auditUid;
	 //=====风控模型计算需用到======
    
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

    public String getCreditCardYears() {
        return creditCardYears;
    }

    public void setCreditCardYears(String creditCardYears) {
        this.creditCardYears = creditCardYears;
    }

    public String getLoanRecordYears() {
        return loanRecordYears;
    }

    public void setLoanRecordYears(String loanRecordYears) {
        this.loanRecordYears = loanRecordYears;
    }

    public String getViolationProportion() {
        return violationProportion;
    }

    public void setViolationProportion(String violationProportion) {
        this.violationProportion = violationProportion;
    }

    public Double getAllHouseWorth() {
        return allHouseWorth;
    }

    public void setAllHouseWorth(Double allHouseWorth) {
        this.allHouseWorth = allHouseWorth;
    }

    public Integer getAllHouseNumber() {
        return allHouseNumber;
    }

    public void setAllHouseNumber(Integer allHouseNumber) {
        this.allHouseNumber = allHouseNumber;
    }

    public Double getCreditCardOverdraft() {
        return creditCardOverdraft;
    }

    public void setCreditCardOverdraft(Double creditCardOverdraft) {
        this.creditCardOverdraft = creditCardOverdraft;
    }

    public Double getCreditQuota() {
        return creditQuota;
    }

    public void setCreditQuota(Double creditQuota) {
        this.creditQuota = creditQuota;
    }

    public Double getUseQuota() {
        return useQuota;
    }

    public void setUseQuota(Double useQuota) {
        this.useQuota = useQuota;
    }

    public Double getCreditLiabilities() {
        return creditLiabilities;
    }

    public void setCreditLiabilities(Double creditLiabilities) {
        this.creditLiabilities = creditLiabilities;
    }

    public Double getLiabilitiesProportion() {
        return liabilitiesProportion;
    }

    public void setLiabilitiesProportion(Double liabilitiesProportion) {
        this.liabilitiesProportion = liabilitiesProportion;
    }

    public Double getLoanPercentage() {
        return loanPercentage;
    }

    public void setLoanPercentage(Double loanPercentage) {
        this.loanPercentage = loanPercentage;
    }

    public Double getForeclosurePercentage() {
        return foreclosurePercentage;
    }

    public void setForeclosurePercentage(Double foreclosurePercentage) {
        this.foreclosurePercentage = foreclosurePercentage;
    }

    public Integer getCreditOverdueNumber() {
        return creditOverdueNumber;
    }

    public void setCreditOverdueNumber(Integer creditOverdueNumber) {
        this.creditOverdueNumber = creditOverdueNumber;
    }

    public Integer getLatelyHalfYearSelectNumber() {
        return latelyHalfYearSelectNumber;
    }

    public void setLatelyHalfYearSelectNumber(Integer latelyHalfYearSelectNumber) {
        this.latelyHalfYearSelectNumber = latelyHalfYearSelectNumber;
    }

    public Integer getLatelyTwoYearMoneyOverdueNumber() {
        return latelyTwoYearMoneyOverdueNumber;
    }

    public void setLatelyTwoYearMoneyOverdueNumber(Integer latelyTwoYearMoneyOverdueNumber) {
        this.latelyTwoYearMoneyOverdueNumber = latelyTwoYearMoneyOverdueNumber;
    }

    public String getIsCompanyProperty() {
        return isCompanyProperty;
    }

    public void setIsCompanyProperty(String isCompanyProperty) {
        this.isCompanyProperty = isCompanyProperty;
    }

    public String getPropertyMortgage() {
        return propertyMortgage;
    }

    public void setPropertyMortgage(String propertyMortgage) {
        this.propertyMortgage = propertyMortgage;
    }

	public boolean isCreateCreditLog() {
		return createCreditLog;
	}

	public void setCreateCreditLog(boolean createCreditLog) {
		this.createCreditLog = createCreditLog;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getHouseWorth() {
		return houseWorth;
	}

	public void setHouseWorth(Double houseWorth) {
		this.houseWorth = houseWorth;
	}

	public Integer getIsLitigation() {
		return isLitigation;
	}

	public void setIsLitigation(Integer isLitigation) {
		this.isLitigation = isLitigation;
	}

	public String getAuditUid() {
		return auditUid;
	}

	public void setAuditUid(String auditUid) {
		this.auditUid = auditUid;
	}

	public Integer getOldLoanIsBank() {
		return oldLoanIsBank;
	}

	public void setOldLoanIsBank(Integer oldLoanIsBank) {
		this.oldLoanIsBank = oldLoanIsBank;
	}

	public Integer getNewLoanIsBank() {
		return newLoanIsBank;
	}

	public void setNewLoanIsBank(Integer newLoanIsBank) {
		this.newLoanIsBank = newLoanIsBank;
	}

	public int getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(int isFinish) {
		this.isFinish = isFinish;
	}

}