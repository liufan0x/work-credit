package com.anjbo.dao;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHouseDto;

public interface OrderBaseHouseMapper {

	int saveOrderHouse(OrderBaseHouseDto orderBaseHouseDto);
	
	int updateOrderHouse(OrderBaseHouseDto orderBaseHouseDto);
	
	int updateOrderHouseNull(OrderBaseHouseDto orderBaseHouseDto);
	
	OrderBaseHouseDto selectOrderHouseByOrderNo(@Param("orderNo") String orderNo);
}
