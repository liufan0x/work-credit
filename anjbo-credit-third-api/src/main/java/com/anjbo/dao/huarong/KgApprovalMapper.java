package com.anjbo.dao.huarong;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface KgApprovalMapper {
	int saveKgApproval(Map<String,Object> map);
	Map getKgApproval(@Param("orderNo") String  orderNo);
}
