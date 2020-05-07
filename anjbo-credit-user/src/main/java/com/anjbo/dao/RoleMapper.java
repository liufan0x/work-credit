package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.user.RoleDto;

/**
 * 角色Dto
 * @author lic
 */
public interface RoleMapper extends BaseMapper<RoleDto, Integer> {

	RoleDto findRoleByRoleId(int roleId);

	List<RoleDto> selectRoleList(RoleDto roleDto);

	int selectRoleCount(RoleDto roleDto);
	int selectAdminId(@Param("agencyId")int agencyId);

	int updateRole(RoleDto roleDto);
	
	int insertRole(RoleDto roleDto);
	
	/**
	 * 新增机构默认角色
	 * @Author KangLG<2017年11月13日>
	 * @param agency
	 * @param createUid
	 * @return
	 */
	int insertByAgency(@Param("agencyId") int agency, @Param("createUid")String createUid);
}