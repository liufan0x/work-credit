/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.RisklistDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:21:45
 * @version 1.0
 */
@Api(value = "风控工具-风险名单查询相关")
@RequestMapping("/risklist/v")
public interface IRisklistController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = RisklistDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<RisklistDto> page(@RequestBody RisklistDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = RisklistDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<RisklistDto> search(@RequestBody RisklistDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = RisklistDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<RisklistDto> find(@RequestBody RisklistDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = RisklistDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<RisklistDto> add(@RequestBody RisklistDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = RisklistDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody RisklistDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = RisklistDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody RisklistDto dto);
		
}