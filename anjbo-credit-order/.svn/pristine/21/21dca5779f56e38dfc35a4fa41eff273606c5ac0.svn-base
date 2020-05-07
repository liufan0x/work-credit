/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.order.OrderBaseCustomerShareholderDto;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-01-19 14:18:56
 * @version 1.0
 */
public interface OrderBaseCustomerShareholderMapper extends BaseMapper<OrderBaseCustomerShareholderDto, Integer>{
	
	/**
	 * 根据订单号删除
	 * @Author KangLG<2018年1月19日>
	 * @param orderNo
	 * @return
	 */
	int deleteByOrderNo(String orderNo);
	/**
	 * 更新股东信息
	 * @param orderBaseCustomerShareholderDto
	 * @return
	 */
	int updateOrderCustomerShareholder(OrderBaseCustomerShareholderDto orderBaseCustomerShareholderDto);
	/**
	 * 删除股东信息
	 * @param id
	 * @return
	 */
	int deleteShareholderById(int id);
	/**
	 * 查询股东信息
	 * @param orderNo
	 * @return
	 */
	List<OrderBaseCustomerShareholderDto> selectOrderCustomerShareholderByOrderNo(String orderNo);
	/**
	 * 查询单个股东信息
	 * @param id
	 * @return
	 */
	OrderBaseCustomerShareholderDto selectOrderCustomerShareholderById(int id);
}
