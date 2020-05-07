package com.anjbo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.anjbo.common.RespDataObject;
import com.anjbo.dao.RiskGradeMapper;
import com.anjbo.service.RiskGradeService;

@Service
public class RiskGradeServiceImpl implements RiskGradeService {

	@Resource
	private RiskGradeMapper riskGradeMapper;

	@Override
	public List<Map<String, Object>> selectRiskGradeByAgencyTypeId(
			Map<String, Object> map) {
		return riskGradeMapper.selectRiskGradeByAgencyTypeId(map);
	}

	/**
	 * 查询费率
	 */
	@Override
	public Map<String, Object> findStageRate(Map<String, Object> map) {
		double loanAmount = MapUtils.getDoubleValue(map, "loanAmount");
		List<Map<String, Object>> list = riskGradeMapper.findStageRate(map);
		Map<String, Object> map3 = null;
		boolean flag = false;
		for (Map<String, Object> map2 : list) {
			map3 = new HashMap<String, Object>();
			if (MapUtils.getDouble(map2, "section") >= loanAmount) {
				map3 = map2;
				flag = true;
			}
		}
		if(flag&&(map3==null||map3.isEmpty())){
			for (Map<String, Object> map2 : list) {
				map3 = new HashMap<String, Object>();
				if (MapUtils.getDouble(map2, "section") < loanAmount) {
					map3 = map2;
					flag = true;
					break;
				}
			}
		}
		return flag ? map3 : ((list==null||list.size()<1)?null:list.get(0));
	}

	@Override
	public Map<String, Object> selectPoundageByProductAndAgencyTypeId(
			Map<String, Object> map) {
		return riskGradeMapper.selectPoundageByProductAndAgencyTypeId(map);
	}
	
	@Override
	public List<Map<String,Object>> searchRiskGradeListByAgency(int agencyId, int productId){
		return riskGradeMapper.searchRiskGradeListByAgency(agencyId, productId);
	}
}
