/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.anjbo.bean.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.service.BaseService;


/**
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:15
 * @version 1.0
 */
public interface UserService extends BaseService<UserDto>{

	RespDataObject<UserDto> login(HttpServletRequest request, Map<String, String> params);
	
	UserDto findByUid(String uid);

	List<UserDto> searchByType(Map<String, Object> params);

	Map<String,Map<String, Integer>> selectUserCountGroupByDeptId(UserDto userDto);
	
	String selectUidByDeptList(List<Integer> listDeptIds);

	List<UserDto> selectAllUserDto();

	int updateUnbind(UserDto userDto);

	int updateToken(UserDto userDto);
	
}
