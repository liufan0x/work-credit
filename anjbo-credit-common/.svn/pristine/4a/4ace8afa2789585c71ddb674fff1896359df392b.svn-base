package com.anjbo.bean.contract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.anjbo.bean.order.OrderBaseCustomerBorrowerDto;
import com.anjbo.bean.order.OrderBaseHousePropertyPeopleDto;
/**
 * 业务申请表/借款合同/借款借据/软件服务合同/流程服务合同/征信查询授权书/婚姻状况声明/结清证明
 * @author liuf
 *
 */
public class LoanContractDto {
	
	/**订单编号*/
	private String orderNo;
	/**客户姓名*/
	private String customerName;
	/**证件号码*/
	private String customerCardNumber;
	/**性别*/
	private String customerSex;
	/** 客户手机号码 */
	private String phoneNumber;
	/**配偶姓名*/
	private String customerWifeName;
	/**配偶证件号码*/
	private String customerWifeCardNumber;
	/**配偶手机号*/
	private String customerWifePhone;
	/**通讯地址*/
	private String postalAddress;
	/**邮箱*/
	private String emailAddress;
	/**签署日期*/
	private Date dateOfSigning;
	private String dateOfSigningStr;
	/**签署城市*/
	private String cityOfSigning;
	private String cityNameOfSigning;
	/**签署区*/
	private String regionOfSigning;
	private String regionNameOfSigning;
	/** 借款金额（万元） **/
	private Double borrowingAmount;
	/** 借款期限（天）**/
	private Integer borrowingDays;
	/**偿还总金额*/
	private Double repayAmount;
	/** 产权人信息 */
    private List<OrderContractPropertyPeopleDto> propertyPeopleDto;
    /** 共同借款人*/
    private List<OrderContractCustomerBorrowerDto> customerBorrowerDto;
    
    /**出款方式*/
    /**银行卡户名*/
    private String bankCardMaster;
    /**银行卡账号*/
    private String bankNo;
    /**开户银行id*/
    private Integer bankNameId;
    /**支行id*/
    private Integer bankSubNameId;
    /**银行名称*/
    private String bankName;
    /**支行名称*/
    private String bankSubName;
    
    /**回款方式*/
    /**回款银行卡户名*/
    private String paymentBankCardName;
    /**回款银行卡账号*/
    private String paymentBankNumber;
    /**回款银行id*/
    private Integer paymentBankNameId;
    /**回款支行id*/
    private Integer paymentBankSubNameId;
    /**回款银行名称*/
    private String paymentBankName;
    /**回款支行名称*/
    private String paymentBankSubName;
    
    
    
    /**结清证明*/
    /**出具结清证明日期*/
    private String foreclosureTime;
    /**实际放款日期*/
    private String lendingTime;
    /**实际回款日期*/
    private String payMentAmountDate;
    /**合同年最后一位*/
    private String y;
    /**合同编号*/
    private String contractNo;
    
