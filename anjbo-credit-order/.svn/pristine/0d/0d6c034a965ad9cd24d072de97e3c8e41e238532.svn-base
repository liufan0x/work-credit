package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseReceivableForDto;
import com.anjbo.dao.OrderBaseReceivableForMapper;
import com.anjbo.service.OrderBaseReceivableForService;
@Service
public class OrderBaseReceivableForServiceImpl implements
		OrderBaseReceivableForService {

	@Resource
	private OrderBaseReceivableForMapper orderBaseReceivableForMapper;

	@Override
	public int saveOrderBaseReceivableFor(
			OrderBaseReceivableForDto orderBaseReceivableForDto) {
		return orderBaseReceivableForMapper.saveOrderBaseReceivableFor(orderBaseReceivableForDto);
	}

	@Override
	public int updateOrderReceivableFor(
			OrderBaseReceivableForDto orderBaseReceivableForDto) {
		return orderBaseReceivableForMapper.saveOrderBaseReceivableFor(orderBaseReceivableForDto);
	}

	@Override
	public List<OrderBaseReceivableForDto> selectOrderReceivableForByOrderNo(
			String orderNo) {
		return orderBaseReceivableForMapper.selectOrderReceivableForByOrderNo(orderNo);
	}
}
