package com.anjbo.bean.order;

import com.anjbo.bean.BaseDto;
/**
 * 买房人信息
 * @author liuf
 *
 */
public class OrderBaseHousePurchaserDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
	
	/**买房人信息*/
	private Integer id;
	/**订单编号*/
	private String orderNo;
	/**买房人姓名*/
	private String buyName;
	/**手机号码*/
	private String buyPhoneNumber;
	/**婚姻状态*/
	private String buyMarriageState;
	/**证件号码*/
	private String buyCardNumber;
	/**证件类型*/
	private String buyCardType;
	/**列表顺序*/
	private String sort;
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
	public String getBuyName() {
		return buyName;
	}
	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}
	public String getBuyPhoneNumber() {
		return buyPhoneNumber;
	}
	public void setBuyPhoneNumber(String buyPhoneNumber) {
		this.buyPhoneNumber = buyPhoneNumber;
	}
	public String getBuyMarriageState() {
		return buyMarriageState;
	}
	public void setBuyMarriageState(String buyMarriageState) {
		this.buyMarriageState = buyMarriageState;
	}
	public String getBuyCardNumber() {
		return buyCardNumber;
	}
	public void setBuyCardNumber(String buyCardNumber) {
		this.buyCardNumber = buyCardNumber;
	}
	public String getBuyCardType() {
		return buyCardType;
	}
	public void setBuyCardType(String buyCardType) {
		this.buyCardType = buyCardType;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public OrderBaseHousePurchaserDto() {
		super();
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	
}
