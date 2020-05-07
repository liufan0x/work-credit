/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service;

import java.util.Map;

import com.anjbo.bean.ProcessDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-03 09:49:33
 * @version 1.0
 */
public interface ProcessService extends BaseService<ProcessDto>{

	ProcessDto getNextProcess(Map<String, Object> params);
	
}
