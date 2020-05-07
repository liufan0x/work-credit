package com.anjbo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHousePropertyDto;

public interface OrderBaseHousePropertyService {

	int saveOrderHouseProperty(OrderBaseHousePropertyDto orderBaseHousePropertyDto);
	
	int updateOrderPropertyHouse(OrderBaseHousePropertyDto orderBaseHousePropertyDto);
	
	int deleteHousePropertyById(int id);
	
	List<OrderBaseHousePropertyDto> selectOrderHousePropertyByOrderNo(String orderNo);
	
	OrderBaseHousePropertyDto selectOrderHousePropertyById(int id);
}
