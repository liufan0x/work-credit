package com.anjbo.service;

import java.util.Map;

public interface ForeclosureTypeService {
	/**
	 * 根据订单号查询要件
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectRiskElement(Map<String,Object> param);
}
