/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service;

import java.util.Map;

import com.anjbo.bean.AdministrationDivideDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-27 21:54:08
 * @version 1.0
 */
public interface AdministrationDivideService extends BaseService<AdministrationDivideDto>{
	Map<String,Object> find(Map<String,Object> map);
	
}

