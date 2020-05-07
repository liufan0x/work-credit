/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.RoleDto;
import com.anjbo.dao.RoleMapper;
import com.anjbo.service.RoleAuthorityService;
import com.anjbo.service.RoleService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:14
 * @version 1.0
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDto>  implements RoleService {
	@Autowired private RoleMapper roleMapper;
	
	@Resource private RoleAuthorityService roleAuthorityService;
	
	@Override
	public int insertByAgency(int agency, String createUid){
		int result = roleMapper.insertByAgency(agency, createUid);
		if(result > 0){
			roleAuthorityService.insertByAgency(agency);
		}
		return result;
	}
	
}
