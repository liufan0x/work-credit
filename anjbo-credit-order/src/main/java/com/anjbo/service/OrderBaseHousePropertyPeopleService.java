package com.anjbo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHousePropertyPeopleDto;

public interface OrderBaseHousePropertyPeopleService {
	
	int saveOrderPropertyPeople(OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto);
	
	int updateOrderPropertyPeople(OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto);
	
	int deleteProHouseById(int id);
	
	List<OrderBaseHousePropertyPeopleDto> selectOrderPropertyPeopleByOrderNo(String orderNo);
	
	OrderBaseHousePropertyPeopleDto selectOrderPropertyPeopleById(int id);
}
