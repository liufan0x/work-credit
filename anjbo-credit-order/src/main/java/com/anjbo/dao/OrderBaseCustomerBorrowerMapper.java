package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseCustomerBorrowerDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;

public interface OrderBaseCustomerBorrowerMapper {
	
	int saveOrderCustomerBorrower(OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto);
	
	int updateOrderCustomerBorrow(OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto);
	
	int updateOrderCustomerBorrowNull(OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto);
	
	List<OrderBaseCustomerBorrowerDto> selectOrderCustomerBorrowerByOrderNo(@Param("orderNo")String orderNo);
	
	OrderBaseCustomerBorrowerDto selectOrderCustomerBorrowerById(@Param("id") int id);
	
	int deleteBorrowerBacthId(OrderBaseCustomerDto orderBaseCustomerDto);
	
	int deleteBorrowerById(int id);
	
	int deleteBorrowerByOrderNo(String orderNo);
}
