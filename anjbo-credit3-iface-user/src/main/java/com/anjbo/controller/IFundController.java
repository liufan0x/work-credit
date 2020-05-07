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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.anjbo.bean.FundDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:59
 * @version 1.0
 */
@Api(value = "合作资金方相关")
@RequestMapping("/fund/v")
public interface IFundController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<FundDto> page(@RequestBody FundDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<FundDto> search(@RequestBody FundDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<FundDto> find(@RequestBody FundDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<FundDto> add(@RequestBody FundDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody FundDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody FundDto dto);
	
	@ApiOperation(value = "冻结资方账号", httpMethod = "POST" ,response = FundDto.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "资方Id", required = true),
        @ApiImplicitParam(name = "contactTel", value = "联系电话", required = true),
        @ApiImplicitParam(name = "managerStatus", value = "资方管理人状态(-1未知0启用1禁用)", required = true),
        @ApiImplicitParam(name = "managerUid", value = "资方管理人uid", required = true),
        @ApiImplicitParam(name = "status", value = "状态 0禁用，1可用", required = true),
	})
	@RequestMapping(value = "/changeManagerStatus")
	public abstract RespStatus changeManagerStatus(FundDto fundDto);
	
	@ApiOperation(value = "根据ID查询 合作资金方", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "/selectCustomerFundById", method= {RequestMethod.POST})
	public FundDto selectCustomerFundById(@RequestBody int id);
}