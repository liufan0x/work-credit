package com.anjbo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anjbo.common.RespPageData;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value ="/financial/v")
public interface IFinancialController {

	@ApiOperation(value = "财务统计表", httpMethod = "POST",response = Map.class)
	@RequestMapping("/query")
	public RespPageData<Map<String, Object>> query(@RequestBody Map<String,Object> paramMap);

}