    /**信用信息查询授权书*/
    /**借款人姓名*/
    private String creditBorrowerName;
    /**借款人证件号码*/
    private String creditBorrowCardNumber;
	public LoanContractDto() {
		super();
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCardNumber() {
		return customerCardNumber;
	}
	public void setCustomerCardNumber(String customerCardNumber) {
		this.customerCardNumber = customerCardNumber;
	}
	public String getCustomerSex() {
		return customerSex;
	}
	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCustomerWifeName() {
		return customerWifeName;
	}
	public void setCustomerWifeName(String customerWifeName) {
		this.customerWifeName = customerWifeName;
	}
	public String getCustomerWifeCardNumber() {
		return customerWifeCardNumber;
	}
	public void setCustomerWifeCardNumber(String customerWifeCardNumber) {
		this.customerWifeCardNumber = customerWifeCardNumber;
	}
	public String getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	public Date getDateOfSigning() {
		return dateOfSigning;
	}
	public void setDateOfSigning(Date dateOfSigning) {
		this.dateOfSigning = dateOfSigning;
	}
	public String getDateOfSigningStr() {
		return dateOfSigningStr;
	}
	public void setDateOfSigningStr(String dateOfSigningStr) {
		this.dateOfSigningStr = dateOfSigningStr;
	}
	public String getCityOfSigning() {
		return cityOfSigning;
	}
	public void setCityOfSigning(String cityOfSigning) {
		this.cityOfSigning = cityOfSigning;
	}
	public String getCityNameOfSigning() {
		return cityNameOfSigning;
	}
	public void setCityNameOfSigning(String cityNameOfSigning) {
		this.cityNameOfSigning = cityNameOfSigning;
	}
	public String getRegionOfSigning() {
		return regionOfSigning;
	}
	public void setRegionOfSigning(String regionOfSigning) {
		this.regionOfSigning = regionOfSigning;
	}
	public String getRegionNameOfSigning() {
		return regionNameOfSigning;
	}
	public void setRegionNameOfSigning(String regionNameOfSigning) {
		this.regionNameOfSigning = regionNameOfSigning;
	}
	public Double getBorrowingAmount() {
		return borrowingAmount;
	}
	public void setBorrowingAmount(Double borrowingAmount) {
		this.borrowingAmount = borrowingAmount;
	}
	public List<OrderContractCustomerBorrowerDto> getCustomerBorrowerDto() {
		if(null==this.customerBorrowerDto||this.customerBorrowerDto.size()<=0){
			this.customerBorrowerDto = new ArrayList<OrderContractCustomerBorrowerDto>();
		}
		return customerBorrowerDto;
	}
	public void setCustomerBorrowerDto(
			List<OrderContractCustomerBorrowerDto> customerBorrowerDto) {
		this.customerBorrowerDto = customerBorrowerDto;
	}
	public List<OrderContractPropertyPeopleDto> getPropertyPeopleDto() {
		if(null==this.propertyPeopleDto||this.propertyPeopleDto.size()<=0){
			this.propertyPeopleDto = new ArrayList<OrderContractPropertyPeopleDto>();
		}
		return propertyPeopleDto;
	}
	public void setPropertyPeopleDto(
			List<OrderContractPropertyPeopleDto> propertyPeopleDto) {
		this.propertyPeopleDto = propertyPeopleDto;
	}
	public String getBankCardMaster() {
		return bankCardMaster;
	}
	public void setBankCardMaster(String bankCardMaster) {
		this.bankCardMaster = bankCardMaster;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public Integer getBankNameId() {
		return bankNameId;
	}
	public void setBankNameId(Integer bankNameId) {
		this.bankNameId = bankNameId;
	}
	public Integer getBankSubNameId() {
		return bankSubNameId;
	}
	public void setBankSubNameId(Integer bankSubNameId) {
		this.bankSubNameId = bankSubNameId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankSubName() {
		return bankSubName;
	}
	public void setBankSubName(String bankSubName) {
		this.bankSubName = bankSubName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(String lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getPayMentAmountDate() {
		return payMentAmountDate;
	}
	public void setPayMentAmountDate(String payMentAmountDate) {
		this.payMentAmountDate = payMentAmountDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getForeclosureTime() {
		return foreclosureTime;
	}
	public void setForeclosureTime(String foreclosureTime) {
		this.foreclosureTime = foreclosureTime;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Double getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}
	public String getPaymentBankCardName() {
		return paymentBankCardName;
	}
	public void setPaymentBankCardName(String paymentBankCardName) {
		this.paymentBankCardName = paymentBankCardName;
	}
	public String getPaymentBankNumber() {
		return paymentBankNumber;
	}
	public void setPaymentBankNumber(String paymentBankNumber) {
		this.paymentBankNumber = paymentBankNumber;
	}
	public Integer getPaymentBankNameId() {
		return paymentBankNameId;
	}
	public void setPaymentBankNameId(Integer paymentBankNameId) {
		this.paymentBankNameId = paymentBankNameId;
	}
	public Integer getPaymentBankSubNameId() {
		return paymentBankSubNameId;
	}
	public void setPaymentBankSubNameId(Integer paymentBankSubNameId) {
		this.paymentBankSubNameId = paymentBankSubNameId;
	}
	public String getPaymentBankName() {
		return paymentBankName;
	}
	public void setPaymentBankName(String paymentBankName) {
		this.paymentBankName = paymentBankName;
	}
	public String getPaymentBankSubName() {
		return paymentBankSubName;
	}
	public void setPaymentBankSubName(String paymentBankSubName) {
		this.paymentBankSubName = paymentBankSubName;
	}
	public String getCustomerWifePhone() {
		return customerWifePhone;
	}
	public void setCustomerWifePhone(String customerWifePhone) {
		this.customerWifePhone = customerWifePhone;
	}
	public String getY() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String yearStr = String.valueOf(year);
		y = yearStr.substring(yearStr.length()-1,yearStr.length());
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getCreditBorrowCardNumber() {
		return creditBorrowCardNumber;
	}
	public void setCreditBorrowCardNumber(String creditBorrowCardNumber) {
		this.creditBorrowCardNumber = creditBorrowCardNumber;
	}
	public String getCreditBorrowerName() {
		return creditBorrowerName;
	}
	public void setCreditBorrowerName(String creditBorrowerName) {
		this.creditBorrowerName = creditBorrowerName;
	}
	public Integer getBorrowingDays() {
		return borrowingDays;
	}
	public void setBorrowingDays(Integer borrowingDays) {
		this.borrowingDays = borrowingDays;
	}
    
}
