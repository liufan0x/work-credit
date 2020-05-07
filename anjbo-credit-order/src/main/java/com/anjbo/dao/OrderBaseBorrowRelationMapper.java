package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseBorrowRelationDto;

public interface OrderBaseBorrowRelationMapper {

		int saveOrderBorrowRelation(OrderBaseBorrowRelationDto orderBaseBorrowRelationDto);
		
		int updateOrderBorrowRelation(OrderBaseBorrowRelationDto orderBaseBorrowRelationDto);
		
		int updateOrderBorrowRelationNull(OrderBaseBorrowRelationDto orderBaseBorrowRelationDto);
		
		/**
		 * 查询关联订单集合
		 * @param orderNo
		 * @return
		 */
		List<OrderBaseBorrowRelationDto> selectOrderBorrowRelationByOrderNo(@Param("orderNo")String orderNo);
		
		OrderBaseBorrowRelationDto selectRelationByOrderNo(@Param("orderNo")String orderNo);
		
		int deleteOrderBorrowRelation(@Param("relationOrderNo")String relationOrderNo);
		
		int deleteRelationByOrderNo(@Param("orderNo")String orderNo);
}
