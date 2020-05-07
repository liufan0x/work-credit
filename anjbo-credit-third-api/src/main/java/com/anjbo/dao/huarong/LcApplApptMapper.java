package com.anjbo.dao.huarong;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LcApplApptMapper {
	
	int saveLcApplAppt(Map<String,Object> map);
	
	Map getLcApplAppt(@Param("orderNo") String  orderNo);
}
