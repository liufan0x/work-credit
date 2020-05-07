package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.order.OrderBaseReceivableForDto;

public interface OrderBaseReceivableForService {
	
	int saveOrderBaseReceivableFor(OrderBaseReceivableForDto orderBaseReceivableForDto);
	
	int updateOrderReceivableFor(OrderBaseReceivableForDto orderBaseReceivableForDto);
	
	List<OrderBaseReceivableForDto> selectOrderReceivableForByOrderNo(String orderNo);
}
