package com.anjbo.bean.tools;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 预约取号
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:10:06
 */
public class Booking extends BaseDto{
	/**申办id**/
	private int id;
	/**申办类型**/
	private String bookingType;
	/**申办编号**/
	private String szItemNo;
	/**房地产所在区**/
	private String bookingSzAreaOid;
	/**办理登记点**/
	private String registrationAreaOid;
	/**预约办理开始时间**/
	private String startWorkDay;
	private String startWorkTime;
	/**预约办理结束时间**/
	private String endWorkDay;
	private String endWorkTime;
	/**创建时间**/
	private Date createTime;
	private String createTimeStr;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookingType() {
		return bookingType;
	}
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	public String getSzItemNo() {
		return szItemNo;
	}
	public void setSzItemNo(String szItemNo) {
		this.szItemNo = szItemNo;
	}
	public String getBookingSzAreaOid() {
		return bookingSzAreaOid;
	}
	public void setBookingSzAreaOid(String bookingSzAreaOid) {
		this.bookingSzAreaOid = bookingSzAreaOid;
	}
	public String getRegistrationAreaOid() {
		return registrationAreaOid;
	}
	public void setRegistrationAreaOid(String registrationAreaOid) {
		this.registrationAreaOid = registrationAreaOid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getStartWorkDay() {
		return startWorkDay;
	}
	public void setStartWorkDay(String startWorkDay) {
		this.startWorkDay = startWorkDay;
	}
	public String getStartWorkTime() {
		return startWorkTime;
	}
	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
	}
	public String getEndWorkDay() {
		return endWorkDay;
	}
	public void setEndWorkDay(String endWorkDay) {
		this.endWorkDay = endWorkDay;
	}
	public String getEndWorkTime() {
		return endWorkTime;
	}
	public void setEndWorkTime(String endWorkTime) {
		this.endWorkTime = endWorkTime;
	}

}
