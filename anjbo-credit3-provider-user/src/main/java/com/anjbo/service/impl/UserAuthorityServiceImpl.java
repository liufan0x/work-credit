/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.UserAuthorityDto;
import com.anjbo.dao.UserAuthorityMapper;
import com.anjbo.service.UserAuthorityService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 12:25:15
 * @version 1.0
 */
@Service
public class UserAuthorityServiceImpl extends BaseServiceImpl<UserAuthorityDto>  implements UserAuthorityService {
	@Autowired private UserAuthorityMapper userAuthorityMapper;

}
