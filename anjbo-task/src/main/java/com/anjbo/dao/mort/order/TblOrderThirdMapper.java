package com.anjbo.dao.mort.order;

import java.util.List;
import java.util.Map;

public interface TblOrderThirdMapper {

	int deleteByPrimaryKey(Integer id);
	
	/**
	 * 批量新增从赎楼系统获取的订单
	 * @Title: insertOrderBatch 
	 * @param @param map
	 * @return void
	 */
	void insertOrderBatch(List<Map<String,Object>> map);
	/**
	 * 根据订单类型删除
	 * @Title: deleteOrderByType 
	 * @param @param type
	 * @return void
	 */
	void deleteOrderByType(Integer type);
	/**
	 * 新增从赎楼系统获取的订单
	 * @Title: insertOrder 
	 * @param @param map
	 * @return void
	 */
	void insertOrder(Map<String,Object> map);
	
	/**
	 * 删除重复订单
	 * @Title: deleteRepeatOrder 
	 * @param @param type
	 * @return void
	 */
	int deleteRepeatOrder(Integer type);
	/**
	 * 更新订单
	 * @Title: updateOrder 
	 * @param @param map
	 * @return void
	 */
	void updateOrder(Map<String,Object> map);
	
	Map<String,Object> findOrderByOrderNo(Map<String,Object> map);
}