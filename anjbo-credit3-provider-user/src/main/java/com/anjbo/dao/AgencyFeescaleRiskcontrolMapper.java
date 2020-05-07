/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.AgencyFeescaleRiskcontrolDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:46
 * @version 1.0
 */
public interface AgencyFeescaleRiskcontrolMapper extends BaseMapper<AgencyFeescaleRiskcontrolDto>{
	
	/**
	 * 获取指定机构类型的阶段费率
	* @Title: findStageRate 
	* @param @param map
	* @param @return
	* @return Map<String,Object>
	 */
	public List<Map<String, Object>> findStageRate(Map<String, Object> map);
	
}
