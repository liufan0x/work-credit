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
import com.anjbo.bean.FundCostDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:55:00
 * @version 1.0
 */
@Api(value = "合作资金方业务产品相关")
@RequestMapping("/fundCost/v")
public interface IFundCostController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = FundCostDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<FundCostDto> page(@RequestBody FundCostDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = FundCostDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<FundCostDto> search(@RequestBody FundCostDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = FundCostDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<FundCostDto> find(@RequestBody FundCostDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = FundCostDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<FundCostDto> add(@RequestBody FundCostDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = FundCostDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody FundCostDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = FundCostDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody FundCostDto dto);
		
}