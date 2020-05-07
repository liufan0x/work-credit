package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.anjbo.bean.RiskAuditVo;
import org.springframework.stereotype.Service;

import com.anjbo.bean.DictDto;
import com.anjbo.dao.RiskAuditMapper;
import com.anjbo.service.RiskAuditService;

@Service
public class RiskAuditServiceImpl implements RiskAuditService {

	@Resource RiskAuditMapper riskAuditMapper;

	@Override
	public List<DictDto> findByCity() {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByCity();
	}

	@Override
	public int findByFistCount(Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByFistCount(pareamt);
	}

	@Override
	public int findByFinalCount(Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByFinalCount(pareamt);
	}

	@Override
	public int findByOfficerCount(Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByOfficerCount(pareamt);
	}

	@Override
	public List<String> findByAuditCount(Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByAuditCount(pareamt);
	}

	@Override
	public List<String> findByBackCount(Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByBackCount(pareamt);
	}

	@Override
	public List<String> findByCloseCount(Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByCloseCount(pareamt);
	}

	@Override
	public List<Map<String, Object>> findByFistCountAll(
			Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByFistCountAll(pareamt);
	}

	@Override
	public List<Map<String, Object>> findByFinalCountAll(
			Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByFinalCountAll(pareamt);
	}

	@Override
	public List<Map<String, Object>> findByOfficerCountAll(
			Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByOfficerCountAll(pareamt);
	}

	@Override
	public List<Map<String, Object>> findByCloseCountAll(
			Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByCloseCountAll(pareamt);
	}

	@Override
	public List<RiskAuditVo> queryRiskAuditRateByAuditId(Map<String, Object> paramMap) {
		return riskAuditMapper.queryRiskAuditRateByAuditId(paramMap);
	}

	@Override
	public List<Map<String, Object>> findByAuditCountAll(
			Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByAuditCountAll(pareamt);
	}

	@Override
	public List<Map<String, Object>> findByBackCountAll(
			Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return riskAuditMapper.findByBackCountAll(pareamt);
	}
	
}
