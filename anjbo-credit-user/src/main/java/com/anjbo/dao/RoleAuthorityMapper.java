/*
 *Project: anjbo-credit-user
 *File: com.anjbo.dao.RoleAuthorityMapper.java  <2017年11月13日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

/**
 * @Author KangLG 
 * @Date 2017年11月13日 下午5:17:05
 * @version 1.0
 */
public interface RoleAuthorityMapper extends BaseMapper<Object, Integer> {

	/**
	 * 新增机构默认角色权限
	 * @Author KangLG<2017年11月13日>
	 * @param agency
	 * @return
	 */
	int insertByAgency(int agency);
	
}
