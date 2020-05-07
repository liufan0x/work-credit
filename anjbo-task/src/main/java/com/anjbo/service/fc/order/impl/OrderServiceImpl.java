package com.anjbo.service.fc.order.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.dao.fc.order.OrderMapper;
import com.anjbo.service.fc.order.OrderService;
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService{
	@Resource
	private OrderMapper orderMapper;
	@Override
	public Map<String, Object> selectCustomer(String orderNo) {
		return orderMapper.selectCustomer(orderNo);
	}
	
}
