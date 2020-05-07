package com.anjbo.service;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseBorrowDto;
@Service
public interface OrderAppService {
	
	int updateOrderBorrow(OrderBaseBorrowDto orderBaseBorrowDto);
}
