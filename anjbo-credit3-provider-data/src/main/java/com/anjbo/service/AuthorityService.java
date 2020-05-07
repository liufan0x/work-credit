/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.AuthorityDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
public interface AuthorityService extends BaseService<AuthorityDto>{

	List<AuthorityDto> selectAuthorityByProcessIds(String processIds);
	
}
