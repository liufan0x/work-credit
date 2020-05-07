package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseCustomerShareholderDto;
import com.anjbo.dao.OrderBaseCustomerShareholderMapper;
import com.anjbo.service.OrderBaseCustomerShareholderService;
@Service
public class OrderBaseCustomerShareholderServiceImpl implements
		OrderBaseCustomerShareholderService {

	@Resource
	private OrderBaseCustomerShareholderMapper orderBaseCustomerShareholderMapper;
	@Override
	public int saveOrderCustomerShareholder(
			OrderBaseCustomerShareholderDto orderBaseCustomerShareholderDto) {
		return orderBaseCustomerShareholderMapper.insert(orderBaseCustomerShareholderDto);
	}

	@Override
	public int updateOrderCustomerShareholder(
			OrderBaseCustomerShareholderDto orderBaseCustomerShareholderDto) {
		return orderBaseCustomerShareholderMapper.updateOrderCustomerShareholder(orderBaseCustomerShareholderDto);
	}

	@Override
	public int deleteShareholderById(int id) {
		return orderBaseCustomerShareholderMapper.deleteShareholderById(id);
	}

	@Override
	public List<OrderBaseCustomerShareholderDto> selectOrderCustomerShareholderByOrderNo(
			String orderNo) {
		return orderBaseCustomerShareholderMapper.selectOrderCustomerShareholderByOrderNo(orderNo);
	}

	@Override
	public OrderBaseCustomerShareholderDto selectOrderCustomerShareholderById(
			int id) {
		return orderBaseCustomerShareholderMapper.selectOrderCustomerShareholderById(id);
	}

}
