/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.contract;

import java.util.Map;

import com.anjbo.bean.contract.ContractListDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 18:11:02
 * @version 1.0
 */
public interface ContractListService extends BaseService<ContractListDto>{
	
	/**
	 * 通过来源获取数据
	 * @param jsonData
	 * @return
	 */
	public String getDataBySource(String orderNo);
	

	/**
	 *字段配置查询
	 * */
	Map<String, Object> configuration(String orderNo);
}
