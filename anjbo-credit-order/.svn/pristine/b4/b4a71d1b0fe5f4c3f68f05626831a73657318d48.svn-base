package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseReceivableForDto;

public interface OrderBaseReceivableForMapper {
	
	int saveOrderBaseReceivableFor(OrderBaseReceivableForDto orderBaseReceivableForDto);
	
	int updateOrderReceivableFor(OrderBaseReceivableForDto orderBaseReceivableForDto);
	
	List<OrderBaseReceivableForDto> selectOrderReceivableForByOrderNo(@Param("orderNo") String orderNo);
	
	int deleteReceivableFor(@Param("orderNo") String orderNo);
	
}
