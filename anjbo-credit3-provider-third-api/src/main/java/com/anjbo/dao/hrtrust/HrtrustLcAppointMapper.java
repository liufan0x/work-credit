package com.anjbo.dao.hrtrust;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface HrtrustLcAppointMapper {
	
	int saveLcAppoint(Map<String, Object> map);

	Map<String,Object> getLcAppoint(@Param("orderNo") String orderNo);
	Integer  updateMap(Map<String,Object> map);
}
