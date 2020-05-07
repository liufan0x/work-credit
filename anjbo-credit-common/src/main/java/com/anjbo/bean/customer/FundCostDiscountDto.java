package com.anjbo.bean.customer;

import java.util.Date;
/**
 * 合作资金方成本优惠
 * @author Administrator
 *
 */
public class FundCostDiscountDto {
	 
    private Integer id;

    /**
     *产品ID，关联表 tbl_customer_fund_cost
     *
     * This field corresponds to the database column tbl_customer_fund_cost_discount.fundCostId
     *
     * @mbggenerated 2017-06-15
     */
    private Integer fundCostId;

    /**
     *满足金额
     *
     * This field corresponds to the database column tbl_customer_fund_cost_discount.money
     *
     * @mbggenerated 2017-06-15
     */
    private Double money;

    /**
     *费率（单位：%/天）
     *
     * This field corresponds to the database column tbl_customer_fund_cost_discount.rate
     *
     * @mbggenerated 2017-06-15
     */
    private Double rate;

    /**
     *创建时间
     *
     * This field corresponds to the database column tbl_customer_fund_cost_discount.createTime
     *
     * @mbggenerated 2017-06-15
     */
    private Date createTime;

    /**
     *最后更新时间
     *
     * This field corresponds to the database column tbl_customer_fund_cost_discount.updateTime
     *
     * @mbggenerated 2017-06-15
     */
    private Date updateTime;

    /**
     *创建人uid
     *
     * This field corresponds to the database column tbl_customer_fund_cost_discount.createUid
     *
     * @mbggenerated 2017-06-15
     */
    private Integer createUid;

    /**
     *
     *
     * This field corresponds to the database column tbl_customer_fund_cost_discount.updateUid
     *
     * @mbggenerated 2017-06-15
     */
    private Integer updateUid;

    /**
     *备注
     *
     * This field corresponds to the database column tbl_customer_fund_cost_discount.remark
     *
     * @mbggenerated 2017-06-15
     */
    private String remark;

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFundCostId() {
        return fundCostId;
    }

    public void setFundCostId(Integer fundCostId) {
        this.fundCostId = fundCostId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    public Integer getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}