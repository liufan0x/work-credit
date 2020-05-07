package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseCustomerBorrowerDto;
import com.anjbo.dao.OrderBaseCustomerBorrowerMapper;
import com.anjbo.service.OrderBaseCustomerBorrowerService;
@Service
public class OrderBaseCustomerBorrowerServiceImpl implements
		OrderBaseCustomerBorrowerService {
	
	@Resource
	private OrderBaseCustomerBorrowerMapper orderBaseCustomerBorrowerMapper;

	@Override
	public int saveOrderCustomerBorrower(
			OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto) {
		return orderBaseCustomerBorrowerMapper.saveOrderCustomerBorrower(orderBaseCustomerBorrowerDto);
	}

	@Override
	public int updateOrderCustomerBorrow(
			OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto) {
		return orderBaseCustomerBorrowerMapper.updateOrderCustomerBorrowNull(orderBaseCustomerBorrowerDto);
	}

	@Override
	public List<OrderBaseCustomerBorrowerDto> selectOrderCustomerBorrowerByOrderNo(
			String orderNo) {
		return orderBaseCustomerBorrowerMapper.selectOrderCustomerBorrowerByOrderNo(orderNo);
	}

	@Override
	public int deleteBorrowerById(int id) {
		return orderBaseCustomerBorrowerMapper.deleteBorrowerById(id);
	}

	@Override
	public OrderBaseCustomerBorrowerDto selectOrderCustomerBorrowerById(int id) {
		return orderBaseCustomerBorrowerMapper.selectOrderCustomerBorrowerById(id);
	}
}
