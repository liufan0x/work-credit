package com.anjbo.bean.yntrust;

import com.anjbo.bean.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 云南信托回款计划还款信息
 */
@ApiModel(value = "云南信托回款计划还款信息")
public class YntrustRepaymentInfoDto extends BaseDto {
    /**
     *
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.id
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "云南信托回款计划还款信息")
    private Integer id;

    /**
     *信贷系统订单编码
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.orderNo
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "信贷系统订单编码")
    private String orderNo;

    /**
     *预计还款时间
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.repayDate
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "预计还款时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date repayDate;

    /**
     *实际还款时间
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.realityPayDate
     *
     * @mbggenerated 2018-03-08
     */

    @ApiModelProperty(value = "实际还款时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date realityPayDate;

    /**
     *应还本金
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.repayPrincipal
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "应还本金")
    private Double repayPrincipal;

    /**
     *已还本金
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.givePayPrincipal
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "已还本金")
    private Double givePayPrincipal;

    /**
     *应还利息
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.repayProfit
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "应还利息")
    private Double repayProfit;

    /**
     *已还利息
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.givePayProfit
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "已还利息")
    private Double givePayProfit;

    /**
     *借款期限
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.borrowingDays
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "借款期限")
    private Integer borrowingDays;

    /**
     *逾期天数
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.lateDay
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "逾期天数")
    private Integer lateDay;

    /**
     *逾期罚息
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.penaltyFee
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "逾期罚息")
    private Double penaltyFee;

    /**
     *逾期违约金
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.latePenalty
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "逾期违约金")
    private Double latePenalty;

    /**
     *逾期服务费
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.lateFee
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "逾期服务费")
    private Double lateFee;

    /**
     *逾期其他费用
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.lateOtherCost
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "逾期其他费用")
    private Double lateOtherCost;

    /**
     *其他费用
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.otherFee
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "其他费用")
    private Double otherFee;

    /**
     *逾期费用总计
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.overDueFee
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "逾期费用总计")
    private Double overDueFee;

    /**
     *扣款总金额(该金额必须等于还款本金+还款利息+技术服务费+贷款服务费+其他费用+罚息+逾期违约金+逾期服务费+逾期其他费用之和)
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.amount
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "扣款总金额(该金额必须等于还款本金+还款利息+技术服务费+贷款服务费+其他费用+罚息+逾期违约金+逾期服务费+逾期其他费用之和)")
    private Double amount;

    /**
     *扣款类型（0:提前划扣,1:正常扣款,2:逾期）
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.repaymentType
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "扣款类型（0:提前划扣,1:正常扣款,2:逾期）")
    private String repaymentType;

    /**
     *扣款类型名称
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.repaymentTypeName
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "扣款类型名称")
    private String repaymentTypeName;

    /**
     *还款状态
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.status
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "还款状态(0:已创建,1:检验中,2:检验失败,3:检验成功,4:支付中,5:支付异常(说明：还款方式为1000线上代扣代扣失败后对应状态),6:支付成功,7:已完成,8:撤销,9:支付失败(说明：还款方式为5001快捷支付代扣失败后对应状态))")
    private Integer status;
    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称")
    private String statusName;

    /**
     *失败原因
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_info.failMsg
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "失败原因")
    private String failMsg;
    /**
     * 交易请求编号
     */
    @ApiModelProperty(value = "交易请求编号(交易类型为“提交”时，唯一性校验；交易类型为“撤销”时，存在性校验)")
    private String transactionNo;
    /**
     * 还款方式
     */
    @ApiModelProperty(value = "还款方式")
    private Integer paymentMethod;
    /**
     * 还款方式名称
     */
    @ApiModelProperty(value = "还款方式名称")
    private String paymentMethodName;
    /**
     * 资金方处理完成时间
     */
    @ApiModelProperty(value = "资金方处理完成时间")
    private Date occurTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 推送状态(0:初始化,1:推送成功,2:推送失败)
     */
    @ApiModelProperty(value = "推送状态(0:初始化,1:推送成功,2:推送失败)")
    private Integer pushStatus;
    
    /**
     * 云南信托产品编码
     */
    @ApiModelProperty(value = "云南信托产品编码")
    private String ynProductCode;
    /**
     * 云南信托产品编码名称
     */
    @ApiModelProperty(value = "云南信托产品编码名称")
    private String ynProductName;
    
    /**
     * 贷后资料存储路径
     * */
    @ApiModelProperty(value = "贷后资料存储路径")
    private String postLoanUrl;
    
    /**
     * 还款计划编号 每期还款计划的唯一标识
     */
    @ApiModelProperty(value = "还款计划编号 每期还款计划的唯一标识")
    private String partnerScheduleNo;

    public String getPostLoanUrl() {
		return postLoanUrl;
	}

	public void setPostLoanUrl(String postLoanUrl) {
		this.postLoanUrl = postLoanUrl;
	}

	public String getYnProductCode() {
		return ynProductCode;
	}

	public void setYnProductCode(String ynProductCode) {
		this.ynProductCode = ynProductCode;
	}

	public String getYnProductName() {
		return ynProductName;
	}

	public void setYnProductName(String ynProductName) {
		this.ynProductName = ynProductName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Date getRealityPayDate() {
        return realityPayDate;
    }

    public void setRealityPayDate(Date realityPayDate) {
        this.realityPayDate = realityPayDate;
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

    public Double getRepayProfit() {
        return repayProfit;
    }

    public void setRepayProfit(Double repayProfit) {
        this.repayProfit = repayProfit;
    }


    public Integer getBorrowingDays() {
        return borrowingDays;
    }

    public void setBorrowingDays(Integer borrowingDays) {
        this.borrowingDays = borrowingDays;
    }

    public Integer getLateDay() {
        return lateDay;
    }

    public void setLateDay(Integer lateDay) {
        this.lateDay = lateDay;
    }

    public Double getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Double penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public Double getLatePenalty() {
        return latePenalty;
    }

    public void setLatePenalty(Double latePenalty) {
        this.latePenalty = latePenalty;
    }

    public Double getLateFee() {
        return lateFee;
    }

    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }

    public Double getLateOtherCost() {
        return lateOtherCost;
    }

    public void setLateOtherCost(Double lateOtherCost) {
        this.lateOtherCost = lateOtherCost;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    public Double getOverDueFee() {
        return overDueFee;
    }

    public void setOverDueFee(Double overDueFee) {
        this.overDueFee = overDueFee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType == null ? null : repaymentType.trim();
    }

    public String getRepaymentTypeName() {
        return repaymentTypeName;
    }

    public void setRepaymentTypeName(String repaymentTypeName) {
        this.repaymentTypeName = repaymentTypeName == null ? null : repaymentTypeName.trim();
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg == null ? null : failMsg.trim();
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderNo() {

    	return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public Double getGivePayProfit() {
        return givePayProfit;
    }

    public void setGivePayProfit(Double givePayProfit) {
        this.givePayProfit = givePayProfit;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

	public String getPartnerScheduleNo() {
		return partnerScheduleNo;
	}

	public void setPartnerScheduleNo(String partnerScheduleNo) {
		this.partnerScheduleNo = partnerScheduleNo;
	}
    
}