/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

import com.anjbo.bean.RoleDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:14
 * @version 1.0
 */
public interface RoleMapper extends BaseMapper<RoleDto>{
		
	/**
	 * 新增机构默认角色
	 * @Author KangLG<2017年11月13日>
	 * @param agency
	 * @param createUid
	 * @return
	 */
	int insertByAgency(int agency, String createUid);
	
}
