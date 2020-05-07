package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseHousePurchaserDto;
import com.anjbo.dao.OrderBaseHousePurchaserMapper;
import com.anjbo.service.OrderBaseHousePurchaserService;
@Service
public class OrderBaseHousePurchaserServiceImpl implements
		OrderBaseHousePurchaserService {

	@Resource
	private OrderBaseHousePurchaserMapper orderBaseHousePurchaserMapper;

	@Override
	public int saveOrderBaseHousePurchaser(
			OrderBaseHousePurchaserDto orderBaseHousePurchaserDto) {
		return orderBaseHousePurchaserMapper.saveOrderBaseHousePurchaser(orderBaseHousePurchaserDto);
	}

	@Override
	public int updateOrderHousePurchaser(
			OrderBaseHousePurchaserDto orderBaseHousePurchaserDto) {
		return orderBaseHousePurchaserMapper.updateOrderHousePurchaserNull(orderBaseHousePurchaserDto);
	}

	@Override
	public List<OrderBaseHousePurchaserDto> selectOrderHousePurchaserByOrderNo(
			String orderNo) {
		return orderBaseHousePurchaserMapper.selectOrderHousePurchaserByOrderNo(orderNo);
	}

	@Override
	public int deleteHousePurchaserById(int id) {
		return orderBaseHousePurchaserMapper.deleteHousePurchaserById(id);
	}

	@Override
	public OrderBaseHousePurchaserDto selectOrderHousePurchaserById(int id) {
		return orderBaseHousePurchaserMapper.selectOrderHousePurchaserById(id);
	}
}
