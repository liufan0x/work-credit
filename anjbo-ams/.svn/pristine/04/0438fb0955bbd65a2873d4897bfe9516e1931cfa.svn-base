package com.anjbo.bean;

import com.anjbo.utils.VersionUtil;

/**
 * 基类
 * @ClassName: BaseDto 
 * @author limh limh@zxsf360.com
 * @date 2014-12-10 下午06:04:30
 */
public class BaseDto extends BasePager{
	/**标示id**/
	protected String sid;
	/**设备**/
	protected String device;
	/**设备id**/
	protected String deviceId;
	/**版本**/
	protected String version;
	
	public String getVersion() {
		if(version==null){
			version = new StringBuffer().append(VersionUtil.getStringValue("project"))
			.append("-").append(VersionUtil.getStringValue("version")).toString();
		}
		return version;
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
}
