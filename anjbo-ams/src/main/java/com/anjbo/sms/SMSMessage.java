package com.anjbo.sms;

import java.io.Serializable;
import java.util.Date;

public class SMSMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String mobile;
	
	private String ip;
	
	private String svrIp;
	
	private String smsContent;
	
	private String smsComeFrom;
	
	private Date today;

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSvrIp() {
		return svrIp;
	}

	public void setSvrIp(String svrIp) {
		this.svrIp = svrIp;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSmsComeFrom() {
		return smsComeFrom;
	}

	public void setSmsComeFrom(String smsComeFrom) {
		this.smsComeFrom = smsComeFrom;
	}

}
