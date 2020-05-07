/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.AgencyFeescaleRiskcontrolDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:58
 * @version 1.0
 */
@Api(value = "id相关")
@RequestMapping("/agencyFeescaleRiskcontrol/v")
public interface IAgencyFeescaleRiskcontrolController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = AgencyFeescaleRiskcontrolDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<AgencyFeescaleRiskcontrolDto> page(@RequestBody AgencyFeescaleRiskcontrolDto dto);
	
	@ApiOperation(value = "查询费率", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/findStageRate")
	public abstract RespDataObject<Map<String, Object>> findStageRate(@RequestBody Map<String, Object> params);
	
	@ApiOperation(value = "查询", httpMethod = "POST" ,response = AgencyFeescaleRiskcontrolDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<AgencyFeescaleRiskcontrolDto> search(@RequestBody AgencyFeescaleRiskcontrolDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = AgencyFeescaleRiskcontrolDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<AgencyFeescaleRiskcontrolDto> find(@RequestBody AgencyFeescaleRiskcontrolDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = AgencyFeescaleRiskcontrolDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<AgencyFeescaleRiskcontrolDto> add(@RequestBody AgencyFeescaleRiskcontrolDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = AgencyFeescaleRiskcontrolDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody AgencyFeescaleRiskcontrolDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = AgencyFeescaleRiskcontrolDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody AgencyFeescaleRiskcontrolDto dto);
	
	
	
}