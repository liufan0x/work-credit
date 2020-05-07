package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseHousePropertyDto;
import com.anjbo.dao.OrderBaseHousePropertyMapper;
import com.anjbo.service.OrderBaseHousePropertyService;
@Service
public class OrderBaseHousePropertyServiceImpl implements
		OrderBaseHousePropertyService {

	@Resource
	private OrderBaseHousePropertyMapper orderBaseHousePropertyMapper;

	@Override
	public int saveOrderHouseProperty(
			OrderBaseHousePropertyDto orderBaseHousePropertyDto) {
		return orderBaseHousePropertyMapper.saveOrderHouseProperty(orderBaseHousePropertyDto);
	}

	@Override
	public int updateOrderPropertyHouse(
			OrderBaseHousePropertyDto orderBaseHousePropertyDto) {
		return orderBaseHousePropertyMapper.updateOrderPropertyHouseNull(orderBaseHousePropertyDto);
	}

	@Override
	public List<OrderBaseHousePropertyDto> selectOrderHousePropertyByOrderNo(
			String orderNo) {
		return orderBaseHousePropertyMapper.selectOrderHousePropertyByOrderNo(orderNo);
	}

	@Override
	public int deleteHousePropertyById(int id) {
		return orderBaseHousePropertyMapper.deleteHousePropertyById(id);
	}

	@Override
	public OrderBaseHousePropertyDto selectOrderHousePropertyById(int id) {
		return orderBaseHousePropertyMapper.selectOrderHousePropertyById(id);
	}
}
