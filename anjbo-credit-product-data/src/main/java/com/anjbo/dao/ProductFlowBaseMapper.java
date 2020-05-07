package com.anjbo.dao;

import java.util.List;
import java.util.Map;

/**
 * @author lic
 * @date 2017-6-9
 */
public interface ProductFlowBaseMapper {
	
	/**
	 * 新增流水数据
	 * @param params
	 * @return
	 */
	int insertProductFlowBase(Map<String, Object> params);
	
	/**
	 * 查询流水数据
	 * @param params(orderNo)
	 * @return
	 */
	List<Map<String, Object>> selectProductFlowBaseList(Map<String, Object> params);
	
	/**
	 * 查询最后一条流水
	 * @param orderNo
	 * @return
	 */
	Map<String, Object> selectProductFlowBase(String orderNo);
	
	/**
	 * 查询可重新开启的所有订单
	 * @return
	 */
	List<Map<String,Object>> selectProductFlowBaseAll();
	
	/**
	 * 查询可撤回的所有订单
	 */
	List<Map<String,Object>> selectWithdrawAll();

	/**
	 * 根据当前节点查询最后一条数据
	 * @param map
	 * @return
	 */
	Map<String,Object> selectProductFlowByCurrentProcessId(Map<String,Object> map);

	/**
	 * 根据下一节点查询最后一条记录
	 * @param map
	 * @return
	 */
	Map<String,Object> selectProductFlowByNextProcessId(Map<String,Object> map);

	/**
	 * 查询最后一条流水
	 * @param map
	 * @return
	 */
	Map<String,Object> selectProductLastFlow(Map<String,Object> map);

	/**
	 * 删除最后一条流水
	 * @param map
	 * @return
	 */
	int deleteLastFlow(Map<String,Object> map);

	/**
	 * 删除所有流水
	 * @param map
	 */
	void deleteAllFlow(Map<String,Object> map);
}