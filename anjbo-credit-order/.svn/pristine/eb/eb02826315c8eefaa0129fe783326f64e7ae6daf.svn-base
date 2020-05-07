package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseBorrowDto;

public interface OrderBaseBorrowMapper {

		int saveOrderBorrow(OrderBaseBorrowDto orderBaseBorrowDto);
		
		int updateOrderBorrow(OrderBaseBorrowDto orderBaseBorrowDto);
		
		int updateOrderBorrowNull(OrderBaseBorrowDto orderBaseBorrowDto);
		
		OrderBaseBorrowDto selectOrderBorrowByOrderNo(@Param("orderNo")String orderNo);
		
		List<OrderBaseBorrowDto> selectOrderBorrowList(OrderBaseBorrowDto orderBaseBorrowDto);
		
		int selectOrderBorrowCount(OrderBaseBorrowDto orderBaseBorrowDto);
		
		public int updateAcceptMember(OrderBaseBorrowDto order);
		
}
