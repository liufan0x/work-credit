/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service;

import com.anjbo.bean.RoleDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:14
 * @version 1.0
 */
public interface RoleService extends BaseService<RoleDto>{
	
	public int insertByAgency(int agency, String createUid);
	
}
