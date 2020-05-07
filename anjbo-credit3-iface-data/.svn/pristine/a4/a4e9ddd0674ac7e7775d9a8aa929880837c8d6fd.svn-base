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
import com.anjbo.bean.BankSubDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-02 18:13:44
 * @version 1.0
 */
@Api(value = "支行相关")
@RequestMapping("/bankSub/v")
public interface IBankSubController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = BankSubDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<BankSubDto> page(@RequestBody BankSubDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = BankSubDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<BankSubDto> search(@RequestBody BankSubDto dto);
	
	@ApiOperation(value = "通过pid查询", httpMethod = "POST" ,response = BankSubDto.class)
	@RequestMapping(value = "searchByPid", method= {RequestMethod.POST})
	public abstract RespData<BankSubDto> searchByPid(@RequestBody BankSubDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = BankSubDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<BankSubDto> find(@RequestBody BankSubDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = BankSubDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<BankSubDto> add(@RequestBody BankSubDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = BankSubDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody BankSubDto dto);
		
}