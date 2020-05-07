package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseCustomerGuaranteeDto;
import com.anjbo.dao.OrderBaseCustomerGuaranteeMapper;
import com.anjbo.service.OrderBaseCustomerGuaranteeService;
@Service
public class OrderBaseCustomerGuaranteeServiceImpl implements
		OrderBaseCustomerGuaranteeService {

	@Resource
	private OrderBaseCustomerGuaranteeMapper orderBaseCustomerGuaranteeMapper;

	@Override
	public int saveOrderCustomerGuarantee(
			OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto) {
		return orderBaseCustomerGuaranteeMapper.saveOrderCustomerGuarantee(orderBaseCustomerGuaranteeDto);
	}

	@Override
	public int updateOrderCustomerGuarantee(
			OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto) {
		return orderBaseCustomerGuaranteeMapper.updateOrderCustomerGuaranteeNull(orderBaseCustomerGuaranteeDto);
	}

	@Override
	public List<OrderBaseCustomerGuaranteeDto> selectOrderCustomerGuaranteeByOrderNo(
			String orderNo) {
		return orderBaseCustomerGuaranteeMapper.selectOrderCustomerGuaranteeByOrderNo(orderNo);
	}

	@Override
	public int deleteGuaranteeById(int id) {
		return orderBaseCustomerGuaranteeMapper.deleteGuaranteeById(id);
	}

	@Override
	public OrderBaseCustomerGuaranteeDto selectOrderCustomerGuaranteeById(int id) {
		return orderBaseCustomerGuaranteeMapper.selectOrderCustomerGuaranteeById(id);
	}
}
