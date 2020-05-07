package com.anjbo.bean.order;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.anjbo.bean.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 订单列表
 * @author lic
 * @date 2017-6-5
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderListDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	/** 订单列表 **/
	private int id;
	
	/** 订单类型 **/
	private int type;
	
	/** 订单编号 **/
	private String orderNo;
	
	/** 订单所属机构 **/
	private int agencyId;
	
	/** 城市Code **/
	private String cityCode;
	
	/** 城市名称 **/
	private String cityName;
	
	/** 分公司（营业部） **/
	private String branchCompany;
	
	/** 产品编码 **/
	private String productCode;
	
	/** 产品名称 **/
	private String productName;
	
	/** 合同编号 **/
	private String contractNo;
	
	/** 客户姓名 **/
	private String customerName;
	
	/** 借款金额（万元） **/
	private Double borrowingAmount;
	
	/** 借款期限（天） **/
	private int borrowingDay;
	
	/** 合作机构Id **/
	private int cooperativeAgencyId;
	
	/** 合作机构名称 **/
	private String cooperativeAgencyName;
	
	/** 渠道经理Uid **/
	private String channelManagerUid;
	
	/** 渠道经理名称 **/
	private String channelManagerName;
	
	/** 受理员Uid **/
	private String acceptMemberUid;
	
	/** 受理员名称 **/
	private String acceptMemberName;
	
	/** 预计计划回款时间 **/
	private String planPaymentTime;
	private String planPaymentTimeStart;
	private String planPaymentTimeEnd;
	private String planPaymentTimeOrders;
	
	/** 距离回款天数（天） **/
	private String distancePaymentDay;
	
	/** 上一节点操作人 **/
	private String previousHandler;
	
	/** 上一节点操作人Uid **/
	private String previousHandlerUid;
	
	/** 上一节点操作时间 **/
	private String previousHandleTime;
	
	/** 订单状态 **/
	private String state;
	
	/** 创建人Uid */
	private String createUid;
	
	/** 创建人 */
	private String createPerson;
	
	/** 当前处理人Uid **/
	private String currentHandlerUid;
	
	/** 当前处理人 **/
	private String currentHandler;
	
	/** 放款时间 **/
	private String lendingTime;	
	private String lendingTimeStart;	
	private String lendingTimeEnd;
	
	/** 创建时间 **/
	private Date createTime;
	
	/** 提单时间开始 **/
	private String createTimeStart;
	
	/** 提单时间结束 **/
	private String createTimeEnd;
	
	/** 流程Id **/
	private String processId;
	
	/** app列表展示第一个字段 **/
	private String appShowValue1;
	
	/** app列表展示第二个字段 **/
	private String appShowValue2;
	
	/** app列表展示第三个字段 **/
	private String appShowValue3;
	
	/** 搜索名称（客户姓名，受理员，渠道经理） **/
	private String searchName;
	
	/** 订单来源 **/
	private String source;
	
	/**公证员*/
	private String notarialUid;
	
	/**面签员*/
	private String facesignUid;
	
	/** 关联订单号 */
	private String relationOrderNo;
	
	/** 是否关联订单 (1:是,2:否)*/
	private int isRelationOrder;
	/**是否需要人脸识别(默认需要,1:需要,2:不需要)**/
	private int isFace;
	
	/** 客户类型 */
	private int customerType;
	
	private String customerTypeName;
	
	/** 预计出款时间 */
	private Date financeOutLoanTime;
	
	private String financeOutLoanTimeStr;
	
	private String receptionManagerUid;  //受理经理Uid
	
	private String loginUid;	// 当前登录人ID
	private String isUp;	// 非空已置顶
	
	/**客户手机号*/
	private String phoneNumber;
	
	/**1:先审批再面签，2:先面签再审批*/
	private int auditSort;
	/**审批顺序*/
	private String auditSortName;
	
	/**分配订单员*/
	private String distributionOrderUid;
	
	/**
	 * 是否陕国投
	 */
	private boolean sgt;
	
	public String getReceptionManagerUid() {
		return receptionManagerUid;
	}

	public void setReceptionManagerUid(String receptionManagerUid) {
		this.receptionManagerUid = receptionManagerUid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getBorrowingAmount() {
		return borrowingAmount;
	}

	public void setBorrowingAmount(Double borrowingAmount) {
		this.borrowingAmount = borrowingAmount;
	}

	public int getBorrowingDay() {
		return borrowingDay;
	}

	public void setBorrowingDay(int borrowingDay) {
		this.borrowingDay = borrowingDay;
	}

	public String getCooperativeAgencyName() {
		return cooperativeAgencyName;
	}

	public void setCooperativeAgencyName(String cooperativeAgencyName) {
		this.cooperativeAgencyName = cooperativeAgencyName;
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

	public String getPlanPaymentTime() {
		return planPaymentTime;
	}

	public void setPlanPaymentTime(String planPaymentTime) {
		this.planPaymentTime = planPaymentTime;
	}

	public String getDistancePaymentDay() {
		return distancePaymentDay;
	}

	public void setDistancePaymentDay(String distancePaymentDay) {
		this.distancePaymentDay = distancePaymentDay;
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

	public String getAppShowValue1() {
		return appShowValue1;
	}

	public void setAppShowValue1(String appShowValue1) {
		this.appShowValue1 = appShowValue1;
	}

	public String getAppShowValue2() {
		return appShowValue2;
	}

	public void setAppShowValue2(String appShowValue2) {
		this.appShowValue2 = appShowValue2;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getCooperativeAgencyId() {
		return cooperativeAgencyId;
	}

	public void setCooperativeAgencyId(int cooperativeAgencyId) {
		this.cooperativeAgencyId = cooperativeAgencyId;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBranchCompany() {
		return branchCompany;
	}

	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}

	public String getLendingTimeStart() {
		return lendingTimeStart;
	}

	public void setLendingTimeStart(String lendingTimeStart) {
		this.lendingTimeStart = lendingTimeStart;
	}

	public String getLendingTimeEnd() {
		return lendingTimeEnd;
	}

	public void setLendingTimeEnd(String lendingTimeEnd) {
		this.lendingTimeEnd = lendingTimeEnd;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getRelationOrderNo() {
		return relationOrderNo;
	}

	public void setRelationOrderNo(String relationOrderNo) {
		this.relationOrderNo = relationOrderNo;
	}

	public String getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(String lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getPreviousHandlerUid() {
		return previousHandlerUid;
	}

	public void setPreviousHandlerUid(String previousHandlerUid) {
		this.previousHandlerUid = previousHandlerUid;
	}

	public String getFacesignUid() {
		return facesignUid;
	}

	public void setFacesignUid(String facesignUid) {
		this.facesignUid = facesignUid;
	}

	public String getNotarialUid() {
		return notarialUid;
	}

	public void setNotarialUid(String notarialUid) {
		this.notarialUid = notarialUid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public int getIsFace() {
		return isFace;
	}

	public void setIsFace(int isFace) {
		this.isFace = isFace;
	}

	public String getPlanPaymentTimeStart() {
		return planPaymentTimeStart;
	}

	public void setPlanPaymentTimeStart(String planPaymentTimeStart) {
		this.planPaymentTimeStart = planPaymentTimeStart;
	}

	public String getPlanPaymentTimeEnd() {
		return planPaymentTimeEnd;
	}

	public void setPlanPaymentTimeEnd(String planPaymentTimeEnd) {
		this.planPaymentTimeEnd = planPaymentTimeEnd;
	}

	public String getPlanPaymentTimeOrders() {
		return planPaymentTimeOrders;
	}

	public void setPlanPaymentTimeOrders(String planPaymentTimeOrders) {
		this.planPaymentTimeOrders = planPaymentTimeOrders;
	}

	public String getAppShowValue3() {
		return appShowValue3;
	}

	public void setAppShowValue3(String appShowValue3) {
		this.appShowValue3 = appShowValue3;
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

	public int getIsRelationOrder() {
		return isRelationOrder;
	}

	public void setIsRelationOrder(int isRelationOrder) {
		this.isRelationOrder = isRelationOrder;
	}

	public String getLoginUid() {
		return loginUid;
	}

	public void setLoginUid(String loginUid) {
		this.loginUid = loginUid;
	}

	public String getIsUp() {
		return isUp;
	}

	public void setIsUp(String isUp) {
		this.isUp = isUp;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getDistributionOrderUid() {
		return distributionOrderUid;
	}

	public void setDistributionOrderUid(String distributionOrderUid) {
		this.distributionOrderUid = distributionOrderUid;
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

	public boolean isSgt() {
		return sgt;
	}

	public void setSgt(boolean sgt) {
		this.sgt = sgt;
	}

	
	
	
}
