package com.anjbo.controller;

import java.util.Map;

import com.anjbo.bean.RiskAuditVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anjbo.common.RespPageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "订单风控统计")
@RequestMapping("/riskAudit/v")
public interface IRiskAuditController{

	@ApiOperation(value = "查询订单风控统计", httpMethod = "POST",response = Map.class)
	@RequestMapping("query")
	public RespPageData<Map<String, Object>> query(@RequestBody Map<String,Object> paramMap);

	/**
	 * 风控退回率统计查询
	 * @param paramMap  通过审批等级  初审/终审/首席风控审批
	 * @return
	 */
	@ApiOperation(value = "风控退回率统计",httpMethod = "POST",response = Map.class)
	@PostMapping("queryRiskAuditRate")
	RespPageData<Map<String, Object>> queryRiskAuditRate(@RequestBody Map<String,Object> paramMap);

}
