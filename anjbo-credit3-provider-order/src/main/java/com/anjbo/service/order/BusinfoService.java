/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.order;

import com.anjbo.bean.order.BusinfoDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:47
 * @version 1.0
 */
public interface BusinfoService extends BaseService<BusinfoDto>{
	/**
	 * 校验面签影像资料
	 * @param orderNo
	 * @param productCode
	 * @return
	 */
	public boolean faceBusinfoCheck(String orderNo,String productCode,int auditSort);
	
	/**
	 * 校验公证资料
	 * @param orderNo
	 * @param productCode
	 * @return
	 */
	public boolean notarizationBusinfoCheck(String orderNo, String productCode); 
	 
}
