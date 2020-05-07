package com.anjbo.bean.product;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 结清原贷款
 * 
 * @author yis
 *
 */
public class ForeclosureDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 结清原贷款信息ID */
	private Integer id;

	/** 订单编号 */
	private String orderNo;

	/** 结清原贷款时间 */
	private Date foreclosureTime;
	private String foreclosureTimeStr;

	/** 是否在银行结清 （0:是 1：否） */
	private int  isBankEnd;

	/** 结清原贷款银行ID */
	private Integer foreclosureBankNameId;

	/** 结清原贷款银行名称 */
	private String foreclosureBankName;

	/** 结清原贷款支行ID */
	private Integer foreclosureBankSubNameId;

	/** 结清原贷款支行名称 */
	private String foreclosureBankSubName;
	
	/**结清原贷款地址*/
	private String foreclosureAddress;
	
	/**银行卡卡号*/
	private String bankNo;
	
	/**银行卡户主*/
	private String bankCardMaster;
	
	/**扣款凭证照片*/
	private String voucherImg;
	
	/**结清证明照片*/
	private String foreclosureImg;
	
	private String remark;
	
	private double loanAmount;   //结清原贷款金额/借款金额
	
	private String licenseRever;   //取证员
	private String licenseReverUid;  
	
	private Date licenseRevTime;   //取证时间
	private String licenseRevTimeStr;
	
	
	/** 取证银行ID */
	private Integer licenseRevBank;
	private String licenseRevBankName;  //取证银行
	/** 取证银行支行ID */
	private Integer licenseRevBankSub;
	private String licenseRevBankSubName;
	
	private String foreclosureMemberUid;  //还款专员(赎楼员)uid
	private String foreclosureMemberName;//还款专员(赎楼员)
	
	
	
	public String getForeclosureTimeStr() {
		return foreclosureTimeStr;
	}

	public void setForeclosureTimeStr(String foreclosureTimeStr) {
		this.foreclosureTimeStr = foreclosureTimeStr;
	}

	public String getLicenseRevTimeStr() {
		return licenseRevTimeStr;
	}

	public void setLicenseRevTimeStr(String licenseRevTimeStr) {
		this.licenseRevTimeStr = licenseRevTimeStr;
	}

	public String getLicenseRevBankSubName() {
		return licenseRevBankSubName;
	}

	public void setLicenseRevBankSubName(String licenseRevBankSubName) {
		this.licenseRevBankSubName = licenseRevBankSubName;
	}


	public String getLicenseReverUid() {
		return licenseReverUid;
	}

	public void setLicenseReverUid(String licenseReverUid) {
		this.licenseReverUid = licenseReverUid;
	}

	public Integer getLicenseRevBank() {
		return licenseRevBank;
	}

	public void setLicenseRevBank(Integer licenseRevBank) {
		this.licenseRevBank = licenseRevBank;
	}

	public Integer getLicenseRevBankSub() {
		return licenseRevBankSub;
	}

	public void setLicenseRevBankSub(Integer licenseRevBankSub) {
		this.licenseRevBankSub = licenseRevBankSub;
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

	public String getForeclosureAddress() {
		return foreclosureAddress;
	}

	public void setForeclosureAddress(String foreclosureAddress) {
		this.foreclosureAddress = foreclosureAddress;
	}

	public String getLicenseRever() {
		return licenseRever;
	}

	public void setLicenseRever(String licenseRever) {
		this.licenseRever = licenseRever;
	}

	public Date getLicenseRevTime() {
		return licenseRevTime;
	}

	public void setLicenseRevTime(Date licenseRevTime) {
		this.licenseRevTime = licenseRevTime;
	}


	public String getLicenseRevBankName() {
		return licenseRevBankName;
	}

	public void setLicenseRevBankName(String licenseRevBankName) {
		this.licenseRevBankName = licenseRevBankName;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
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
		this.orderNo = orderNo;
	}

	public Date getForeclosureTime() {
		return foreclosureTime;
	}

	public void setForeclosureTime(Date foreclosureTime) {
		this.foreclosureTime = foreclosureTime;
	}

	public int getIsBankEnd() {
		return isBankEnd;
	}

	public void setIsBankEnd(int isBankEnd) {
		this.isBankEnd = isBankEnd;
	}

	public Integer getForeclosureBankNameId() {
		return foreclosureBankNameId;
	}

	public void setForeclosureBankNameId(Integer foreclosureBankNameId) {
		this.foreclosureBankNameId = foreclosureBankNameId;
	}

	public String getForeclosureBankName() {
		return foreclosureBankName;
	}

	public void setForeclosureBankName(String foreclosureBankName) {
		this.foreclosureBankName = foreclosureBankName;
	}

	public Integer getForeclosureBankSubNameId() {
		return foreclosureBankSubNameId;
	}

	public void setForeclosureBankSubNameId(Integer foreclosureBankSubNameId) {
		this.foreclosureBankSubNameId = foreclosureBankSubNameId;
	}

	public String getForeclosureBankSubName() {
		return foreclosureBankSubName;
	}

	public void setForeclosureBankSubName(String foreclosureBankSubName) {
		this.foreclosureBankSubName = foreclosureBankSubName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankCardMaster() {
		return bankCardMaster;
	}

	public void setBankCardMaster(String bankCardMaster) {
		this.bankCardMaster = bankCardMaster;
	}

	public String getVoucherImg() {
		return voucherImg;
	}

	public void setVoucherImg(String voucherImg) {
		this.voucherImg = voucherImg;
	}

	public String getForeclosureImg() {
		return foreclosureImg;
	}

	public void setForeclosureImg(String foreclosureImg) {
		this.foreclosureImg = foreclosureImg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	



}
