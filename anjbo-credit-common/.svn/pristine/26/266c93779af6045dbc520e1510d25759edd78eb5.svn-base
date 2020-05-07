package com.anjbo.bean.yntrust;

import com.anjbo.bean.BaseDto;

import java.util.Date;

/**
 * 云南信托还款计划
 */
public class YntrustRepaymentPlanDto extends BaseDto {
    /**
     *
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_plan.id
     *
     * @mbggenerated 2018-03-08
     */
    private Integer id;

    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_plan.orderNo
     *
     * @mbggenerated 2018-03-08
     */
    private String orderNo;

    /**
     *计划还款时间
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_plan.repayDate
     *
     * @mbggenerated 2018-03-08
     */
    private Date repayDate;

    /**
     *借款期限
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_plan.borrowingDays
     *
     * @mbggenerated 2018-03-08
     */
    private Integer borrowingDays;

    /**
     *计划还款本金
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_plan.repayPrincipal
     *
     * @mbggenerated 2018-03-08
     */
    private Double repayPrincipal;

    /**
     *计划还款利息
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_plan.repayProfit
     *
     * @mbggenerated 2018-03-08
     */
    private Double repayProfit;
    /**
     * 还款计划编号 每期还款计划的唯一标识
     */
    private String partnerScheduleNo;
    /**
     * 推送状态(0:初始化,1:推送成功,2:推送失败)
     */
    private Integer pushStatus;
    /**
     * 还款计划类型
     * 如果为空则为正常未改变，为0代表提前还款（提前部分或全额还款）类型
     */
    private String scheduleType;
    /**
     * 还款计划变更原因
     * 目前固定为3种变更原因：
     0=项目结清
     1=提前部分还款
     2=错误更正
     */
    private String changeReason;

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

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Integer getBorrowingDays() {
        return borrowingDays;
    }

    public void setBorrowingDays(Integer borrowingDays) {
        this.borrowingDays = borrowingDays;
    }

    public Double getRepayPrincipal() {
        return repayPrincipal;
    }

    public void setRepayPrincipal(Double repayPrincipal) {
        this.repayPrincipal = repayPrincipal;
    }

    public Double getRepayProfit() {
        return repayProfit;
    }

    public void setRepayProfit(Double repayProfit) {
        this.repayProfit = repayProfit;
    }

    public String getPartnerScheduleNo() {
        return partnerScheduleNo;
    }

    public void setPartnerScheduleNo(String partnerScheduleNo) {
        this.partnerScheduleNo = partnerScheduleNo;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
}