package com.anjbo.dao.hrtrust;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface HrtrustKgApprovalMapper {
	int saveKgApproval(Map<String, Object> map);
	Map<String,Object> getKgApproval(@Param("orderNo") String orderNo);
	Integer  updateMap(Map<String,Object> map);
}
