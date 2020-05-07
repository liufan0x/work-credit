package com.anjbo.service;

import java.util.List;
import java.util.Map;

public interface ProductFlowBaseService {
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
	
	Map<String, Object> selectProductFlowBase(String orderNo);

	/**
	 * 根据节点名称查询最后一条数据
	 * @param map
	 * @return
	 */
	Map<String,Object> selectProductFlowByProcessId(Map<String,Object> map);
	
	/**
	 * 查询所有流水
	 * @return
	 */
	List<Map<String,Object>> selectProductFlowBaseAll();

}
