/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.UserDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:15
 * @version 1.0
 */
public interface UserMapper extends BaseMapper<UserDto>{

	List<Map<String,Object>> selectUserCountGroupByDeptId(UserDto userDto);
	
	List<String> selectUidByDeptList(List<Integer> list);

	List<UserDto> selectAllUserDto();
	
	List<UserDto> searchByUid(UserDto userDto);
	
	int updateUnbind(UserDto userDto);

	int updateToken(UserDto userDto);
}
