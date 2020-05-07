/*
 *Project: anjbo-credit-common
 *File: com.anjbo.bean.dingtalk.DingtalkUser.java  <2017年10月17日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.dingtalk;

import java.io.Serializable;

/**
 * @Author KangLG 
 * @Date 2017年10月17日 上午10:07:15
 * @version 1.0
 */
public class DingtalkUser implements Serializable{
	/** @Author KangLG 2017年10月17日 上午10:07:30*/
	private static final long serialVersionUID = 1L;
	
	private String depId;
	private String uid;	
	private int isEnable;//0启用1未启用
	
	public DingtalkUser(){}
	public DingtalkUser(String depId, String uid){
		this.uid = uid;
		this.depId = depId;
	}
	
	/*
	 * getter - setter
	 */
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public int getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	
	

}
