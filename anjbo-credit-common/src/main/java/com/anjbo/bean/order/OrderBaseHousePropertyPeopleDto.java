package com.anjbo.bean.order;

import com.anjbo.bean.BaseDto;
/**
 * 产权人信息
 * @author liuf
 *
 */
public class OrderBaseHousePropertyPeopleDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	/**产权人信息*/
	private Integer id;
	/**订单编号*/
	private String orderNo;
	/**产权人姓名*/
	private String propertyName;
	/**手机号码*/
	private String propertyPhoneNumber;
	/**婚姻状态*/
	private String propertyMarriageState;
	/**证件号码*/
	private String propertyCardNumber;
	/**证件类型*/
	private String propertyCardType;
	/**列表顺序*/
	private Integer sort;
	/**职位*/
	private String propertyPosition;
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
	public String getPropertyMarriageState() {
		return propertyMarriageState;
	}
	public void setPropertyMarriageState(String propertyMarriageState) {
		this.propertyMarriageState = propertyMarriageState;
	}
	public String getPropertyCardNumber() {
		return propertyCardNumber;
	}
	public void setPropertyCardNumber(String propertyCardNumber) {
		this.propertyCardNumber = propertyCardNumber;
	}
	public String getPropertyCardType() {
		return propertyCardType;
	}
	public void setPropertyCardType(String propertyCardType) {
		this.propertyCardType = propertyCardType;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public OrderBaseHousePropertyPeopleDto() {
		super();
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	public String getPropertyPosition() {
		return propertyPosition;
	}
	public void setPropertyPosition(String propertyPosition) {
		this.propertyPosition = propertyPosition;
	}
	
}
