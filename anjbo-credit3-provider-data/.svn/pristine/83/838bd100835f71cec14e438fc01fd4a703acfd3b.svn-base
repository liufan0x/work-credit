/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.AdministrationDivideDto;
import com.anjbo.dao.AdministrationDivideMapper;
import com.anjbo.service.AdministrationDivideService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-27 21:54:08
 * @version 1.0
 */
@Service
public class AdministrationDivideServiceImpl extends BaseServiceImpl<AdministrationDivideDto>  implements AdministrationDivideService {
	@Autowired private AdministrationDivideMapper administrationDivideMapper;

	@Override
	public Map<String, Object> find(Map<String, Object> map) {
		return administrationDivideMapper.find(map);
	}
  
	
}
