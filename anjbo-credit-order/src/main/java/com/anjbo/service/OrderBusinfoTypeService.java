package com.anjbo.service;

import java.util.List;
import java.util.Map;

public interface OrderBusinfoTypeService {
	List<Map> getAllBusType();
	 /**
     * 业务产品必传影像资料种类
     * @param map
     * @return
     */
    int mustBusInfoCount(Map<String,Object> map);
}
