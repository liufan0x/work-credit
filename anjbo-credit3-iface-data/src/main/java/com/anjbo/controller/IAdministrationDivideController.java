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
import com.anjbo.bean.AdministrationDivideDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-27 21:54:08
 * @version 1.0
 */
@RequestMapping("/administrationDivide/v")
@Api(value="id相关")
public interface IAdministrationDivideController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = AdministrationDivideDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<AdministrationDivideDto> page(@RequestBody AdministrationDivideDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = AdministrationDivideDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<AdministrationDivideDto> search(@RequestBody AdministrationDivideDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = AdministrationDivideDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<AdministrationDivideDto> find(@RequestBody AdministrationDivideDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = AdministrationDivideDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<AdministrationDivideDto> add(@RequestBody AdministrationDivideDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = AdministrationDivideDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody AdministrationDivideDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = AdministrationDivideDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody AdministrationDivideDto dto);
		
}