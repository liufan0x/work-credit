package com.anjbo.service.mort.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.mort.UserMapper;
import com.anjbo.service.mort.UserService;

/**
 * 用户信息
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午03:16:49
 */

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public int selectGoodNopay(String uid, int goodType) {
		return userMapper.selectGoodNopay(uid, String.valueOf(goodType));
	}
	
}
