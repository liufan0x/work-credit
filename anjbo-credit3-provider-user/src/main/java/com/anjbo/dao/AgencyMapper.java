/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

import com.anjbo.bean.AgencyDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:45
 * @version 1.0
 */
public interface AgencyMapper extends BaseMapper<AgencyDto>{

	Integer selectMaxAgencyCode();
		
}
