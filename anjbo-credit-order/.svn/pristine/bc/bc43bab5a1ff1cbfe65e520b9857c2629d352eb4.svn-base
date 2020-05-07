package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseHousePropertyPeopleDto;
import com.anjbo.dao.OrderBaseHousePropertyPeopleMapper;
import com.anjbo.service.OrderBaseHousePropertyPeopleService;
@Service
public class OrderBaseHousePropertyPeopleServiceImpl implements
		OrderBaseHousePropertyPeopleService {

	@Resource
	private OrderBaseHousePropertyPeopleMapper orderBaseHousePropertyPeopleMapper;

	@Override
	public int saveOrderPropertyPeople(
			OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto) {
		return orderBaseHousePropertyPeopleMapper.saveOrderPropertyPeople(orderBaseHousePropertyPeopleDto);
	}

	@Override
	public int updateOrderPropertyPeople(
			OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto) {
		return orderBaseHousePropertyPeopleMapper.updateOrderPropertyPeopleNull(orderBaseHousePropertyPeopleDto);
	}

	@Override
	public List<OrderBaseHousePropertyPeopleDto> selectOrderPropertyPeopleByOrderNo(
			String orderNo) {
		return orderBaseHousePropertyPeopleMapper.selectOrderPropertyPeopleByOrderNo(orderNo);
	}

	@Override
	public int deleteProHouseById(int id) {
		return orderBaseHousePropertyPeopleMapper.deleteProHouseById(id);
	}

	@Override
	public OrderBaseHousePropertyPeopleDto selectOrderPropertyPeopleById(int id) {
		return orderBaseHousePropertyPeopleMapper.selectOrderPropertyPeopleById(id);
	}
}
