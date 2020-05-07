package com.anjbo.bean.yntrust;

import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 回款计划支付信息
 */
@ApiModel(value = "回款计划支付信息")
public class YntrustRepaymentPayDto extends BaseDto {
    /**
     *
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.id
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "回款计划支付信息")
    private Integer id;


    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.orderNo
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "信贷系统订单编号")
    private String orderNo;

    /**
     *还款订单交易请求编号 云信生成 与trustTransactionNo二选一
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.trustTransactionNo
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "还款订单交易请求编号 云信生成 与trustTransactionNo二选一")
    private String trustTransactionNo;

    /**
     *还款订单交易请求编号 快鸽生成 与transactionNo二选一
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.transactionNo
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "还款订单交易请求编号 快鸽生成 与transactionNo二选一")
    private String transactionNo;

    /**
     *交易类型 (0:线下转账 2:线上代扣)
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.voucherType
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "交易类型 (0:线下转账 2:线上代扣)")
    private String voucherType;

    /**
     *交易类型名称
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.voucherTypeName
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "交易类型名称")
    private String voucherTypeName;

    /**
     *银行帐户号
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.accountNo
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "银行帐户号")
    private String accountNo;

    /**
     *银行帐户名称
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.accountName
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "银行帐户名称")
    private String accountName;

    /**
     *通道交易号 线上代扣则为空 线下转账必填（付款流水号）
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.bankTransactionNo
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "通道交易号 线上代扣则为空 线下转账必填（付款流水号）")
    private String bankTransactionNo;

    /**
     *交易金额
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.amount
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "交易金额")
    private Double amount;

    /**
     *收款方账号 为空则默认信托专户账户
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.trustAccountNo
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "收款方账号 为空则默认信托专户账户")
    private String trustAccountNo;

    /**
     *收款方户名 为空则默认信托专户账户
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.trustAccountName
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "收款方户名 为空则默认信托专户账户")
    private String trustAccountName;

    /**
     *收款方开户行 为空则默认信托专户开户行
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.trustBankCode
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "收款方开户行 为空则默认信托专户开户行")
    private String trustBankCode;

    /**
     *支付状态
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.status
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "支付状态")
    private String status;
    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称")
    private String statusName;

    /**
     *失败原因
     *
     * This field corresponds to the database column tbl_third_yntrust_repayment_pay.failMsg
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "失败原因")
    private String failMsg;
    /**
     * 支付编号(云南信托生成)
     */
    @ApiModelProperty(value = "支付编号(云南信托生成)")
    private String trustPaymentNos;
    /**
     * 交易完成时间
     */
    @ApiModelProperty(value = "交易完成时间")
    private Date completeTime;
    /**
     * 交易入账时间
     */
    @ApiModelProperty(value = "交易入账时间")
    private Date fundAcceptTime;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getTrustTransactionNo() {
        return trustTransactionNo;
    }

    public void setTrustTransactionNo(String trustTransactionNo) {
        this.trustTransactionNo = trustTransactionNo == null ? null : trustTransactionNo.trim();
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType == null ? null : voucherType.trim();
    }

    public String getVoucherTypeName() {
        return voucherTypeName;
    }

    public void setVoucherTypeName(String voucherTypeName) {
        this.voucherTypeName = voucherTypeName == null ? null : voucherTypeName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getBankTransactionNo() {
        return bankTransactionNo;
    }

    public void setBankTransactionNo(String bankTransactionNo) {
        this.bankTransactionNo = bankTransactionNo == null ? null : bankTransactionNo.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTrustAccountNo() {
        return trustAccountNo;
    }

    public void setTrustAccountNo(String trustAccountNo) {
        this.trustAccountNo = trustAccountNo == null ? null : trustAccountNo.trim();
    }

    public String getTrustAccountName() {
        return trustAccountName;
    }

    public void setTrustAccountName(String trustAccountName) {
        this.trustAccountName = trustAccountName == null ? null : trustAccountName.trim();
    }

    public String getTrustBankCode() {
        return trustBankCode;
    }

    public void setTrustBankCode(String trustBankCode) {
        this.trustBankCode = trustBankCode == null ? null : trustBankCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getTrustPaymentNos() {
        return trustPaymentNos;
    }
    public void setTrustPaymentNos(String trustPaymentNos) {
        this.trustPaymentNos = trustPaymentNos;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getFundAcceptTime() {
        return fundAcceptTime;
    }

    public void setFundAcceptTime(Date fundAcceptTime) {
        this.fundAcceptTime = fundAcceptTime;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }
}