package com.anjbo.dao;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHouseLendingDto;
/**
 * 放款信息:房抵贷
 * @author Administrator
 *
 */
public interface OrderBaseHouseLendingMapper {

	int saveOrderHouseLending(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
	
	int updateOrderHouseLending(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
	
	int updateOrderHouseLendingNull(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
	
	OrderBaseHouseLendingDto selectOrderHouseLendingByOrderNo(@Param("orderNo") String orderNo);
	
	int updateOrderPaymentNull(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
	
	int updateOrderLendingNull(OrderBaseHouseLendingDto orderBaseHouseLendingDto);
}
