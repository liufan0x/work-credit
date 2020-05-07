package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.user.AuthorityDto;

public interface AuthorityService {

	AuthorityDto selectAuthorityByProductId(AuthorityDto authorityDto);
	
	List<Map<String, Object>> selectUserAuthorityList();
	
	List<Map<String, Object>> selectAuthority();
	
	int insertUserAuthority(Map<String, Object> params);
	int insertUserAuthorityAdmin(Integer agentId, String uid, boolean isAllPermi);
	int signUserAuthorityAdmin(String uid);
	
	int updateUserAuthority(Map<String, Object> params);

	Map<String, Object> selectUserAuthority(String uid);

	int insertRoleAuthority(Map<String, Object> params);
	
	int updateRoleAuthority(Map<String, Object> params);

	Map<String, Object> selectRoleAuthority(int roleId);

	List<Map<String, Object>> selectResource();

	List<AuthorityDto> selectAuthorityDtoByAuthorityDto(
			AuthorityDto authorityDto);
	
	List<AuthorityDto> selectAuthorityByProcessIds(String processIds);
}
