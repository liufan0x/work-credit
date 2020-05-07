package com.anjbo.bean.finance;

import com.anjbo.bean.BaseDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AfterLoanLogDto extends BaseDto{
    /**
     *
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.id
     *
     * @mbggenerated 2018-04-08
     */
    private Integer id;


    /**
     *还款期数
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.repaymentPeriods
     *
     * @mbggenerated 2018-04-08
     */
    private Integer repaymentPeriods;

    /**
     *还款金额
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.amount
     *
     * @mbggenerated 2018-04-08
     */
    private Double amount;

    /**
     *收款方式
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.repaymentType
     *
     * @mbggenerated 2018-04-08
     */
    private Integer repayType;

    /**
     *实际还款日期
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.realityPayDate
     *
     * @mbggenerated 2018-04-08
     */
    private Date realityPayDate;

    /**
     *应还逾期费
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.repayOverdue
     *
     * @mbggenerated 2018-04-08
     */
    private Double repayOverdue;

    /**
     *已还逾期费
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.givePayOverdue
     *
     * @mbggenerated 2018-04-08
     */
    private Double givePayOverdue;

    /**
     *已还利息
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.givePayInterest
     *
     * @mbggenerated 2018-04-08
     */
    private Double givePayInterest;

    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.orderNo
     *
     * @mbggenerated 2018-04-08
     */
    private String orderNo;

    /**
     *事件类型(0:其他,1:贷后检查,2:逾期处理,3:还款记录)
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.eventType
     *
     * @mbggenerated 2018-04-08
     */
    private Integer eventType;

    /**
     *事件类型名称
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.eventTypeName
     *
     * @mbggenerated 2018-04-08
     */
    private String eventTypeName;

    /**
     *时间内容
     *
     * This field corresponds to the database column tbl_finance_afterloan_log.remark
     *
     * @mbggenerated 2018-04-08
     */
    private String remark;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 条件名称主要用于列表检索
     */
    private String whereName;
    /**
     * 操作开始时间主要用于列表检索
     */
    private Date createStartTime;
    /**
     * 操作结束时间主要用于列表检索
     */
    private Date createEndTime;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 操作时间排序
     */
    private String operateTimeOrderBy;
    /**
     * 关联的附件
     */
    private List<Map<String,Object>> file;
    /**
     * 溢出
     */
    private Double overflow;

    /**
     * 应还本金
     */
    private Double repayPrincipal;
    /**
     * 已还本金
     */
    private Double givePayPrincipal;
    /**
     * 是否撤回(1:是,2:否)
     */
    private Integer withdraw;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepaymentPeriods() {
        return repaymentPeriods;
    }

    public void setRepaymentPeriods(Integer repaymentPeriods) {
        this.repaymentPeriods = repaymentPeriods;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getRealityPayDate() {
        return realityPayDate;
    }

    public void setRealityPayDate(Date realityPayDate) {
        this.realityPayDate = realityPayDate;
    }

    public Double getRepayOverdue() {
        return repayOverdue;
    }

    public void setRepayOverdue(Double repayOverdue) {
        this.repayOverdue = repayOverdue;
    }

    public Double getGivePayOverdue() {
        return givePayOverdue;
    }

    public void setGivePayOverdue(Double givePayOverdue) {
        this.givePayOverdue = givePayOverdue;
    }

    public Double getGivePayInterest() {
        return givePayInterest;
    }

    public void setGivePayInterest(Double givePayInterest) {
        this.givePayInterest = givePayInterest;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName == null ? null : eventTypeName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getWhereName() {
        return whereName;
    }

    public void setWhereName(String whereName) {
        this.whereName = whereName;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public List<Map<String, Object>> getFile() {
        return file;
    }

    public void setFile(List<Map<String, Object>> file) {
        this.file = file;
    }

    public Double getOverflow() {
        return overflow;
    }

    public void setOverflow(Double overflow) {
        this.overflow = overflow;
    }

    public Integer getRepayType() {
        return repayType;
    }

    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    public Double getRepayPrincipal() {
        return repayPrincipal;
    }

    public void setRepayPrincipal(Double repayPrincipal) {
        this.repayPrincipal = repayPrincipal;
    }

    public Double getGivePayPrincipal() {
        return givePayPrincipal;
    }

    public void setGivePayPrincipal(Double givePayPrincipal) {
        this.givePayPrincipal = givePayPrincipal;
    }

    public String getOperateTimeOrderBy() {
        return operateTimeOrderBy;
    }

    public void setOperateTimeOrderBy(String operateTimeOrderBy) {
        this.operateTimeOrderBy = operateTimeOrderBy;
    }

    public Integer getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Integer withdraw) {
        this.withdraw = withdraw;
    }
}