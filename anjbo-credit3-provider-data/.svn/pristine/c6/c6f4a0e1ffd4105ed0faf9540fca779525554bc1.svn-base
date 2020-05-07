/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.AuthorityDto;
import com.anjbo.dao.AuthorityMapper;
import com.anjbo.service.AuthorityService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl<AuthorityDto>  implements AuthorityService {
	
	@Autowired private AuthorityMapper authorityMapper;
	
	public List<AuthorityDto> selectAuthorityByProcessIds(String processIds){
		return authorityMapper.selectAuthorityByProcessIds(processIds);
	}
	
}
