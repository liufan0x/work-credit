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
import com.anjbo.bean.RoleAuthorityDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 12:25:15
 * @version 1.0
 */
@Api(value = "角色权限关系表相关")
@RequestMapping("/roleAuthority/v")
public interface IRoleAuthorityController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = RoleAuthorityDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<RoleAuthorityDto> page(@RequestBody RoleAuthorityDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = RoleAuthorityDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<RoleAuthorityDto> search(@RequestBody RoleAuthorityDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = RoleAuthorityDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<RoleAuthorityDto> find(@RequestBody RoleAuthorityDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = RoleAuthorityDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<RoleAuthorityDto> add(@RequestBody RoleAuthorityDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = RoleAuthorityDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody RoleAuthorityDto dto);
		
}