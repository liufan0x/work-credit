package com.anjbo.dao;

import java.util.List;
import java.util.Map;


public interface BoxBaseMapper {
	
	//查询可用柜子
	public List<Map<String, Object>> selectBoxBaseList (Map<String,Object> map);
	
	/**
	 * 根据权限查询是否有柜子可以使用
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectBoxByOperationAuthority(Map<String,Object> map);
	
	//修改箱子使用状态
	public int updateUseStatus(Map<String,Object> map);
	
	
	//修改箱子使用状态
	public int updateBoxBaseByBoxNo(Map<String,Object> map);
	
	
	
	//通过要件箱号查询箱子
	public List<Map<String, Object>> selectBoxBaseByBoxNo (Map<String,Object> map);
	
	//订单号返回boxNo
	public Map<String,Object> selectBoxBaseByOrderNo(Map<String,Object> map);
	
	
}