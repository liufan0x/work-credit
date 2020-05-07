package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseCustomerGuaranteeDto;

public interface OrderBaseCustomerGuaranteeMapper {

	int saveOrderCustomerGuarantee(OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto);
	
	int updateOrderCustomerGuarantee(OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto);
	
	int updateOrderCustomerGuaranteeNull(OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto);
	
	List<OrderBaseCustomerGuaranteeDto> selectOrderCustomerGuaranteeByOrderNo(@Param("orderNo")String orderNo);
	
	OrderBaseCustomerGuaranteeDto selectOrderCustomerGuaranteeById(@Param("id") int id);
	
	int deleteGuaranteeById(int id);
	
	int deleteGuaranteeByOrderNo(String orderNo);
}
