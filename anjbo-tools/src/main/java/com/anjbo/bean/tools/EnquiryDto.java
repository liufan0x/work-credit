package com.anjbo.bean.tools;

import java.util.Date;
import java.util.List;

import com.anjbo.bean.BaseDto;

/**
 * 询价
 * 
 * @author Object
 * 
 * @date 2016-11-11 上午10:32:11
 */
public class EnquiryDto extends BaseDto {

	private int id;
	/** 询价人Uid **/
	private String uid;
	/** 城市Id **/
	private String cityId;
	/** 城市名称 **/
	private String cityName;
	/** 评估公司 **/
	private String type;
	/** 物业Id **/
	private String propertyId;
	/** 物业名称 **/
	private String propertyName;
	/** 楼栋Id **/
	private String buildingId;
	/** 楼栋名称 **/
	private String buildingName;
	/** 房间ID **/
	private String roomId;
	/** 房间名称 **/
	private String roomName;
	/** 楼层 **/
	private String currentFloor;
	/** 面积 **/
	private double area;
	/** 登记价格 **/
	private double registerPrice;
	/** 成交价格 **/
	private double dealPrice;
	/** 权利人 **/
	private String obligee;
	/** 是否消费贷(0：是,1：否) **/
	private int consumerloans;
	/** 购房期限(0:未满五年,1：满五年) **/
	private String range;
	/** 内部流水号 **/
	private String serialid;
	/** 是否获取净值/税费(0: 获取,1:不获取(默认 0)) **/
	private int isGetNetPriceTax;
	/** 禁用(0:可用,1：不可用) **/
	private int enable;
	/** 来源(0:app,1: 微信 ,2:PC询价 ,3:赎楼系统) **/
	private String source;
	/** 创建时间 **/
	private Date createtime;
	/** 询价结果 **/
	private List<EnquiryReportDto> enquiryReportDtos;
	
	private String district;//区域
	
	/** 登记日期 **/
	private String registerDate;
	
	/** 总楼层 **/
	private String totalFloor;
	/** 当前楼层 **/
	private String inTheFloor;
	/** 朝向 **/
	private String orientation;
	/** 装修状态 **/
	private String decorate;
	/** 梯户比 **/
	private String ladder;
	
	public String getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(String totalFloor) {
		this.totalFloor = totalFloor;
	}

	public String getInTheFloor() {
		return inTheFloor;
	}

	public void setInTheFloor(String inTheFloor) {
		this.inTheFloor = inTheFloor;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getDecorate() {
		return decorate;
	}

	public void setDecorate(String decorate) {
		this.decorate = decorate;
	}

	public String getLadder() {
		return ladder;
	}

	public void setLadder(String ladder) {
		this.ladder = ladder;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getCurrentFloor() {
		return currentFloor;
	}
	public void setCurrentFloor(String currentFloor) {
		this.currentFloor = currentFloor;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getRegisterPrice() {
		return registerPrice;
	}
	public void setRegisterPrice(double registerPrice) {
		this.registerPrice = registerPrice;
	}
	public double getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(double dealPrice) {
		this.dealPrice = dealPrice;
	}
	public String getObligee() {
		return obligee;
	}
	public void setObligee(String obligee) {
		this.obligee = obligee;
	}
	public int getConsumerloans() {
		return consumerloans;
	}
	public void setConsumerloans(int consumerloans) {
		this.consumerloans = consumerloans;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getSerialid() {
		return serialid;
	}
	public void setSerialid(String serialid) {
		this.serialid = serialid;
	}
	public int getIsGetNetPriceTax() {
		return isGetNetPriceTax;
	}
	public void setIsGetNetPriceTax(int isGetNetPriceTax) {
		this.isGetNetPriceTax = isGetNetPriceTax;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public List<EnquiryReportDto> getEnquiryReportDtos() {
		return enquiryReportDtos;
	}
	public void setEnquiryReportDtos(List<EnquiryReportDto> enquiryReportDtos) {
		this.enquiryReportDtos = enquiryReportDtos;
	}
	
}