package com.anjbo.task.fc.fdd;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.anjbo.service.fc.fdd.FddServer;
import com.anjbo.service.fc.order.OrderService;


public class FddTask {

	private Logger log = Logger.getLogger(getClass());
	@Resource
	private FddServer fddServer;
	/**
	 *每5分钟同步
	* @Title: run 
	* @param 
	* @return void
	 */
	public void run(){
		try{
			fddServer.run();
		} catch (Exception e){
			log.error("快鸽按揭从赎楼系统获取订单失败==>", e);
		}
	}
}
