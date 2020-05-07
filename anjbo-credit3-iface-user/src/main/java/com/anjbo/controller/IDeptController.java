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
import com.anjbo.bean.DeptDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:14
 * @version 1.0
 */
@Api(value = "部门表相关")
@RequestMapping("/dept/v")
public interface IDeptController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = DeptDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<DeptDto> page(@RequestBody DeptDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = DeptDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<DeptDto> search(@RequestBody DeptDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = DeptDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<DeptDto> find(@RequestBody DeptDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = DeptDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<DeptDto> add(@RequestBody DeptDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = DeptDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody DeptDto dto);
		
}