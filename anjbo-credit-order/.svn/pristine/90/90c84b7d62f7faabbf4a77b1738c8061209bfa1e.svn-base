package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHousePurchaserDto;

public interface OrderBaseHousePurchaserMapper {
	
	int saveOrderBaseHousePurchaser(OrderBaseHousePurchaserDto orderBaseHousePurchaserDto);
	
	int updateOrderHousePurchaser(OrderBaseHousePurchaserDto orderBaseHousePurchaserDto);
	
	int updateOrderHousePurchaserNull(OrderBaseHousePurchaserDto orderBaseHousePurchaserDto);
	
	List<OrderBaseHousePurchaserDto> selectOrderHousePurchaserByOrderNo(@Param("orderNo") String orderNo);
	
	OrderBaseHousePurchaserDto selectOrderHousePurchaserById(@Param("id") int id);
	
	int deleteHousePurchaserById(int id);
	
	int deleteHousePurchaserByOrderNo(String orderNo);
}
