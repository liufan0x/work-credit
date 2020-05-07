package com.anjbo.dao.huarong;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LcApptIndivMapper {
	
	int saveLcApptIndiv(Map<String,Object> map);
	
	Map getLcApptIndiv(@Param("orderNo") String  orderNo);
}
