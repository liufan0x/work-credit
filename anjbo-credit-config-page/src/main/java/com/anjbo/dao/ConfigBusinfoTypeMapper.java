package com.anjbo.dao;

import java.util.List;
import java.util.Map;

/**
 * 页面配置 
 * @author liuf
 * @date 2017-8-18
 */
public interface ConfigBusinfoTypeMapper {

	List<Map<String,Object>> selectBusinfoParentType(Map<String,Object> map);
	
	List<Map<String,Object>> selectBusinfoSonType(Map<String,Object> map);
	
	List<Map<String,Object>> getAllType(Map<String,Object> map);

	List<Map<String,Object>> selectNecessaryBusinfo(Map<String,Object> map);

}