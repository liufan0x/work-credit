/*
 *Project: anjbo-task
 *File: com.anjbo.task.user.DingtalkDeptSync.java  <2017年11月2日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.task.user;

import com.anjbo.common.HttpUtil;
import com.anjbo.task.BaseTask;
import com.anjbo.utils.ConfigUtil;

/**
 * 钉钉数据同步
 * @Author KangLG 
 * @Date 2017年11月2日 下午3:01:05
 * @version 1.0
 */
public class DingtalkSyncTask extends BaseTask {
	
	public void runDept(){
		logger.info("钉钉数据同步(部门)...");
		try{
			new HttpUtil().post(ConfigUtil.getStringValue("CREDIT_URL")+"/credit/user/dept/autoSyncDingtalkDept", null);
		} catch (Exception e){
			logger.error("钉钉数据同步异常(部门)...", e);
		}
	}
	
	public void runUser(){
		logger.info("钉钉数据同步(员工)...");
		try{
			new HttpUtil().post(ConfigUtil.getStringValue("CREDIT_URL")+"/credit/user/base/autoSyncDingtalkUser", null);
		} catch (Exception e){
			logger.error("钉钉数据同步异常(员工)...", e);
		}
	}
	
}
