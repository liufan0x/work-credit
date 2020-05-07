/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service;

import com.anjbo.bean.ModelConfigDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:42:47
 * @version 1.0
 */
public interface ModelConfigService extends BaseService<ModelConfigDto>{
	int updateByFundId(ModelConfigDto modelConfigDto);
}
