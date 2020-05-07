/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

import com.anjbo.bean.ModelConfigDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:42:47
 * @version 1.0
 */
public interface ModelConfigMapper extends BaseMapper<ModelConfigDto>{
		int updateByFundId(ModelConfigDto modelConfigDto);
}
