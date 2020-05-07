package com.anjbo.bean.order;

import com.anjbo.bean.BaseDto;
/**
 * 共同借款人信息
 * @author liuf
 *
 */
public class OrderBaseCustomerBorrowerDto extends BaseDto{

	private static final long serialVersionUID = 1L;
	
	/**共同借款人信息*/
	private Integer id;
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
	/**婚姻状况*/
	private String borrowerMarriageState;
	/**配偶姓名*/
	private String borrowerWifeName;
	/**配偶证件类型*/
	private String borrowerWifeCardType;
	/**配偶证件号码*/
	private String borrowerWifeCardNumber;
	/**与借款人关系*/
	private String borrowerRelationship;
	/**是否产权人(是,否)*/
	private String borrowerIsPropertyProle;
	/** 是否完成1:是,2:否 */
	private Integer isFinish;
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
	public String getBorrowerMarriageState() {
		return borrowerMarriageState;
	}
	public void setBorrowerMarriageState(String borrowerMarriageState) {
		this.borrowerMarriageState = borrowerMarriageState;
	}
	public String getBorrowerWifeName() {
		return borrowerWifeName;
	}
	public void setBorrowerWifeName(String borrowerWifeName) {
		this.borrowerWifeName = borrowerWifeName;
	}
	public String getBorrowerWifeCardType() {
		return borrowerWifeCardType;
	}
	public void setBorrowerWifeCardType(String borrowerWifeCardType) {
		this.borrowerWifeCardType = borrowerWifeCardType;
	}
	public String getBorrowerWifeCardNumber() {
		return borrowerWifeCardNumber;
	}
	public void setBorrowerWifeCardNumber(String borrowerWifeCardNumber) {
		this.borrowerWifeCardNumber = borrowerWifeCardNumber;
	}
	public String getBorrowerRelationship() {
		return borrowerRelationship;
	}
	public void setBorrowerRelationship(String borrowerRelationship) {
		this.borrowerRelationship = borrowerRelationship;
	}
	public String getBorrowerIsPropertyProle() {
		return borrowerIsPropertyProle;
	}
	public void setBorrowerIsPropertyProle(String borrowerIsPropertyProle) {
		this.borrowerIsPropertyProle = borrowerIsPropertyProle;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public OrderBaseCustomerBorrowerDto() {
		super();
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	
}
