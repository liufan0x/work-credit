package com.anjbo.service;

import java.util.Map;

import com.anjbo.bean.order.OrderBaseCustomerDto;

public interface OrderBaseCustomerService {
	
	int saveOrderCustomer(OrderBaseCustomerDto orderBaseCustomerDto);
	
	int updateOrderCustomer(OrderBaseCustomerDto orderBaseCustomerDto);
	
	OrderBaseCustomerDto selectOrderCustomerByOrderNo(String orderNo);
	
	int appUpdateOrderCustomer(OrderBaseCustomerDto orderBaseCustomerDto);
	
	/**
	 * 更新客户企业信息
	 * @param orderBaseCustomerDto
	 * @return
	 */
	int appUpdateOrderCustomerCompany(OrderBaseCustomerDto orderBaseCustomerDto);
	
	public int appUpdateCustomerBorrower(OrderBaseCustomerDto orderBaseCustomerDto);
	
	public int appUpdateCustomerGuarantee(OrderBaseCustomerDto orderBaseCustomerDto);
	
	public int appUpdateCustomerShareholder(OrderBaseCustomerDto orderBaseCustomerDto);
	
	Map<Object,String> allCustomerNos(Map<Object,String> orderNos);
}
