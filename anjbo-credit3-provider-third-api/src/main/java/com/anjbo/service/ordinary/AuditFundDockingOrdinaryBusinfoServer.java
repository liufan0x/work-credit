/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.ordinary;

import java.util.List;
import java.util.Map;


/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-03 19:44:34
 * @version 1.0
 */
public interface AuditFundDockingOrdinaryBusinfoServer{

	public Map<String, Object> getOrdinaryBusinfoType(Map<String, Object> map);
	
	public int deleteImg(Map<String, Object> map);

	List<Map<String, Object>> selectListAll(Map<String, Object> map);

	public void insretImg(Map<String, Object> map);

}
