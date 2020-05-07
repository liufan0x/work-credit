/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.RoleAuthorityDto;
import com.anjbo.dao.RoleAuthorityMapper;
import com.anjbo.service.RoleAuthorityService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 12:25:15
 * @version 1.0
 */
@Service
public class RoleAuthorityServiceImpl extends BaseServiceImpl<RoleAuthorityDto>  implements RoleAuthorityService {
	@Autowired private RoleAuthorityMapper roleAuthorityMapper;
	
	@Override
	public int insertByAgency(int agency) {
		return roleAuthorityMapper.insertByAgency(agency);
	}
	
}
