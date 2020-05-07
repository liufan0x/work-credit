package com.anjbo.dao.huarong;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface KgHouseMapper {
	int saveKgHouse(Map<String,Object> map);
	Map getKgHouse(@Param("orderNo") String  orderNo);
}
