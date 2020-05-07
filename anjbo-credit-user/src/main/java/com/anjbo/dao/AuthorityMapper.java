package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.user.AuthorityDto;

/**
 * 用户Dto
 * @author lic
 */
public interface AuthorityMapper {

	AuthorityDto selectAuthorityByProductId(AuthorityDto authorityDto);
	
	List<Map<String, Object>> selectUserAuthorityList(); 
	
	int insertUserAuthority(Map<String, Object> params);
	/**初始化机构测试员/管理员权限  (1看单权限配置7用户列表9角色列表27部门维护)*/
	int insertUserAuthorityAdmin(@Param("agentId")Integer agentId, @Param("uid")String uid, @Param("isAllPermi")boolean isAllPermi);
	int signUserAuthorityAdmin(@Param("uid")String uid);
	
	int updateUserAuthority(Map<String, Object> params);

	Map<String, Object> selectUserAuthority(String uid);

	int insertRoleAuthority(Map<String, Object> params);
	
	int updateRoleAuthority(Map<String, Object> params);

	Map<String, Object> selectRoleAuthority(int roleId);

	List<Map<String, Object>> selectResource();
	
	List<Map<String, Object>> selectAuthority();

	List<AuthorityDto> selectAuthorityDtoByAuthorityDto(
			AuthorityDto authorityDto);

	List<AuthorityDto> selectAuthorityByProcessIds(@Param(value="processIds")String processIds);
	
}