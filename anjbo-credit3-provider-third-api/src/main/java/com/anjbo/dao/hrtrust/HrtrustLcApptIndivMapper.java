package com.anjbo.dao.hrtrust;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface HrtrustLcApptIndivMapper {
	
	int saveLcApptIndiv(Map<String, Object> map);

	Map<String,Object> getLcApptIndiv(@Param("orderNo") String orderNo);
	Integer  updateMap(Map<String,Object> map);
}
