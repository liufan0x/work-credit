package com.anjbo.bean.contract;

import com.anjbo.bean.BaseDto;
/**
 * 合同共同借款人信息
 * @author liuf
 *
 */
public class ContractCustomerBorrowerDto extends BaseDto{

	private static final long serialVersionUID = 1L;
	
	/**姓名*/
	private String borrowerName;
	/**手机号码*/
	private String borrowerPhone;
	/**证件号码*/
	private String borrowerCardNumber;
	/**通讯地址*/
	private String postalAddress;
	/**邮箱*/
	private String emailAddress;
	public ContractCustomerBorrowerDto() {
		super();
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
