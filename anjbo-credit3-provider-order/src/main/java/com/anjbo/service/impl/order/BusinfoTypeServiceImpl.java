/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BusinfoTypeDto;
import com.anjbo.dao.order.BusinfoTypeMapper;
import com.anjbo.service.order.BusinfoTypeService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:47
 * @version 1.0
 */
@Service
public class BusinfoTypeServiceImpl extends BaseServiceImpl<BusinfoTypeDto>  implements BusinfoTypeService {
	@Autowired private BusinfoTypeMapper businfoTypeMapper;

	@Override
	public List<Map<String, Object>> getSonType(Map<String, Object> map) {
		return businfoTypeMapper.getSonType(map);
	}

	@Override
	public List<Map<String, Object>> getParentBusInfoTypes(Map<String, Object> map) {
		return businfoTypeMapper.getParentBusInfoTypes(map);
	}
	
	
}
