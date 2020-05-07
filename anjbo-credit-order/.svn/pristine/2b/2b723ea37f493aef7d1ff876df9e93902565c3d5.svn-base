package com.anjbo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseCustomerDto;

public interface OrderBaseCustomerMapper {

	int saveOrderCustomer(OrderBaseCustomerDto orderBaseCustomerDto);
	
	int updateOrderCustomer(OrderBaseCustomerDto orderBaseCustomerDto);
	
	int updateOrderCustomerNull(OrderBaseCustomerDto orderBaseCustomerDto);
	
	/**
	 * 更新客户企业信息
	 * @param orderBaseCustomerDto
	 * @return
	 */
	int updateOrderCustomerCompanyNull(OrderBaseCustomerDto orderBaseCustomerDto);
	
	OrderBaseCustomerDto selectOrderCustomerByOrderNo(@Param("orderNo") String orderNo);
	
	Map<Object,String> allCustomerNos(Map<Object,String> orderNos);
}
