package com.anjbo.dao.huarong;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface KgAppointMapper {
	
	int saveKgAppoint(Map<String,Object> map);
	
	Map getKgAppoint(@Param("orderNo") String  orderNo);
}
