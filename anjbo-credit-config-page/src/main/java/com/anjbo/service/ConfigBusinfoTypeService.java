package com.anjbo.service;

import java.util.List;
import java.util.Map;

public interface ConfigBusinfoTypeService {
	/**
	 * 查询影像资料父类型
	 * @return
	 */
	List<Map<String,Object>> selectBusinfoParentType(Map<String,Object> map);
	
	/**
	 * 查询影像资料子类型
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectBusinfoSonType(Map<String,Object> map);
	
	/**
	 * 初始化影像资料页面
	 * @param map
	 * @return
	 */
	Map<String,Object> selectBusinfoInit(Map<String,Object> map);
	
	/**
	 * 查询指定productCode的所有业务资料子类型，用于移动功能的列表
	 * @param map
	 * @return
	 */
	Map<String, Object> getAllType(Map<String,Object> map);

	/**
	 * 查询必备影像资料
	 * @param map(key=productCode:产品code)
	 * @return true(校验通过),false(校验失败)
	 */
	List<Map<String,Object>> selectNecessaryBusinfo(Map<String,Object> map);
}
