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

import com.anjbo.bean.order.BaseHousePropertypeopleDto;
import com.anjbo.dao.order.BaseHousePropertypeopleMapper;
import com.anjbo.service.order.BaseHousePropertypeopleService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@Service
public class BaseHousePropertypeopleServiceImpl extends BaseServiceImpl<BaseHousePropertypeopleDto>  implements BaseHousePropertypeopleService {
	@Autowired private BaseHousePropertypeopleMapper baseHousePropertypeopleMapper;

	@Override
	public List<Map<String, Object>> findAll(BaseHousePropertypeopleDto baseHousePropertyDto) {
		// TODO Auto-generated method stub
		return baseHousePropertypeopleMapper.findAll(baseHousePropertyDto);
	}

}
