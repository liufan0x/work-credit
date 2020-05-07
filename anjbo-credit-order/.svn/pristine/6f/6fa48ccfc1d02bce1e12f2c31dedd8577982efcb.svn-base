package com.anjbo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;

public interface OrderBaseBorrowRelationService {
	int savecd(OrderBaseBorrowRelationDto orderBaseBorrowRelationDto);
	int updatecd(OrderBaseBorrowRelationDto orderBaseBorrowRelationDto);
	int deletecd(OrderBaseBorrowDto orderBaseBorrowDto);
	public int deleteRelationByOrderNo(String orderNo);
	OrderBaseBorrowRelationDto selectRelationByOrderNo(@Param("orderNo")String orderNo);
	List<OrderBaseBorrowRelationDto> selectOrderBorrowRelationByOrderNo(@Param("orderNo")String orderNo);
}
