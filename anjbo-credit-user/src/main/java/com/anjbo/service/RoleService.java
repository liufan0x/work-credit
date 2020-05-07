package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.user.RoleDto;

public interface RoleService {

	RoleDto findRoleByRoleId(int roleId);

	List<RoleDto> selectRoleList(RoleDto roleDto);

	int selectRoleCount(RoleDto roleDto);
	int selectAdminId(int agencyId);

	int updateRole(RoleDto roleDto);
	
	int insertRole(RoleDto roleDto);
	
	int insertByAgency(int agency, String createUid);
	
}
