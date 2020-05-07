package com.anjbo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;

import com.anjbo.common.RespDataObject;

public interface RiskGradeService {
	/**
	 * 根据机构类型ID查询风控等级
	 * @param agencyTypeId
	 * @return
	 */
	public List<Map<String,Object>> selectRiskGradeByAgencyTypeId(Map<String,Object> map);
	
	/**
	 * 根据产品与机构类型获取固定/关外/其他等费用 
	 * @param map key=productId(产品id),agencyTypeId(机构类型id)
	 * @return
	 */
	Map<String,Object> selectPoundageByProductAndAgencyTypeId(Map<String,Object> map);
	
	/**
	 * 获取指定机构类型的阶段费率
	* @Title: findStageRate 
	* @param @param map
	* @param @return
	* @return Map<String,Object>
	 */
	public Map<String, Object> findStageRate(Map<String, Object> map);
	
	/**
	 * 根据机构码获取(风控等级)收费类型
	 * @Author KangLG<2017年12月11日>
	 * @param agencyId
	 * @param productId
	 * @return
	 */
	public List<Map<String,Object>> searchRiskGradeListByAgency(int agencyId, int productId);
}
