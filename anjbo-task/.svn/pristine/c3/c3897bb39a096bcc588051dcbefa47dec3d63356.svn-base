/*
 *Project: anjbo-task
 *File: com.anjbo.task.third.IcbcQPDTask.java  <2017年11月2日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.task.third;

import com.anjbo.common.HttpUtil;
import com.anjbo.task.BaseTask;
import com.anjbo.utils.ConfigUtil;

/**
 * 工行数据同步
 * @Author KangLG 
 * @Date 2017年11月2日 下午3:31:26
 * @version 1.0
 */
public class IcbcQPDTask extends BaseTask {
	
	/**
	 * 当日流水同步
	 * @Author KangLG<2017年11月2日>
	 */
	public void runQPD(){
		logger.info("工行银企互联，QPD获取数据...");
		try{
			new HttpUtil().post(ConfigUtil.getStringValue("CREDIT_URL")+"/credit/third/api/icbc/qpd/sync", null);
		} catch (Exception e){
			logger.error("工行数据同步异常(QPD)...", e);
		}
	}
}
