package com.anjbo.bean.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.anjbo.bean.BaseDto;

/**
 * 借款信息
 * 
 * @author liuf
 *
 */
public class OrderBaseBorrowDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	/**关联订单号*/
	private String relationOrderNo;
	/** 借款信息 */
	private Integer id;
	/** 订单编号 */
	private String orderNo;
	/** 合作机构名称 */
	private String agencyName;
	/** 合作机构类型 */
	private String agencyTypeName;
	/** 渠道经理uid */
	private String channelManagerUid;
	/** 受理员uid */
	private String acceptMemberUid;
	/** 公证员uid */
	private String notarialUid;
	/** 面签员uid */
	private String facesignUid;
	/** 要件管理员uid */
	private String elementUid;
	/** 公证员名称 */
	private String notarialName;
	/** 面签员名称 */
	private String facesignName;
	/** 要件管理员名称 */
	private String elementName;
	/** 指派返款专员 */
	private String foreclosureMemberUid;
	private String foreclosureMemberName;
	/** 分公司（营业部） */
	private String branchCompany;
	/** 客户姓名 */
	private String borrowerName;
	/** 客户手机号码 */
	private String phoneNumber;
	/** 原贷款是否银行(1:是,2:否) */
	private Integer isOldLoanBank;
	/** 原贷款银行名称/原贷款地点 */
	private String oldLoanBankName;
	/** 原贷款支行 */
	private String oldLoanBankSubName;
	/** 原贷款银行ID */
	private Integer oldLoanBankNameId;
	/** 原贷款支行ID */
	private Integer oldLoanBankSubNameId;
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
	/** 新贷款银行ID */
	private Integer loanBankNameId;
	/** 新贷款支行ID */
	private Integer loanSubBankNameId;
	/** 新贷款银行经理电话 */
	private String loanBankNameManagerPhone;
	/** 新贷款银行经理 */
	private String loanBankNameManager;
	/** 借款期限 */
	private Integer borrowingDays;
	/** 借款金额 */
	private Double loanAmount;
	/** 费率 */
	private Double rate;
	/** 逾期费率 */
	private Double overdueRate;
	/** 收费金额 */
	private Double chargeMoney;
	/** 关外手续费 */
	private Double customsPoundage;
	/** 风控等级id */
	private Integer riskGradeId;
	/** 风控等级 */
	private String riskGrade;
	/** 其他费用 */
	private Double otherPoundage;
	/** 是否有返佣:0:初始化,1:是,2:否 */
	private Integer isRebate;
	/** 返佣金额 */
	private Double rebateMoney;
	/** 返佣银行Id */
	private Integer rebateBankId;
	/** 返佣支行Id */
	private Integer rebateBankSubId;
	/** 返佣银行 */
	private String rebateBank;
	/** 返佣支行 */
	private String rebateSubBank;
	/** 返佣银行户名 */
	private String rebateBankCardName;
	/** 返佣银行卡号 */
	private String rebateBankCardNum;
	/** 预计出款时间 */
	private Date financeOutLoanTime;
	/** 预计出款时间字符串 */
	private String financeOutLoanTimeStr;
	/** 是否一次性回款:0:初始化,1:是,2:否(非交易类没有是否一次性回款) */
	private Integer isOnePay;
	private String receivableForTime; //回款时间
	/** 备注说明 */
	private String remark;
	/** 是否完成1:是,2:否 */
	private Integer isFinish;
	/** 是否是畅贷订单 */
	private Integer isChangLoan;
	/** 收费方式0按天1按段 */
	private Integer modeid;
	/**订单回款*/
	private List<OrderBaseReceivableForDto> orderReceivableForDto;
	/**关联业务订单*/
	private List<OrderBaseBorrowRelationDto> orderBaseBorrowRelationDto;
	/** 订单列表需要的属性 */

	/** 订单所属机构 **/
	private Integer agencyId;
	/** 城市Code **/
	private String cityCode;
	/** 城市名称 **/
	private String cityName;
	/** 产品编码 **/
	private String productCode;
	/** 产品名称 */
	private String productName;
	/** 合作机构Id **/
	private int cooperativeAgencyId;
	/** 合作机构名称 */
	private String cooperativeAgencyName;
	/** 渠道经理名称 **/
	private String channelManagerName;
	/** 受理员名称 **/
	private String acceptMemberName;
	/** 上一节点操作人uid **/
	private String previousHandlerUid;
	/** 上一节点操作人 **/
	private String previousHandler;
	/** 上一节点操作时间 **/
	private String previousHandleTime;
	/** 订单状态 **/
	private String state;
	/** 当前处理人Uid **/
	private String currentHandlerUid;
	/** 当前处理人 **/
	private String currentHandler;
	/** 流程Id **/
	private String processId;
	/** 订单来源 **/
	private String source;
	/** 费用支付方式 (1：费用前置  2：费用后置  ) */
	private int paymentMethod;
	/** 费用支付方式 */
	private String paymentMethodName;
	/** 服务费*/
	private Double serviceCharge;
	/** 公证面签状态 */
	/** true：已公证 false:未公证 */
	private boolean isNotarization;
	/** true:已面签 false ：未面签 */
	private boolean isFaceSign;
	
	//=====APP=====
	
	/**计划回款时间*/
	private Date payMentAmountDateOne;
	/**回款金额*/
	private Double payMentAmountOne;
	/**计划回款时间*/
	private Date payMentAmountDateTwo;
	/**回款金额*/
	private Double payMentAmountTwo;
	/** 客户类型 */
	private int customerType;
	private String customerTypeName;	
	/** 手续费*/
	private Double rateProcedure;
	/** 提前手续费*/
	private Double rateBefore;
	/** 下户费*/
	private Double feeUnder;
	/** 还款方式ENU(0默认兼容老数据1先息后本2等额本息),paidType*/
	private int paidType;
	private String paidTypeName;
	/** 抵押类型（0默认兼容老数据，1首次抵押2二次抵押）*/
	private int mortgageType;
	private String mortgageTypeName;
	
	/**1:先审批再面签，2:先面签再审批*/
	private int auditSort;
	/**前手抵押余额*/
	private Double forwardMortgageBalance;
	/**户籍省份id*/
	private Integer domicileProvinceId;
	/**户籍市id*/
	private Integer domicileCityId;
	/**户籍省份*/
	private String domicileProvince;
	/**户籍市*/
	private String domicileCity;
	
	private String auditSortName;
	
	//====APP====
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

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyTypeName() {
		return agencyTypeName;
	}

	public void setAgencyTypeName(String agencyTypeName) {
		this.agencyTypeName = agencyTypeName;
	}

	public String getChannelManagerUid() {
		return channelManagerUid;
	}

	public void setChannelManagerUid(String channelManagerUid) {
		this.channelManagerUid = channelManagerUid;
	}

	public String getAcceptMemberUid() {
		return acceptMemberUid;
	}

	public void setAcceptMemberUid(String acceptMemberUid) {
		this.acceptMemberUid = acceptMemberUid;
	}

	public String getNotarialUid() {
		return notarialUid;
	}

	public void setNotarialUid(String notarialUid) {
		this.notarialUid = notarialUid;
	}

	public String getFacesignUid() {
		return facesignUid;
	}

	public void setFacesignUid(String facesignUid) {
		this.facesignUid = facesignUid;
	}

	public String getElementUid() {
		return elementUid;
	}

	public void setElementUid(String elementUid) {
		this.elementUid = elementUid;
	}

	public String getBranchCompany() {
		return branchCompany;
	}

	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Integer getOldLoanBankNameId() {
		return oldLoanBankNameId;
	}

	public void setOldLoanBankNameId(Integer oldLoanBankNameId) {
		this.oldLoanBankNameId = oldLoanBankNameId;
	}

	public Integer getOldLoanBankSubNameId() {
		return oldLoanBankSubNameId;
	}

	public void setOldLoanBankSubNameId(Integer oldLoanBankSubNameId) {
		this.oldLoanBankSubNameId = oldLoanBankSubNameId;
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

	public Integer getLoanBankNameId() {
		return loanBankNameId;
	}

	public void setLoanBankNameId(Integer loanBankNameId) {
		this.loanBankNameId = loanBankNameId;
	}

	public Integer getLoanSubBankNameId() {
		return loanSubBankNameId;
	}

	public void setLoanSubBankNameId(Integer loanSubBankNameId) {
		this.loanSubBankNameId = loanSubBankNameId;
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

	public Integer getBorrowingDays() {
		return borrowingDays;
	}

	public void setBorrowingDays(Integer borrowingDays) {
		this.borrowingDays = borrowingDays;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
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

	public Double getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(Double chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public Double getCustomsPoundage() {
		return customsPoundage;
	}

	public void setCustomsPoundage(Double customsPoundage) {
		this.customsPoundage = customsPoundage;
	}

	public Integer getRiskGradeId() {
		return riskGradeId;
	}

	public void setRiskGradeId(Integer riskGradeId) {
		this.riskGradeId = riskGradeId;
	}

	public Double getOtherPoundage() {
		return otherPoundage;
	}

	public void setOtherPoundage(Double otherPoundage) {
		this.otherPoundage = otherPoundage;
	}

	public Integer getIsRebate() {
		return isRebate;
	}

	public void setIsRebate(Integer isRebate) {
		this.isRebate = isRebate;
	}

	public Double getRebateMoney() {
		return rebateMoney;
	}

	public void setRebateMoney(Double rebateMoney) {
		this.rebateMoney = rebateMoney;
	}

	public Integer getRebateBankId() {
		return rebateBankId;
	}

	public void setRebateBankId(Integer rebateBankId) {
		this.rebateBankId = rebateBankId;
	}

	public Integer getRebateBankSubId() {
		return rebateBankSubId;
	}

	public void setRebateBankSubId(Integer rebateBankSubId) {
		this.rebateBankSubId = rebateBankSubId;
	}

	public String getRebateBank() {
		return rebateBank;
	}

	public void setRebateBank(String rebateBank) {
		this.rebateBank = rebateBank;
	}

	public String getRebateSubBank() {
		return rebateSubBank;
	}

	public void setRebateSubBank(String rebateSubBank) {
		this.rebateSubBank = rebateSubBank;
	}

	public String getRebateBankCardName() {
		return rebateBankCardName;
	}

	public void setRebateBankCardName(String rebateBankCardName) {
		this.rebateBankCardName = rebateBankCardName;
	}

	public String getRebateBankCardNum() {
		return rebateBankCardNum;
	}

	public void setRebateBankCardNum(String rebateBankCardNum) {
		this.rebateBankCardNum = rebateBankCardNum;
	}

	public Integer getIsOnePay() {
		return isOnePay;
	}

	public void setIsOnePay(Integer isOnePay) {
		this.isOnePay = isOnePay;
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

	public OrderBaseBorrowDto() {
		super();
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCooperativeAgencyId() {
		return cooperativeAgencyId;
	}

	public void setCooperativeAgencyId(int cooperativeAgencyId) {
		this.cooperativeAgencyId = cooperativeAgencyId;
	}

	public String getChannelManagerName() {
		return channelManagerName;
	}

	public void setChannelManagerName(String channelManagerName) {
		this.channelManagerName = channelManagerName;
	}

	public String getAcceptMemberName() {
		return acceptMemberName;
	}

	public void setAcceptMemberName(String acceptMemberName) {
		this.acceptMemberName = acceptMemberName;
	}

	public String getPreviousHandler() {
		return previousHandler;
	}

	public void setPreviousHandler(String previousHandler) {
		this.previousHandler = previousHandler;
	}

	public String getPreviousHandleTime() {
		return previousHandleTime;
	}

	public void setPreviousHandleTime(String previousHandleTime) {
		this.previousHandleTime = previousHandleTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCurrentHandlerUid() {
		return currentHandlerUid;
	}

	public void setCurrentHandlerUid(String currentHandlerUid) {
		this.currentHandlerUid = currentHandlerUid;
	}

	public String getCurrentHandler() {
		return currentHandler;
	}

	public void setCurrentHandler(String currentHandler) {
		this.currentHandler = currentHandler;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public List<OrderBaseBorrowRelationDto> getOrderBaseBorrowRelationDto() {
		if(null==this.orderBaseBorrowRelationDto||this.orderBaseBorrowRelationDto.size()<=0){
			this.orderBaseBorrowRelationDto = new ArrayList<OrderBaseBorrowRelationDto>();
		}
		return orderBaseBorrowRelationDto;
	}

	public void setOrderBaseBorrowRelationDto(
			List<OrderBaseBorrowRelationDto> orderBaseBorrowRelationDto) {
		this.orderBaseBorrowRelationDto = orderBaseBorrowRelationDto;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getNotarialName() {
		return notarialName;
	}

	public void setNotarialName(String notarialName) {
		this.notarialName = notarialName;
	}

	public String getFacesignName() {
		return facesignName;
	}

	public void setFacesignName(String facesignName) {
		this.facesignName = facesignName;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getRiskGrade() {
		return riskGrade;
	}

	public void setRiskGrade(String riskGrade) {
		this.riskGrade = riskGrade;
	}

	public List<OrderBaseReceivableForDto> getOrderReceivableForDto() {
		if(null==this.orderReceivableForDto||this.orderReceivableForDto.size()<=0){
			this.orderReceivableForDto = new ArrayList<OrderBaseReceivableForDto>();
		}
		return orderReceivableForDto;
	}

	public void setOrderReceivableForDto(List<OrderBaseReceivableForDto> orderReceivableForDto) {
		this.orderReceivableForDto = orderReceivableForDto;
	}

	public String getPreviousHandlerUid() {
		return previousHandlerUid;
	}

	public void setPreviousHandlerUid(String previousHandlerUid) {
		this.previousHandlerUid = previousHandlerUid;
	}

	public String getCooperativeAgencyName() {
		return cooperativeAgencyName;
	}

	public void setCooperativeAgencyName(String cooperativeAgencyName) {
		this.cooperativeAgencyName = cooperativeAgencyName;
	}

	public boolean getIsNotarization() {
		return isNotarization;
	}

	public void setIsNotarization(boolean isNotarization) {
		this.isNotarization = isNotarization;
	}

	public boolean getIsFaceSign() {
		return isFaceSign;
	}

	public void setIsFaceSign(boolean isFaceSign) {
		this.isFaceSign = isFaceSign;
	}

	public Date getPayMentAmountDateOne() {
		return payMentAmountDateOne;
	}

	public void setPayMentAmountDateOne(Date payMentAmountDateOne) {
		this.payMentAmountDateOne = payMentAmountDateOne;
	}

	public Double getPayMentAmountOne() {
		return payMentAmountOne;
	}

	public void setPayMentAmountOne(Double payMentAmountOne) {
		this.payMentAmountOne = payMentAmountOne;
	}

	public Date getPayMentAmountDateTwo() {
		return payMentAmountDateTwo;
	}

	public void setPayMentAmountDateTwo(Date payMentAmountDateTwo) {
		this.payMentAmountDateTwo = payMentAmountDateTwo;
	}

	public Double getPayMentAmountTwo() {
		return payMentAmountTwo;
	}

	public void setPayMentAmountTwo(Double payMentAmountTwo) {
		this.payMentAmountTwo = payMentAmountTwo;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

	public Integer getIsChangLoan() {
		return isChangLoan;
	}

	public void setIsChangLoan(Integer isChangLoan) {
		this.isChangLoan = isChangLoan;
	}

	public Integer getModeid() {
		return modeid;
	}

	public void setModeid(Integer modeid) {
		this.modeid = modeid;
	}

	public String getForeclosureMemberUid() {
		return foreclosureMemberUid;
	}

	public void setForeclosureMemberUid(String foreclosureMemberUid) {
		this.foreclosureMemberUid = foreclosureMemberUid;
	}

	public String getForeclosureMemberName() {
		return foreclosureMemberName;
	}

	public void setForeclosureMemberName(String foreclosureMemberName) {
		this.foreclosureMemberName = foreclosureMemberName;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public String getRelationOrderNo() {
		return relationOrderNo;
	}

	public void setRelationOrderNo(String relationOrderNo) {
		this.relationOrderNo = relationOrderNo;
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

	public Double getRateProcedure() {
		return rateProcedure;
	}

	public void setRateProcedure(Double rateProcedure) {
		this.rateProcedure = rateProcedure;
	}

	public Double getRateBefore() {
		return rateBefore;
	}

	public void setRateBefore(Double rateBefore) {
		this.rateBefore = rateBefore;
	}

	public Double getFeeUnder() {
		return feeUnder;
	}

	public void setFeeUnder(Double feeUnder) {
		this.feeUnder = feeUnder;
	}

	public int getPaidType() {
		return paidType;
	}

	public void setPaidType(int paidType) {
		this.paidType = paidType;
	}

	public int getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(int mortgageType) {
		this.mortgageType = mortgageType;
	}

	public String getReceivableForTime() {
		return receivableForTime;
	}

	public void setReceivableForTime(String receivableForTime) {
		this.receivableForTime = receivableForTime;
	}

	public String getMortgageTypeName() {
		switch (mortgageType) {
		case 1:		
			mortgageTypeName = "首次抵押";
			break;	
		case 2:		
			mortgageTypeName = "二次抵押";
			break;	
		default: 
			break;
		}
		return mortgageTypeName;
	}

	public void setMortgageTypeName(String mortgageTypeName) {
		this.mortgageTypeName = mortgageTypeName;
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
	
	public int getAuditSort() {
		return auditSort;
	}

	public void setAuditSort(int auditSort) {
		this.auditSort = auditSort;
	}

	public String getAuditSortName() {
		switch (auditSort) {
			case 1:		
				auditSortName = "先审批再面签";
				break;	
			case 2:		
				auditSortName = "先面签再审批";
				break;	
			default: 
				break;
		}
		return auditSortName;
	}

	public void setAuditSortName(String auditSortName) {
		this.auditSortName = auditSortName;
	}

	public Date getFinanceOutLoanTime() {
		return financeOutLoanTime;
	}

	public void setFinanceOutLoanTime(Date financeOutLoanTime) {
		this.financeOutLoanTime = financeOutLoanTime;
	}

	public String getFinanceOutLoanTimeStr() {
		if(null!=this.financeOutLoanTime){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.financeOutLoanTimeStr = format.format(this.financeOutLoanTime);
		}
		return financeOutLoanTimeStr;
	}

	public void setFinanceOutLoanTimeStr(String financeOutLoanTimeStr) {
		this.financeOutLoanTimeStr = financeOutLoanTimeStr;
	}

	public Double getForwardMortgageBalance() {
		return forwardMortgageBalance;
	}

	public void setForwardMortgageBalance(Double forwardMortgageBalance) {
		this.forwardMortgageBalance = forwardMortgageBalance;
	}

	public Integer getDomicileProvinceId() {
		return domicileProvinceId;
	}

	public void setDomicileProvinceId(Integer domicileProvinceId) {
		this.domicileProvinceId = domicileProvinceId;
	}

	public Integer getDomicileCityId() {
		return domicileCityId;
	}

	public void setDomicileCityId(Integer domicileCityId) {
		this.domicileCityId = domicileCityId;
	}

	public String getDomicileProvince() {
		return domicileProvince;
	}

	public void setDomicileProvince(String domicileProvince) {
		this.domicileProvince = domicileProvince;
	}

	public String getDomicileCity() {
		return domicileCity;
	}

	public void setDomicileCity(String domicileCity) {
		this.domicileCity = domicileCity;
	}

}
