package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.RiskAuditVo;


public interface RiskAuditMapper {
	List<DictDto> findByCity();
	int findByFistCount(Map<String, Object> pareamt);
	int findByFinalCount(Map<String, Object> pareamt);
	int findByOfficerCount(Map<String, Object> pareamt);
	List<String> findByAuditCount(Map<String, Object> pareamt);
	List<String> findByBackCount(Map<String, Object> pareamt);
	List<String> findByCloseCount(Map<String, Object> pareamt);
	List<Map<String, Object>> findByFistCountAll(Map<String, Object> pareamt);
	List<Map<String, Object>> findByFinalCountAll(Map<String, Object> pareamt);
	List<Map<String, Object>> findByOfficerCountAll(Map<String, Object> pareamt);
	List<Map<String, Object>> findByAuditCountAll(Map<String, Object> pareamt);
	List<Map<String, Object>> findByBackCountAll(Map<String, Object> pareamt);
	List<Map<String, Object>> findByCloseCountAll(Map<String, Object> pareamt);

	List<RiskAuditVo> queryRiskAuditRateByAuditId(Map<String, Object> paramMap);
}
