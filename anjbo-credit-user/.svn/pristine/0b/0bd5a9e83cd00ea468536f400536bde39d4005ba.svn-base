package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.user.RoleDto;
import com.anjbo.dao.RoleAuthorityMapper;
import com.anjbo.dao.RoleMapper;
import com.anjbo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{	
	@Resource private RoleMapper roleMapper;
	@Resource private RoleAuthorityMapper roleAuthorityMapper;
	
	@Override
	public RoleDto findRoleByRoleId(int roleId) {
		return roleMapper.findRoleByRoleId(roleId);
	}
	
	@Override
	public List<RoleDto> selectRoleList(RoleDto roleDto) {
		return roleMapper.selectRoleList(roleDto);
	}
	
	@Override
	public int selectRoleCount(RoleDto roleDto) {
		return roleMapper.selectRoleCount(roleDto);
	}
	
	@Override
	public int selectAdminId(int agencyId){
		return roleMapper.selectAdminId(agencyId);
	}
	
	@Override
	public int updateRole(RoleDto roleDto) {
		return roleMapper.updateRole(roleDto);
	}
	
	@Override
	public int insertRole(RoleDto roleDto) {
		return roleMapper.insertRole(roleDto);
	}
	
	@Override
	public int insertByAgency(int agency, String createUid){
		int result = roleMapper.insertByAgency(agency, createUid);
		if(result > 0){
			roleAuthorityMapper.insertByAgency(agency);
		}
		return result;
	}
	
	
}
