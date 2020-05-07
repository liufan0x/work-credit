package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHousePropertyDto;

public interface OrderBaseHousePropertyMapper {

	int saveOrderHouseProperty(OrderBaseHousePropertyDto orderBaseHousePropertyDto);
	
	int updateOrderPropertyHouse(OrderBaseHousePropertyDto orderBaseHousePropertyDto);
	
	int updateOrderPropertyHouseNull(OrderBaseHousePropertyDto orderBaseHousePropertyDto);
	
	List<OrderBaseHousePropertyDto> selectOrderHousePropertyByOrderNo(@Param("orderNo") String orderNo);
	
	OrderBaseHousePropertyDto selectOrderHousePropertyById(@Param("id") int id);
	
	int deleteHousePropertyById(int id);
	
	int deleteHousePropertyByOrderNo(String orderNo);
}
