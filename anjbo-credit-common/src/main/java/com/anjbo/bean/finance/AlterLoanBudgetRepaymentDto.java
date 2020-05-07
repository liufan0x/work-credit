package com.anjbo.bean.finance;

import com.anjbo.bean.BaseDto;
import org.springframework.data.annotation.Transient;

import java.util.Date;

/**
 * 贷后分期
 */
public class AlterLoanBudgetRepaymentDto extends BaseDto{
    /**
     * This field corresponds to the database column tbl_finance_afterloan_advance.id
     *
     * @mbggenerated 2018-04-03
     */
    private Integer id;


    /**
     *订单号
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.orderNo
     *
     * @mbggenerated 2018-04-03
     */
    private String orderNo;

    /**
     *还款期数
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.repaymentPeriods
     *
     * @mbggenerated 2018-04-03
     */
    private Integer repaymentPeriods;

    /**
     *还款日期
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.repaymentDate
     *
     * @mbggenerated 2018-04-03
     */
    private Date repaymentDate;

    private String repaymentDateStr;
    /**
     *剩余本金/元
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.surplusPrincipal
     *
     * @mbggenerated 2018-04-03
     */
    private Double surplusPrincipal;

    /**
     *应还金额/元
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.repayAmount
     *
     * @mbggenerated 2018-04-03
     */
    private Double repayAmount;

    /**
     *应还本金/元
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.repayPrincipal
     *
     * @mbggenerated 2018-04-03
     */
    private Double repayPrincipal;

    /**
     *应还利息/元
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.repayInterest
     *
     * @mbggenerated 2018-04-03
     */
    private Double repayInterest;

    /**
     *应还逾期/元
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.repayOverdue
     *
     * @mbggenerated 2018-04-03
     */
    private Double repayOverdue;

    /**
     *已还本金/元
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.givePayPrincipal
     *
     * @mbggenerated 2018-04-03
     */
    private Double givePayPrincipal;

    /**
     *已还利息/元
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.givePayInterest
     *
     * @mbggenerated 2018-04-03
     */
    private Double givePayInterest;

    /**
     * This field corresponds to the database column tbl_finance_afterloan_advance.givePayOverdue
     *
     * @mbggenerated 2018-04-03
     */
    private Double givePayOverdue;

    /**
     *逾期天数
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.lateDay
     *
     * @mbggenerated 2018-04-03
     */
    private Integer lateDay;

    /**
     *还款状态
     *
     * This field corresponds to the database column tbl_finance_afterloan_advance.status
     *
     * @mbggenerated 2018-04-03
     */
    private Integer status;
    /**
     * 费率
     */
    private Double rate;
    /**
     * 逾期费率
     */
    private Double overdueRate;
    /**
     * 列表控制还款按钮
     */
    @Transient
    private boolean statusButton;
    /**
     * 溢出
     */
    private Double overflow;
    /**
     * 最后一期
     */
    private boolean lastPeriods;
    /**
     * 实际还款日期
     */
    private Date realityPayDate;
    /**
     * 状态名称
     */
    private String statusName;
    /**
     * 1:关闭客户,2开启所有,9:关闭所有
     */
    private Integer closeMsg;
    /**
     * 特殊字段
     */
    private String special;

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
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getRepaymentPeriods() {
        return repaymentPeriods;
    }

    public void setRepaymentPeriods(Integer repaymentPeriods) {
        this.repaymentPeriods = repaymentPeriods;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Double getSurplusPrincipal() {
        return surplusPrincipal;
    }

    public void setSurplusPrincipal(Double surplusPrincipal) {
        this.surplusPrincipal = surplusPrincipal;
    }

    public Double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public Double getRepayPrincipal() {
        return repayPrincipal;
    }

    public void setRepayPrincipal(Double repayPrincipal) {
        this.repayPrincipal = repayPrincipal;
    }

    public Double getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(Double repayInterest) {
        this.repayInterest = repayInterest;
    }

    public Double getRepayOverdue() {
        return repayOverdue;
    }

    public void setRepayOverdue(Double repayOverdue) {
        this.repayOverdue = repayOverdue;
    }

    public Double getGivePayPrincipal() {
        return givePayPrincipal;
    }

    public void setGivePayPrincipal(Double givePayPrincipal) {
        this.givePayPrincipal = givePayPrincipal;
    }

    public Double getGivePayInterest() {
        return givePayInterest;
    }

    public void setGivePayInterest(Double givePayInterest) {
        this.givePayInterest = givePayInterest;
    }

    public Double getGivePayOverdue() {
        return givePayOverdue;
    }

    public void setGivePayOverdue(Double givePayOverdue) {
        this.givePayOverdue = givePayOverdue;
    }

    public Integer getLateDay() {
        return lateDay;
    }

    public void setLateDay(Integer lateDay) {
        this.lateDay = lateDay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public boolean isStatusButton() {
        return statusButton;
    }

    public void setStatusButton(boolean statusButton) {
        this.statusButton = statusButton;
    }

    public Double getOverflow() {
        return overflow;
    }

    public void setOverflow(Double overflow) {
        this.overflow = overflow;
    }

    public boolean isLastPeriods() {
        return lastPeriods;
    }

    public void setLastPeriods(boolean lastPeriods) {
        this.lastPeriods = lastPeriods;
    }

    public Date getRealityPayDate() {
        return realityPayDate;
    }

    public void setRealityPayDate(Date realityPayDate) {
        this.realityPayDate = realityPayDate;
    }

    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRepaymentDateStr() {
        return repaymentDateStr;
    }

    public void setRepaymentDateStr(String repaymentDateStr) {
        this.repaymentDateStr = repaymentDateStr;
    }

    public Integer getCloseMsg() {
        return closeMsg;
    }

    public void setCloseMsg(Integer closeMsg) {
        this.closeMsg = closeMsg;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }
}