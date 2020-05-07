package com.anjbo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHousePurchaserDto;

public interface OrderBaseHousePurchaserService {
	
	int saveOrderBaseHousePurchaser(OrderBaseHousePurchaserDto orderBaseHousePurchaserDto);
	
	int updateOrderHousePurchaser(OrderBaseHousePurchaserDto orderBaseHousePurchaserDto);
	
	int deleteHousePurchaserById(int id);
	
	List<OrderBaseHousePurchaserDto> selectOrderHousePurchaserByOrderNo(String orderNo);
	
	OrderBaseHousePurchaserDto selectOrderHousePurchaserById(int id);
}
