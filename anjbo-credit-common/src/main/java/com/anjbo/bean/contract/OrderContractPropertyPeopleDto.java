package com.anjbo.bean.contract;

/**
 * 共同借款人信息
 * @author liuf
 *
 */
public class OrderContractPropertyPeopleDto{

	private static final long serialVersionUID = 1L;
	
	/**订单编号*/
	private String orderNo;
	/**产权人姓名*/
	private String propertyName;
	/**手机号码*/
	private String propertyPhoneNumber;
	/**证件号码*/
	private String propertyCardNumber;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyPhoneNumber() {
		return propertyPhoneNumber;
	}
	public void setPropertyPhoneNumber(String propertyPhoneNumber) {
		this.propertyPhoneNumber = propertyPhoneNumber;
	}
	public String getPropertyCardNumber() {
		return propertyCardNumber;
	}
	public void setPropertyCardNumber(String propertyCardNumber) {
		this.propertyCardNumber = propertyCardNumber;
	}
	public OrderContractPropertyPeopleDto() {
		super();
	}
	
}
