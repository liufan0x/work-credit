package com.anjbo.bean.product;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 抵押品入库
 * @author admin
 *
 */
public class CollateralDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNo;
	private String region;  //所在区域
	private String housePropertyType; //房产证类型
	private String houseName;  //房产名称
	private String housePropertyNumber; //房产证号
	private Date collateralTime;  //入库时间
	private String remark;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getHousePropertyType() {
		return housePropertyType;
	}
	public void setHousePropertyType(String housePropertyType) {
		this.housePropertyType = housePropertyType;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getHousePropertyNumber() {
		return housePropertyNumber;
	}
	public void setHousePropertyNumber(String housePropertyNumber) {
		this.housePropertyNumber = housePropertyNumber;
	}
	public Date getCollateralTime() {
		return collateralTime;
	}
	public void setCollateralTime(Date collateralTime) {
		this.collateralTime = collateralTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
