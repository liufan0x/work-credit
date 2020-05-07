package com.anjbo.bean.contract;

/**
 * 共同借款人信息
 * @author liuf
 *
 */
public class OrderContractCustomerBorrowerDto{

	private static final long serialVersionUID = 1L;
	
	/**订单编号*/
	private String orderNo;
	/**姓名*/
	private String borrowerName;
	/**手机号码*/
	private String borrowerPhone;
	/**证件类型*/
	private String borrowerCardType;
	/**证件号码*/
	private String borrowerCardNumber;
	/**通讯地址*/
	private String postalAddress;
	/**邮箱*/
	private String emailAddress;
	public OrderContractCustomerBorrowerDto() {
		super();
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getBorrowerPhone() {
		return borrowerPhone;
	}
	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}
	public String getBorrowerCardType() {
		return borrowerCardType;
	}
	public void setBorrowerCardType(String borrowerCardType) {
		this.borrowerCardType = borrowerCardType;
	}
	public String getBorrowerCardNumber() {
		return borrowerCardNumber;
	}
	public void setBorrowerCardNumber(String borrowerCardNumber) {
		this.borrowerCardNumber = borrowerCardNumber;
	}
	public String getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
}
