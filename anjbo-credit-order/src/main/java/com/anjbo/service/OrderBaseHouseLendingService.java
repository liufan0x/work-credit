package com.anjbo.service;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHouseLendingDto;
/**
 * 
 * @author Administrator
 *
 */
public interface OrderBaseHouseLendingService {
	int saveOrderHouseLending(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
	
	int updateOrderHouseLending(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
	
	int updateOrderHouseLendingNull(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
	
	OrderBaseHouseLendingDto selectOrderHouseLendingByOrderNo(@Param("orderNo") String orderNo);
	
    int updateOrderPaymentNull(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
	
	int updateOrderLendingNull(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
}
