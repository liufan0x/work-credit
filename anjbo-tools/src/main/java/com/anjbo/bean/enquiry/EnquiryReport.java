/**
 * 
 */
package com.anjbo.bean.enquiry;

/**
 * @author Kevin Chang
 * 
 */
public class EnquiryReport {
	private int id;
	private String referID;// 询价单流水号
	private int enquiryid;// 询价id外键
	private String referTime;// 询价时间
	private String replyTime;// 回复时间
	private String pgsqID;// 询价公司内部流水号
	private String projectName;// 物业名称(必填项，字符型长度100)
	private String buildingName;// 栋号（必填项，字符型长度50）
	private String houseName;// 房号（必填项，字符型长度50）
	private double buildingArea;// 建筑面积（必填项，2为小数）

	private double unitPrice;// 评估单价
	private double totalPrice;// 评估总价
	private double tax;// 预计税费

	private double netPrice;// 评估净值
	private double sffive; // 上浮5%
	private double sften;// 上浮10%
	private String status;// 评估单状态
	private String specialMessage;// 特别提示信息
	private String companyId;//公司id
	private String company;// 提供评估信息公司
	private String bankId;// 银行id
	private String bankName;// 银行名称
	// 询价付费（0免费 1优惠券 2付费）
	private int pay;

	private int assessFlag;// 是否已申请评估(1:已申请,0:未申请)
	private double transactionPrice;// 成交价格
	
	private int maxLoanPrice;//最高可贷=评估净值*0.7/10000（单位：元）

	public int getMaxLoanPrice() {
		return maxLoanPrice;
	}

	public void setMaxLoanPrice(int maxLoanPrice) {
		this.maxLoanPrice = maxLoanPrice;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public String getReferID() {
		return referID;
	}

	public void setReferID(String referID) {
		this.referID = referID;
	}

	public String getReferTime() {
		return referTime;
	}

	public void setReferTime(String referTime) {
		this.referTime = referTime;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getPgsqID() {
		return pgsqID;
	}

	public void setPgsqID(String pgsqID) {
		this.pgsqID = pgsqID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSpecialMessage() {
		return specialMessage;
	}

	public void setSpecialMessage(String specialMessage) {
		this.specialMessage = specialMessage;
	}

	public int getEnquiryid() {
		return enquiryid;
	}

	public void setEnquiryid(int enquiryid) {
		this.enquiryid = enquiryid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public double getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(double buildingArea) {
		this.buildingArea = buildingArea;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}

	public double getSffive() {
		return sffive;
	}

	public void setSffive(double sffive) {
		this.sffive = sffive;
	}

	public double getSften() {
		return sften;
	}

	public void setSften(double sften) {
		this.sften = sften;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssessFlag() {
		return assessFlag;
	}

	public void setAssessFlag(int assessFlag) {
		this.assessFlag = assessFlag;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public double getTransactionPrice() {
		return transactionPrice;
	}

	public void setTransactionPrice(double transactionPrice) {
		this.transactionPrice = transactionPrice;
	}

}
