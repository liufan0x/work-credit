package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.OrderBusinfoTypeMapper;
import com.anjbo.service.OrderBusinfoTypeService;
@Service
public class OrderBusinfoTypeServiceImpl implements OrderBusinfoTypeService {
	
	@Resource
	private OrderBusinfoTypeMapper orderBusinfoTypeMapper;
	
	@Override
	public List<Map> getAllBusType() {
		return orderBusinfoTypeMapper.getAllBusType();
	}

	@Override
	public int mustBusInfoCount(Map<String,Object> map) {
		return orderBusinfoTypeMapper.mustBusInfoCount(map);
	}

}
