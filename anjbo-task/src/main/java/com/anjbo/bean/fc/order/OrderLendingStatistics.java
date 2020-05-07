package com.anjbo.bean.fc.order;

import java.util.Date;

/**
 * 放款统计表
 * @author admin
 *
 */
public class OrderLendingStatistics {

	private int id;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 订单编号 */
	private String orderNo;
	/** 客户姓名 */
	private String customerName;
	/** 借款金额 */
	private double borrowingAmount;
	/** 借款期限 */
	private int borrowingDay;
	/** 城市编码 */
	private String cityCode;
	/** 城市名称 */
	private String cityName;
	/** 分公司营业部 */
	private String branchCompany;
	/** 渠道经理Uid */
	private String channelManagerUid;
	/** 渠道经理名称 */
	private String channelManagerName;
	/** 产品编码 */
	private String productCode;
	/** 产品名称 */
	private String productName;
	/** 放款时间 */
	private Date lendingTime;
	/** 放款时间字符串 */
	private String lendingTimeStr;
	/** 放款金额 */
	private double lendingAmount;
	/** 利息 */
	private Double interest;
	/** 罚息 */
	private Double fine;
	/** 服务费 */
	private Double serviceCharge;
	/** 创收 */
	private Double income;
	/** 正常返佣 */
	private double rebateMoney;
	/** 罚息返佣 */
	private double fRebateMoney;
	/** 关外手续费 */
	private Double customsPoundage;
	/** 其他金额 */
	private Double otherPoundage;
	/** 费率 */
	private Double rate;
	/** 逾期费率*/
	private Double overdueRate;
	/** 详情列表所需字段*/
	/** 订单来源 **/
	private String source;
	/** 合作机构Id **/
	private int cooperativeAgencyId;
	/** 合作机构名称 */
	private String cooperativeAgencyName;
	/** 受理员Uid **/
	private String acceptMemberUid;
	/** 受理员名称 **/
	private String acceptMemberName;
	/** 客户类型 */
	private int customerType;
	/** 客户类型名称  */
	private String customerTypeName;
	/** 业务标准*/
	private int business;
	/** 业务标准名称 */
	private String businessName;
	/** 预计计划回款时间 **/
	private String planPaymentTime;
	/** 实际回款时间 (多次回款取尾期时间)*/
	private String payMentAmountDate;
	/** 实际用款天数(回款时间-放款时间+1)*/
	private int actualLoanDay; 
	/** 回款情况（大于0提前还款，小于0逾期还款） */
	private int datediff;
	/** 提前还款天数*/
	private int  tqDatediff;
	/** 客户逾期天数*/
	private int  yqDatediff; 
	/** 收息日期*/
	private String interestTime;
	/** 罚息收取时间*/
	private String payTime;
	/** 返佣收取时间*/
	private String rebateTime;
	/** 原贷款银行*/
	private String oldLoanBankName;
	/** 原贷款支行*/
	private String oldLoanBankSubName;
	/** 新贷款银行*/
	private String loanBankName;
	/** 新贷款支行*/
	private String loanBankSubName;
	/** 原贷款银行支行*/
	private String oldLoanBankAndSub;
	/** 新贷款银行支行*/
	private String newLoanBankAndSub;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getBorrowingAmount() {
		return borrowingAmount;
	}
	public void setBorrowingAmount(double borrowingAmount) {
		this.borrowingAmount = borrowingAmount;
	}
	public int getBorrowingDay() {
		return borrowingDay;
	}
	public void setBorrowingDay(int borrowingDay) {
		this.borrowingDay = borrowingDay;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getBranchCompany() {
		return branchCompany;
	}
	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}
	public String getChannelManagerUid() {
		return channelManagerUid;
	}
	public void setChannelManagerUid(String channelManagerUid) {
		this.channelManagerUid = channelManagerUid;
	}
	public String getChannelManagerName() {
		return channelManagerName;
	}
	public void setChannelManagerName(String channelManagerName) {
		this.channelManagerName = channelManagerName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getLendingTimeStr() {
		return lendingTimeStr;
	}
	public void setLendingTimeStr(String lendingTimeStr) {
		this.lendingTimeStr = lendingTimeStr;
	}
	public double getLendingAmount() {
		return lendingAmount;
	}
	public void setLendingAmount(double lendingAmount) {
		this.lendingAmount = lendingAmount;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public Double getFine() {
		return fine;
	}
	public void setFine(Double fine) {
		this.fine = fine;
	}
	public Double getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	public Double getCustomsPoundage() {
		return customsPoundage;
	}
	public void setCustomsPoundage(Double customsPoundage) {
		this.customsPoundage = customsPoundage;
	}
	public Double getOtherPoundage() {
		return otherPoundage;
	}
	public void setOtherPoundage(Double otherPoundage) {
		this.otherPoundage = otherPoundage;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getCooperativeAgencyId() {
		return cooperativeAgencyId;
	}
	public void setCooperativeAgencyId(int cooperativeAgencyId) {
		this.cooperativeAgencyId = cooperativeAgencyId;
	}
	public String getCooperativeAgencyName() {
		return cooperativeAgencyName;
	}
	public void setCooperativeAgencyName(String cooperativeAgencyName) {
		this.cooperativeAgencyName = cooperativeAgencyName;
	}
	public String getAcceptMemberUid() {
		return acceptMemberUid;
	}
	public void setAcceptMemberUid(String acceptMemberUid) {
		this.acceptMemberUid = acceptMemberUid;
	}
	public String getAcceptMemberName() {
		return acceptMemberName;
	}
	public void setAcceptMemberName(String acceptMemberName) {
		this.acceptMemberName = acceptMemberName;
	}
	public int getCustomerType() {
		return customerType;
	}
	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}
	public String getCustomerTypeName() {
		switch (customerType) {
			case 1:		
				customerTypeName = "个人";
				break;	
			case 2:		
				customerTypeName = "小微企业";
				break;	
			default: 
				break;
		}
		return customerTypeName;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	public String getBusinessName() {
		switch (business) {
		case 1:		
			businessName = "标准";
			break;	
		case 2:		
			businessName = "非标准";
			break;	
		default: 
			break;
	}
	return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPlanPaymentTime() {
		return planPaymentTime;
	}
	public void setPlanPaymentTime(String planPaymentTime) {
		this.planPaymentTime = planPaymentTime;
	}
	public String getPayMentAmountDate() {
		return payMentAmountDate;
	}
	public void setPayMentAmountDate(String payMentAmountDate) {
		this.payMentAmountDate = payMentAmountDate;
	}
	public int getActualLoanDay() {
		return actualLoanDay;
	}
	public void setActualLoanDay(int actualLoanDay) {
		this.actualLoanDay = actualLoanDay;
	}
	public int getTqDatediff() {
		return tqDatediff;
	}
	public void setTqDatediff(int tqDatediff) {
		this.tqDatediff = tqDatediff;
	}
	public int getYqDatediff() {
		return yqDatediff;
	}
	public void setYqDatediff(int yqDatediff) {
		this.yqDatediff = yqDatediff;
	}
	public String getInterestTime() {
		return interestTime;
	}
	public void setInterestTime(String interestTime) {
		this.interestTime = interestTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getRebateTime() {
		return rebateTime;
	}
	public void setRebateTime(String rebateTime) {
		this.rebateTime = rebateTime;
	}
	public String getOldLoanBankAndSub() {
		return oldLoanBankAndSub;
	}
	public void setOldLoanBankAndSub(String oldLoanBankAndSub) {
		this.oldLoanBankAndSub = oldLoanBankAndSub;
	}
	public String getNewLoanBankAndSub() {
		return newLoanBankAndSub;
	}
	public void setNewLoanBankAndSub(String newLoanBankAndSub) {
		this.newLoanBankAndSub = newLoanBankAndSub;
	}
	public int getBusiness() {
		return business;
	}
	public void setBusiness(int business) {
		this.business = business;
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
	public String getLoanBankName() {
		return loanBankName;
	}
	public void setLoanBankName(String loanBankName) {
		this.loanBankName = loanBankName;
	}
	public String getLoanBankSubName() {
		return loanBankSubName;
	}
	public void setLoanBankSubName(String loanBankSubName) {
		this.loanBankSubName = loanBankSubName;
	}
	public int getDatediff() {
		return datediff;
	}
	public void setDatediff(int datediff) {
		this.datediff = datediff;
	}
	public double getRebateMoney() {
		return rebateMoney;
	}
	public void setRebateMoney(double rebateMoney) {
		this.rebateMoney = rebateMoney;
	}
	public double getfRebateMoney() {
		return fRebateMoney;
	}
	public void setfRebateMoney(double fRebateMoney) {
		this.fRebateMoney = fRebateMoney;
	}
	
}
