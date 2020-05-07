package com.anjbo.bean.tools;

import java.util.Date;
/**
 * 宝安学位
 * @author Administrator
 *
 * @date 2017-8-31 上午10:24:50
 */
public class DegreeBADto extends DegreeLockRecordDto{
	private Integer id;
	/**行政区*/
	private String region;
	/**街道/直属*/
	private String streetDirectly;
	private String schoolName;
	private String userYear;
	private String houseProperty;
	private String houseAddress;
	private Date createTime;
	public DegreeBADto() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getStreetDirectly() {
		return streetDirectly;
	}
	public void setStreetDirectly(String streetDirectly) {
		this.streetDirectly = streetDirectly;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getUserYear() {
		return userYear;
	}
	public void setUserYear(String userYear) {
		this.userYear = userYear;
	}
	public String getHouseProperty() {
		return houseProperty;
	}
	public void setHouseProperty(String houseProperty) {
		this.houseProperty = houseProperty;
	}
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
