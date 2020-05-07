/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.ModelConfigDto;
import com.anjbo.dao.ModelConfigMapper;
import com.anjbo.service.ModelConfigService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:42:47
 * @version 1.0
 */
@Service
public class ModelConfigServiceImpl extends BaseServiceImpl<ModelConfigDto>  implements ModelConfigService {
	@Autowired private ModelConfigMapper modelConfigMapper;

	@Override
	public int updateByFundId(ModelConfigDto modelConfigDto) {
		return modelConfigMapper.updateByFundId(modelConfigDto);
	}

}
