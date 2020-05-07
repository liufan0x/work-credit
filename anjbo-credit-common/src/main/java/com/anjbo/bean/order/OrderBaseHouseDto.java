package com.anjbo.bean.order;

import java.util.ArrayList;
import java.util.List;

import com.anjbo.bean.BaseDto;
/**
 * 房产交易信息
 * @author liuf
 *
 */
public class OrderBaseHouseDto extends BaseDto{

	private static final long serialVersionUID = 1L;
	
	/**房产交易信息*/
	private Integer id;
	/**订单编号*/
	private String orderNo;
	/**原房贷金额(万元)*/
	private Double oldHouseLoanAmount;
	/**原房贷余额(万元)*/
	private Double oldHouseLoanBalance;
	/**成交价格(万元)*/
	private Double houseDealPrice;
	/**成交定金(万元)*/
	private Double houseDealDeposit;
	/**资金监管金额(万元)*/
	private Double houseSuperviseAmount;
	/**贷款金额(万元)*/
	private Double houseLoanAmount;
	/**备注说明*/
	private String remark;
	/** 是否完成1:是,2:否 */
	private Integer isFinish;
	/** 房产信息*/
    private List<OrderBaseHousePropertyDto> orderBaseHousePropertyDto;
    /** 买房人信息*/
    private List<OrderBaseHousePurchaserDto> orderBaseHousePurchaserDto;
    /** 产权人信息 */
    private List<OrderBaseHousePropertyPeopleDto> orderBaseHousePropertyPeopleDto;
    
    /**附加信息*/
    /**省*/
	private String province;
	/**市*/
	private String city;
	/**市*/
	private String cityName;
	/** 原贷款是否银行(1:是,2:否) */
	private Integer isOldLoanBank;
	/** 原贷款银行名称/原贷款地点 */
	private String oldLoanBankName;
	/** 原贷款支行 */
	private String oldLoanBankSubName;
	/** 原贷款银行经理 */
	private String oldLoanBankManager;
	/** 原贷款银行经理电话 */
	private String oldLoanBankManagerPhone;
	/** 新贷款是否银行(1:是,2:否) */
	private Integer isLoanBank;
	/** 新贷款银行/新贷款地点 */
	private String loanBankName;
	/** 新贷款支行 */
	private String loanSubBankName;
	/** 新贷款银行经理电话 */
	private String loanBankNameManagerPhone;
	/** 新贷款银行经理 */
	private String loanBankNameManager;
	/** 业务类型：0未知1交易2非交易*/
	private int bussinessType;
	/** 业务类型：0未知1交易2非交易*/
	private String bussinessTypeName;
	
