package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.UserAuthorityDto;
import com.anjbo.bean.UserDto;


public interface AuditFirstMapper {
	
	List<Map<String, Object>> findbyFirst(Map<String, Object> pareamt);
	
	List<UserAuthorityDto> findByAuthority(String name); //查询权限
	List<UserDto> findByUser(Map<String, Object> pareamt); //查询用户
}
