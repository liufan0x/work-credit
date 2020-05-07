package com.anjbo.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 基类
 * @ClassName: BaseDto 
 * @author limh limh@zxsf360.com
 * @date 2014-12-10 下午06:04:30
 */
public class BaseDto extends BasePager{

	private static final long serialVersionUID = 1L;
	/**创建人**/
	protected String createUid;
	/**创建时间**/
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
	protected Date createTime;
	/**修改人**/
	protected String updateUid;
	/**修改时间**/
	protected Date updateTime;
	/**标示id**/
	protected String sid;
	/**设备**/
	protected String device;
	/**设备id**/
	protected String deviceId;
	/**版本**/
	protected String version;
	/**查询列表用是否查关联信息(1.不查)*/
	protected int isRelation;
	/** 下一个处理人*/
	private String nextHandleUid;
	
	public String getNextHandleUid() {
		return nextHandleUid;
	}
	public void setNextHandleUid(String nextHandleUid) {
		this.nextHandleUid = nextHandleUid;
	}
	public String getVersion() {
		return version;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(String updateUid) {
		this.updateUid = updateUid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getIsRelation() {
		return isRelation;
	}
	public void setIsRelation(int isRelation) {
		this.isRelation = isRelation;
	}
	
}
