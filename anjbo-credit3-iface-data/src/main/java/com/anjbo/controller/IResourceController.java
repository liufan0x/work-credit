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
import com.anjbo.bean.ResourceDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:28
 * @version 1.0
 */
@Api(value = "资源表相关")
@RequestMapping("/resource/v")
public interface IResourceController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = ResourceDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<ResourceDto> page(@RequestBody ResourceDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = ResourceDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<ResourceDto> search(@RequestBody ResourceDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = ResourceDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<ResourceDto> find(@RequestBody ResourceDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = ResourceDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<ResourceDto> add(@RequestBody ResourceDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = ResourceDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody ResourceDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = ResourceDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody ResourceDto dto);
	
	@ApiOperation(value = "权限与资源", httpMethod = "POST" ,response = ResourceDto.class)
	@RequestMapping(value = "selectResourceAuthority", method= {RequestMethod.POST})
	public abstract RespData<ResourceDto> selectResourceAuthority();
}