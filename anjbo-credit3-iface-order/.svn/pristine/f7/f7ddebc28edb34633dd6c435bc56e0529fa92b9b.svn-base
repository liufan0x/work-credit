/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.anjbo.bean.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@ApiModel(value = "订单房产交易信息")
public class BaseHouseDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 房产信息*/
	@ApiModelProperty(value = "房产信息")
    private List<BaseHousePropertyDto> baseHousePropertyDto;
	
    /** 买房人信息*/
	@ApiModelProperty(value = "买房人信息")
    private List<BaseHousePurchaserDto> baseHousePurchaserDto;
	
    /** 产权人信息 */
	@ApiModelProperty(value = "产权人信息")
    private List<BaseHousePropertypeopleDto> baseHousePropertypeopleDto;
	
	/** 订单房产交易信息 */
	@ApiModelProperty(value = "订单房产交易信息")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 原房贷金额(万元) */
	@ApiModelProperty(value = "原房贷金额(万元)")
	private Long oldHouseLoanAmount;
	
	/** 原房贷余额(万元) */
	@ApiModelProperty(value = "原房贷余额(万元)")
	private Long oldHouseLoanBalance;
	
	/** 成交价格(万元) */
	@ApiModelProperty(value = "成交价格(万元)")
	private Long houseDealPrice;
	
	/** 成交定金(万元) */
	@ApiModelProperty(value = "成交定金(万元)")
	private Long houseDealDeposit;
	
	/** 资金监管金额(万元) */
	@ApiModelProperty(value = "资金监管金额(万元)")
	private Long houseSuperviseAmount;
	
	/** 贷款金额(万元) */
	@ApiModelProperty(value = "贷款金额(万元)")
	private Long houseLoanAmount;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 是否完成1:是,2:否 */
	@ApiModelProperty(value = "是否完成1:是,2:否")
	private java.lang.Integer isFinish;
	
	/** 业务类型：0未知1交易2非交易 */
	@ApiModelProperty(value = "业务类型：0未知1交易2非交易")
	private java.lang.Integer bussinessType;
	
	/** 抵押是否银行,1是2否,yesOrNo */
	@ApiModelProperty(value = "抵押是否银行,1是2否,yesOrNo")
	private java.lang.Integer mortgageIsBank;
	
	/** 抵押地点 */
	@ApiModelProperty(value = "抵押地点")
	private java.lang.String mortgageAddress;
	
	/** 客户经理 */
	@ApiModelProperty(value = "客户经理")
	private java.lang.String customerMgr;
	
	/** 客户经理电话 */
	@ApiModelProperty(value = "客户经理电话")
	private java.lang.String customerMgrPhone;
	
	/** 抵押期限S */
	@ApiModelProperty(value = "抵押期限S")
	private java.lang.String mortgageCycleStart;
	
	/** 抵押期限E */
	@ApiModelProperty(value = "抵押期限E")
	private java.lang.String mortgageCycleEnd;
	
	/** 贷款金额(元) */
	@ApiModelProperty(value = "贷款金额(元)")
	private Long amountLoan;
	
	/** 贷款余额(元) */
	@ApiModelProperty(value = "贷款余额(元)")
	private Long amountLoanSurplus;
	
	/** 借款期限S */
	@ApiModelProperty(value = "借款期限S")
	private java.lang.String lendingCycleStart;
	
	/** 借款期限E */
	@ApiModelProperty(value = "借款期限E")
	private java.lang.String lendingCycleEnd;
	
	/** 还款方式ENU(0默认兼容老数据1先息后本2等额本息),paidType */
	@ApiModelProperty(value = "还款方式ENU(0默认兼容老数据1先息后本2等额本息),paidType")
	private java.lang.Integer paidType;
	
	/** 抵押银行id */
	@ApiModelProperty(value = "抵押银行id")
	private java.lang.String mortgageBankId;
	
	/** 抵押支行id */
	@ApiModelProperty(value = "抵押支行id")
	private java.lang.String mortgageSubBankId;
	
	/** 抵押银行 */
	@ApiModelProperty(value = "抵押银行")
	private java.lang.String mortgageBankName;
	
	/** 抵押支行 */
	@ApiModelProperty(value = "抵押支行")
	private java.lang.String mortgageSubBankName;
	

	public List<BaseHousePropertyDto> getBaseHousePropertyDto() {
		if(null == this.baseHousePropertyDto || this.baseHousePropertyDto.size() <= 0) {
			this.baseHousePropertyDto = new ArrayList<BaseHousePropertyDto>();
		}
		return baseHousePropertyDto;
	}
	public void setBaseHousePropertyDto(List<BaseHousePropertyDto> baseHousePropertyDto) {
		this.baseHousePropertyDto = baseHousePropertyDto;
	}
	public List<BaseHousePurchaserDto> getBaseHousePurchaserDto() {
		if(null == this.baseHousePurchaserDto || this.baseHousePurchaserDto.size() <= 0) {
			this.baseHousePurchaserDto = new ArrayList<BaseHousePurchaserDto>();
		}
		return baseHousePurchaserDto;
	}
	public void setBaseHousePurchaserDto(List<BaseHousePurchaserDto> baseHousePurchaserDto) {
		this.baseHousePurchaserDto = baseHousePurchaserDto;
	}
	public List<BaseHousePropertypeopleDto> getBaseHousePropertypeopleDto() {
		if(null == this.baseHousePropertypeopleDto || this.baseHousePropertypeopleDto.size() <= 0) {
			this.baseHousePropertypeopleDto = new ArrayList<BaseHousePropertypeopleDto>();
		}
		return baseHousePropertypeopleDto;
	}
	public void setBaseHousePropertypeopleDto(List<BaseHousePropertypeopleDto> baseHousePropertypeopleDto) {
		this.baseHousePropertypeopleDto = baseHousePropertypeopleDto;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setOldHouseLoanAmount(Long value) {
		this.oldHouseLoanAmount = value;
	}	
	public Long getOldHouseLoanAmount() {
		return this.oldHouseLoanAmount;
	}
	public void setOldHouseLoanBalance(Long value) {
		this.oldHouseLoanBalance = value;
	}	
	public Long getOldHouseLoanBalance() {
		return this.oldHouseLoanBalance;
	}
	public void setHouseDealPrice(Long value) {
		this.houseDealPrice = value;
	}	
	public Long getHouseDealPrice() {
		return this.houseDealPrice;
	}
	public void setHouseDealDeposit(Long value) {
		this.houseDealDeposit = value;
	}	
	public Long getHouseDealDeposit() {
		return this.houseDealDeposit;
	}
	public void setHouseSuperviseAmount(Long value) {
		this.houseSuperviseAmount = value;
	}	
	public Long getHouseSuperviseAmount() {
		return this.houseSuperviseAmount;
	}
	public void setHouseLoanAmount(Long value) {
		this.houseLoanAmount = value;
	}	
	public Long getHouseLoanAmount() {
		return this.houseLoanAmount;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setIsFinish(java.lang.Integer value) {
		this.isFinish = value;
	}	
	public java.lang.Integer getIsFinish() {
		return this.isFinish;
	}
	public void setBussinessType(java.lang.Integer value) {
		this.bussinessType = value;
	}	
	public java.lang.Integer getBussinessType() {
		return this.bussinessType;
	}
	public void setMortgageIsBank(java.lang.Integer value) {
		this.mortgageIsBank = value;
	}	
	public java.lang.Integer getMortgageIsBank() {
		return this.mortgageIsBank;
	}
	public void setMortgageAddress(java.lang.String value) {
		this.mortgageAddress = value;
	}	
	public java.lang.String getMortgageAddress() {
		return this.mortgageAddress;
	}
	public void setCustomerMgr(java.lang.String value) {
		this.customerMgr = value;
	}	
	public java.lang.String getCustomerMgr() {
		return this.customerMgr;
	}
	public void setCustomerMgrPhone(java.lang.String value) {
		this.customerMgrPhone = value;
	}	
	public java.lang.String getCustomerMgrPhone() {
		return this.customerMgrPhone;
	}
	public void setMortgageCycleStart(java.lang.String value) {
		this.mortgageCycleStart = value;
	}	
	public java.lang.String getMortgageCycleStart() {
		return this.mortgageCycleStart;
	}
	public void setMortgageCycleEnd(java.lang.String value) {
		this.mortgageCycleEnd = value;
	}	
	public java.lang.String getMortgageCycleEnd() {
		return this.mortgageCycleEnd;
	}
	public void setAmountLoan(Long value) {
		this.amountLoan = value;
	}	
	public Long getAmountLoan() {
		return this.amountLoan;
	}
	public void setAmountLoanSurplus(Long value) {
		this.amountLoanSurplus = value;
	}	
	public Long getAmountLoanSurplus() {
		return this.amountLoanSurplus;
	}
	public void setLendingCycleStart(java.lang.String value) {
		this.lendingCycleStart = value;
	}	
	public java.lang.String getLendingCycleStart() {
		return this.lendingCycleStart;
	}
	public void setLendingCycleEnd(java.lang.String value) {
		this.lendingCycleEnd = value;
	}	
	public java.lang.String getLendingCycleEnd() {
		return this.lendingCycleEnd;
	}
	public void setPaidType(java.lang.Integer value) {
		this.paidType = value;
	}	
	public java.lang.Integer getPaidType() {
		return this.paidType;
	}
	public void setMortgageBankId(java.lang.String value) {
		this.mortgageBankId = value;
	}	
	public java.lang.String getMortgageBankId() {
		return this.mortgageBankId;
	}
	public void setMortgageSubBankId(java.lang.String value) {
		this.mortgageSubBankId = value;
	}	
	public java.lang.String getMortgageSubBankId() {
		return this.mortgageSubBankId;
	}
	public void setMortgageBankName(java.lang.String value) {
		this.mortgageBankName = value;
	}	
	public java.lang.String getMortgageBankName() {
		return this.mortgageBankName;
	}
	public void setMortgageSubBankName(java.lang.String value) {
		this.mortgageSubBankName = value;
	}	
	public java.lang.String getMortgageSubBankName() {
		return this.mortgageSubBankName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("oldHouseLoanAmount",getOldHouseLoanAmount())
			.append("oldHouseLoanBalance",getOldHouseLoanBalance())
			.append("houseDealPrice",getHouseDealPrice())
			.append("houseDealDeposit",getHouseDealDeposit())
			.append("houseSuperviseAmount",getHouseSuperviseAmount())
			.append("houseLoanAmount",getHouseLoanAmount())
			.append("remark",getRemark())
			.append("isFinish",getIsFinish())
			.append("bussinessType",getBussinessType())
			.append("mortgageIsBank",getMortgageIsBank())
			.append("mortgageAddress",getMortgageAddress())
			.append("customerMgr",getCustomerMgr())
			.append("customerMgrPhone",getCustomerMgrPhone())
			.append("mortgageCycleStart",getMortgageCycleStart())
			.append("mortgageCycleEnd",getMortgageCycleEnd())
			.append("amountLoan",getAmountLoan())
			.append("amountLoanSurplus",getAmountLoanSurplus())
			.append("lendingCycleStart",getLendingCycleStart())
			.append("lendingCycleEnd",getLendingCycleEnd())
			.append("paidType",getPaidType())
			.append("mortgageBankId",getMortgageBankId())
			.append("mortgageSubBankId",getMortgageSubBankId())
			.append("mortgageBankName",getMortgageBankName())
			.append("mortgageSubBankName",getMortgageSubBankName())
			.toString();
	}
}

