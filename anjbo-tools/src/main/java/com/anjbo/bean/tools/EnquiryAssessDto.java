package com.anjbo.bean.tools;

import java.util.Date;

import com.anjbo.bean.BaseDto;


/**
 * 评估
 * 
 * @author zhuzj
 * 
 */
public class EnquiryAssessDto extends BaseDto {
	private String id;
	private String userid; // 询价人id
	private String propertyName; // 物业名称
	private String bankName;// 按揭银行
	private String bankCode; // 银行编码
	private double dealAmount;// 成交价格
	private String userName; // 用户名称
	private String mobile;// 手机号
	private double loanAmount;// 贷款金额
	private int loanPercent; // 贷款成数
	private int status;// 0:评估待支付,1:正在评估,2:评估完成,3:正在出报告,4:已出报告,5评估失败,6申请报告失败
	private Date applyTime; // 申请时间
	private Date applyReportTime; // 申请报告时间
	private String applyTimeStr; // 申请时间
	private String applyReportTimeStr; // 申请报告时间
	private Date lastUpdateTime;
	private int enable;// 是否启用(1:启用,0:禁用)
	private int type; // 询价类型（1世联 2同致诚）
	private Date reportTime;// 出报告时间
	private Date createTime;
	private double assessAmount; // 成交总价
	private int enquiryId; // 询价id
	private String createtimeStr;
	private String serialId;// 询价流水号
	private double enquiryTotalPrice;// 询价总价
	private int isRead;// 是否已读(0:未读,1:已读)
	// 评估接口使用字段
	private String unitName;// 询价公司名称
	private String organUnitName;// 询价公司分部名称
	private String content;// 补充说明

	private String buildingNum; // 楼栋
	private String roomName; // 房号
	private double area; // 面积

	private String orderid;
	private double amount;
	private String discount;// 折扣
	private double sLAssessAmount;//世联评估总价
	private double dDAssessAmount;//戴德评估总价
	private double tzcNetPrice;//同致诚评估净值
	private double slNetPrice;//世联评估净值
	private double ddNetPrice;//戴德梁行评估净值
	private int tzcAssessStatus;//同致诚评估状态（0初始化  1已申请  2已评估）
	private String city;//城市
	private String district;//区域
	
	/** 是否是渠道经理查询记录，用于评估记录列表 */
	private boolean isEnquiryManager;
	
	/** 是否关闭（1,开启 2,关闭 ） */
	private int isClose;
	
	public int getIsClose() {
		return isClose;
	}

	public void setIsClose(int isClose) {
		this.isClose = isClose;
	}

	public boolean getIsEnquiryManager() {
		return isEnquiryManager;
	}

	public void setIsEnquiryManager(boolean isEnquiryManager) {
		this.isEnquiryManager = isEnquiryManager;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getTzcAssessStatus() {
		return tzcAssessStatus;
	}

	public void setTzcAssessStatus(int tzcAssessStatus) {
		this.tzcAssessStatus = tzcAssessStatus;
	}

	public double getTzcNetPrice() {
		return tzcNetPrice;
	}

	public void setTzcNetPrice(double tzcNetPrice) {
		this.tzcNetPrice = tzcNetPrice;
	}

	public double getSlNetPrice() {
		return slNetPrice;
	}

	public void setSlNetPrice(double slNetPrice) {
		this.slNetPrice = slNetPrice;
	}

	public double getDdNetPrice() {
		return ddNetPrice;
	}

	public void setDdNetPrice(double ddNetPrice) {
		this.ddNetPrice = ddNetPrice;
	}

	public double getsLAssessAmount() {
		return sLAssessAmount;
	}

	public void setsLAssessAmount(double sLAssessAmount) {
		this.sLAssessAmount = sLAssessAmount;
	}

	public double getdDAssessAmount() {
		return dDAssessAmount;
	}

	public void setdDAssessAmount(double dDAssessAmount) {
		this.dDAssessAmount = dDAssessAmount;
	}

	public String getApplyTimeStr() {
		return applyTimeStr;
	}

	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	public String getApplyReportTimeStr() {
		return applyReportTimeStr;
	}

	public void setApplyReportTimeStr(String applyReportTimeStr) {
		this.applyReportTimeStr = applyReportTimeStr;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getEnquiryId() {
		return enquiryId;
	}

	public void setEnquiryId(int enquiryId) {
		this.enquiryId = enquiryId;
	}

	public double getAssessAmount() {
		return assessAmount;
	}

	public void setAssessAmount(double assessAmount) {
		this.assessAmount = assessAmount;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public double getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(double dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public int getLoanPercent() {
		return loanPercent;
	}

	public void setLoanPercent(int loanPercent) {
		this.loanPercent = loanPercent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getApplyReportTime() {
		return applyReportTime;
	}

	public void setApplyReportTime(Date applyReportTime) {
		this.applyReportTime = applyReportTime;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOrganUnitName() {
		return organUnitName;
	}

	public void setOrganUnitName(String organUnitName) {
		this.organUnitName = organUnitName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public double getEnquiryTotalPrice() {
		return enquiryTotalPrice;
	}

	public void setEnquiryTotalPrice(double enquiryTotalPrice) {
		this.enquiryTotalPrice = enquiryTotalPrice;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
}
