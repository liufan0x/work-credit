package com.anjbo.service;

import com.anjbo.bean.order.OrderBaseHouseDto;

public interface OrderBaseHouseService {
	
	int saveOrderHouse(OrderBaseHouseDto orderBaseHouseDto);
	
	int updateOrderHouse(OrderBaseHouseDto orderBaseHouseDto);
	

	int updateOrderHouseApp(OrderBaseHouseDto orderBaseHouseDto);
	
	OrderBaseHouseDto selectOrderHouseByOrderNo(String orderNo);
	
	int appUpdateHousePurchaser(OrderBaseHouseDto orderBaseHouseDto);
	
	int appUpdateHouseProperty(OrderBaseHouseDto orderBaseHouseDto);
	
	int appUpdateHousePropertyPeople(OrderBaseHouseDto orderBaseHouseDto);
}
