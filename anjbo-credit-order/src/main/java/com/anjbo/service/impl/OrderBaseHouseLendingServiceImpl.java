package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseHouseLendingDto;
import com.anjbo.dao.OrderBaseHouseLendingMapper;
import com.anjbo.service.OrderBaseHouseLendingService;
@Service
public class OrderBaseHouseLendingServiceImpl implements
		OrderBaseHouseLendingService {

	@Resource
	private OrderBaseHouseLendingMapper orderBaseHouseLendingMapper;
	@Override
	public int saveOrderHouseLending(
			OrderBaseHouseLendingDto orderBaseHouseLendingDto) {
		return orderBaseHouseLendingMapper.saveOrderHouseLending(orderBaseHouseLendingDto);
	}

	@Override
	public int updateOrderHouseLending(
			OrderBaseHouseLendingDto orderBaseHouseLendingDto) {
		return orderBaseHouseLendingMapper.updateOrderHouseLending(orderBaseHouseLendingDto);
	}

	@Override
	public int updateOrderHouseLendingNull(
			OrderBaseHouseLendingDto orderBaseHouseLendingDto) {
		return orderBaseHouseLendingMapper.updateOrderHouseLendingNull(orderBaseHouseLendingDto);
	}

	@Override
	public OrderBaseHouseLendingDto selectOrderHouseLendingByOrderNo(
			String orderNo) {
		return orderBaseHouseLendingMapper.selectOrderHouseLendingByOrderNo(orderNo);
	}

	@Override
	public int updateOrderPaymentNull(OrderBaseHouseLendingDto orderBaseHouseLendingDto) {
		return orderBaseHouseLendingMapper.updateOrderPaymentNull(orderBaseHouseLendingDto);
	}

	@Override
	public int updateOrderLendingNull(OrderBaseHouseLendingDto orderBaseHouseLendingDto) {
		return orderBaseHouseLendingMapper.updateOrderLendingNull(orderBaseHouseLendingDto);
	}

}
