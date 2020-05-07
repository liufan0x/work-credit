package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.risk.RiskModelConfigDto;

public interface RiskModelConfigMapper {

	/**
	 * 查询风控模型
	 * @return
	 */
	List<RiskModelConfigDto> selectCreditList(Map<String, Object> params);
	
	/**
	 * 查询风控模型总条数
	 * @return
	 */
	Integer selectCreditCount(Map<String, Object> params);
	
	/**
	 * 修改风控模型
	 * @param creditDto
	 */
	void updateCredit(Map<String, Object> params);
}
