package com.anjbo.bean.tools;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 询价结果
 * 
 * @author Object
 * 
 * @date 2016-11-11 上午11:13:03
 */
public class EnquiryReportDto extends BaseDto {

	private int id;
	/** 询价Id **/
	private int enquiryId;
	/** 物业名称 **/
	private String propertyName;
	/** 楼栋名称 **/
	private String buildingName;
	/** 房间名称 **/
	private String roomName;
	/** 建筑面积 **/
	private double area;
	/** 评估单价 **/
	private double unitPrice;
	/** 评估总价 **/
	private double totalPrice;
	/** 税费 **/
	private double tax;
	/** 评估净值 **/
	private double netPrice;
	/** 上浮5% **/
	private double sffive;
	/** 上浮10% **/
	private double sften;
	/** 评估公司名称 **/
	private String company;
	/** 创建时间 **/
	private Date createTime;
	/** 最后修改时间 **/
	private Date updateTime;
	/** 接口返回信息 **/
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnquiryId() {
		return enquiryId;
	}

	public void setEnquiryId(int enquiryId) {
		this.enquiryId = enquiryId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}


	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