	/** 一抵贷信息 **/
	/** 抵押是否银行,1是2否,yesOrNo */	
	private int mortgageIsBank;
	private String mortgageIsBankName;
	/** 抵押地点 */	
	private java.lang.String mortgageAddress;
	/** 抵押银行 **/
	private Integer mortgageBankId;
	/** 抵押支行 **/
	private Integer mortgageSubBankId;
	/** 抵押银行 **/
	private String mortgageBankName;
	/** 抵押支行 **/
	private String mortgageSubBankName;
	/** 客户经理 */	
	private java.lang.String customerMgr;
	/** 客户经理电话 */	
	private java.lang.String customerMgrPhone;
	/** 抵押期限S */	
	private java.lang.String mortgageCycleStart;
	/** 抵押期限E */	
	private java.lang.String mortgageCycleEnd;
	/** 贷款金额(元) */	
	private Long amountLoan;
	/** 贷款余额(元) */	
	private Long amountLoanSurplus;
	/** 借款期限S */	
	private java.lang.String lendingCycleStart;
	/** 借款期限E */	
	private java.lang.String lendingCycleEnd;
	/** 还款方式ENU(0默认兼容老数据1先息后本2等额本息),paidType */	
	private int paidType;
	private String paidTypeName;
	
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
	public Double getOldHouseLoanAmount() {
		return oldHouseLoanAmount;
	}
	public void setOldHouseLoanAmount(Double oldHouseLoanAmount) {
		this.oldHouseLoanAmount = oldHouseLoanAmount;
	}
	public Double getOldHouseLoanBalance() {
		return oldHouseLoanBalance;
	}
	public void setOldHouseLoanBalance(Double oldHouseLoanBalance) {
		this.oldHouseLoanBalance = oldHouseLoanBalance;
	}
	public Double getHouseDealPrice() {
		return houseDealPrice;
	}
	public void setHouseDealPrice(Double houseDealPrice) {
		this.houseDealPrice = houseDealPrice;
	}
	public Double getHouseDealDeposit() {
		return houseDealDeposit;
	}
	public void setHouseDealDeposit(Double houseDealDeposit) {
		this.houseDealDeposit = houseDealDeposit;
	}
	public Double getHouseSuperviseAmount() {
		return houseSuperviseAmount;
	}
	public void setHouseSuperviseAmount(Double houseSuperviseAmount) {
		this.houseSuperviseAmount = houseSuperviseAmount;
	}
	public Double getHouseLoanAmount() {
		return houseLoanAmount;
	}
	public void setHouseLoanAmount(Double houseLoanAmount) {
		this.houseLoanAmount = houseLoanAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public OrderBaseHouseDto() {
		super();
	}
	public List<OrderBaseHousePropertyDto> getOrderBaseHousePropertyDto() {
		if(null==this.orderBaseHousePropertyDto||this.orderBaseHousePropertyDto.size()<=0){
			this.orderBaseHousePropertyDto = new ArrayList<OrderBaseHousePropertyDto>();
		}
		return orderBaseHousePropertyDto;
	}
	public void setOrderBaseHousePropertyDto(
			List<OrderBaseHousePropertyDto> orderBaseHousePropertyDto) {
		this.orderBaseHousePropertyDto = orderBaseHousePropertyDto;
	}
	public List<OrderBaseHousePurchaserDto> getOrderBaseHousePurchaserDto() {
		if(null==this.orderBaseHousePurchaserDto||this.orderBaseHousePurchaserDto.size()<=0){
			this.orderBaseHousePurchaserDto = new ArrayList<OrderBaseHousePurchaserDto>();
		}
		return orderBaseHousePurchaserDto;
	}
	public void setOrderBaseHousePurchaserDto(
			List<OrderBaseHousePurchaserDto> orderBaseHousePurchaserDto) {
		this.orderBaseHousePurchaserDto = orderBaseHousePurchaserDto;
	}
	public List<OrderBaseHousePropertyPeopleDto> getOrderBaseHousePropertyPeopleDto() {
		if(null==this.orderBaseHousePropertyPeopleDto||this.orderBaseHousePropertyPeopleDto.size()<=0){
			this.orderBaseHousePropertyPeopleDto = new ArrayList<OrderBaseHousePropertyPeopleDto>();
		}
		return orderBaseHousePropertyPeopleDto;
	}
	public void setOrderBaseHousePropertyPeopleDto(
			List<OrderBaseHousePropertyPeopleDto> orderBaseHousePropertyPeopleDto) {
		this.orderBaseHousePropertyPeopleDto = orderBaseHousePropertyPeopleDto;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getIsOldLoanBank() {
		return isOldLoanBank;
	}
	public void setIsOldLoanBank(Integer isOldLoanBank) {
		this.isOldLoanBank = isOldLoanBank;
	}
	public String getOldLoanBankName() {
		return oldLoanBankName;
	}
	public void setOldLoanBankName(String oldLoanBankName) {
		this.oldLoanBankName = oldLoanBankName;
	}
	public String getOldLoanBankSubName() {
		return oldLoanBankSubName;
	}
	public void setOldLoanBankSubName(String oldLoanBankSubName) {
		this.oldLoanBankSubName = oldLoanBankSubName;
	}
	public String getOldLoanBankManager() {
		return oldLoanBankManager;
	}
	public void setOldLoanBankManager(String oldLoanBankManager) {
		this.oldLoanBankManager = oldLoanBankManager;
	}
	public String getOldLoanBankManagerPhone() {
		return oldLoanBankManagerPhone;
	}
	public void setOldLoanBankManagerPhone(String oldLoanBankManagerPhone) {
		this.oldLoanBankManagerPhone = oldLoanBankManagerPhone;
	}
	public Integer getIsLoanBank() {
		return isLoanBank;
	}
	public void setIsLoanBank(Integer isLoanBank) {
		this.isLoanBank = isLoanBank;
	}
	public String getLoanBankName() {
		return loanBankName;
	}
	public void setLoanBankName(String loanBankName) {
		this.loanBankName = loanBankName;
	}
	public String getLoanSubBankName() {
		return loanSubBankName;
	}
	public void setLoanSubBankName(String loanSubBankName) {
		this.loanSubBankName = loanSubBankName;
	}
	public String getLoanBankNameManagerPhone() {
		return loanBankNameManagerPhone;
	}
	public void setLoanBankNameManagerPhone(String loanBankNameManagerPhone) {
		this.loanBankNameManagerPhone = loanBankNameManagerPhone;
	}
	public String getLoanBankNameManager() {
		return loanBankNameManager;
	}
	public void setLoanBankNameManager(String loanBankNameManager) {
		this.loanBankNameManager = loanBankNameManager;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	public int getBussinessType() {
		return bussinessType;
	}
	public void setBussinessType(int bussinessType) {
		this.bussinessType = bussinessType;
	}
	public String getBussinessTypeName() {
		switch (bussinessType) {
		case 1:
			bussinessTypeName="交易类";
			break;
		case 2:
			bussinessTypeName="非交易类";
			break;
		default:
			break;
		}
		return bussinessTypeName;
	}
	public void setBussinessTypeName(String bussinessTypeName) {
		this.bussinessTypeName = bussinessTypeName;
	}
	public int getMortgageIsBank() {
		return mortgageIsBank;
	}
	public void setMortgageIsBank(int mortgageIsBank) {
		this.mortgageIsBank = mortgageIsBank;
	}
	public String getMortgageIsBankName() {
		switch (mortgageIsBank) {
		case 1:
			mortgageIsBankName = "是";
			break;
		case 2:
			mortgageIsBankName = "否";
			break;
		default:
			break;
		}
		return mortgageIsBankName;
	}
	public void setMortgageIsBankName(String mortgageIsBankName) {
		this.mortgageIsBankName = mortgageIsBankName;
	}
	public java.lang.String getMortgageAddress() {
		return mortgageAddress;
	}
	public void setMortgageAddress(java.lang.String mortgageAddress) {
		this.mortgageAddress = mortgageAddress;
	}
	public java.lang.String getCustomerMgr() {
		return customerMgr;
	}
	public void setCustomerMgr(java.lang.String customerMgr) {
		this.customerMgr = customerMgr;
	}
	public java.lang.String getCustomerMgrPhone() {
		return customerMgrPhone;
	}
	public void setCustomerMgrPhone(java.lang.String customerMgrPhone) {
		this.customerMgrPhone = customerMgrPhone;
	}
	public java.lang.String getMortgageCycleStart() {
		return mortgageCycleStart;
	}
	public void setMortgageCycleStart(java.lang.String mortgageCycleStart) {
		this.mortgageCycleStart = mortgageCycleStart;
	}
	public java.lang.String getMortgageCycleEnd() {
		return mortgageCycleEnd;
	}
	public void setMortgageCycleEnd(java.lang.String mortgageCycleEnd) {
		this.mortgageCycleEnd = mortgageCycleEnd;
	}
	public Long getAmountLoan() {
		return amountLoan;
	}
	public void setAmountLoan(Long amountLoan) {
		this.amountLoan = amountLoan;
	}
	public Long getAmountLoanSurplus() {
		return amountLoanSurplus;
	}
	public void setAmountLoanSurplus(Long amountLoanSurplus) {
		this.amountLoanSurplus = amountLoanSurplus;
	}
	public java.lang.String getLendingCycleStart() {
		return lendingCycleStart;
	}
	public void setLendingCycleStart(java.lang.String lendingCycleStart) {
		this.lendingCycleStart = lendingCycleStart;
	}
	public java.lang.String getLendingCycleEnd() {
		return lendingCycleEnd;
	}
	public void setLendingCycleEnd(java.lang.String lendingCycleEnd) {
		this.lendingCycleEnd = lendingCycleEnd;
	}
	public int getPaidType() {
		return paidType;
	}
	public void setPaidType(int paidType) {
		this.paidType = paidType;
	}
	public String getPaidTypeName() {
		switch (paidType) {
		case 1:
			paidTypeName = "先息后本";
			break;
		case 2:
			paidTypeName = "等额本息";
			break;
		default:
			break;
		}
		return paidTypeName;
	}
	public void setPaidTypeName(String paidTypeName) {
		this.paidTypeName = paidTypeName;
	}
	public Integer getMortgageBankId() {
		return mortgageBankId;
	}
	public void setMortgageBankId(Integer mortgageBankId) {
		this.mortgageBankId = mortgageBankId;
	}
	public Integer getMortgageSubBankId() {
		return mortgageSubBankId;
	}
	public void setMortgageSubBankId(Integer mortgageSubBankId) {
		this.mortgageSubBankId = mortgageSubBankId;
	}
	public String getMortgageBankName() {
		return mortgageBankName;
	}
	public void setMortgageBankName(String mortgageBankName) {
		this.mortgageBankName = mortgageBankName;
	}
	public String getMortgageSubBankName() {
		return mortgageSubBankName;
	}
	public void setMortgageSubBankName(String mortgageSubBankName) {
		this.mortgageSubBankName = mortgageSubBankName;
	}

	
}
