/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.AuthorityDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
public interface AuthorityMapper extends BaseMapper<AuthorityDto>{

	List<AuthorityDto> selectAuthorityByProcessIds(@Param(value="processIds") String processIds);
		
}
