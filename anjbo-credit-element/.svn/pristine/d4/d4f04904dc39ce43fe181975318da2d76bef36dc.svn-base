package com.anjbo.service;

import java.util.List;
import java.util.Map;

public interface BoxBaseService {

	//查询可使用的箱子，分配箱子
	public Map<String, Object> randomBox(Map<String, Object> params);

	public List<Map<String, Object>> selectBoxBaseByBoxNo(Map<String, Object> params);

	/**
	 * 根据权限查询是否有柜子可以使用
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectBoxByOperationAuthority(Map<String,Object> map);
	
	//订单号返回boxNo
	public Map<String,Object> selectBoxBaseByOrderNo(Map<String,Object> map);
}
