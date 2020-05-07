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

import com.anjbo.bean.UserDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:15
 * @version 1.0
 */
@Api(value = "用户表相关")
@RequestMapping("/user/v")
public interface IUserController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<UserDto> page(@RequestBody UserDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<UserDto> search(@RequestBody UserDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<UserDto> find(@RequestBody UserDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<UserDto> add(@RequestBody UserDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody UserDto dto);
	
	@ApiOperation(value = "编辑Token", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "editToken", method= {RequestMethod.POST})
	public abstract RespStatus editToken(@RequestBody UserDto dto);
	
	@ApiOperation(value = "修改密码", httpMethod = "POST" ,response = RespStatus.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "newPwd1", value = "旧密码", required = false),
		@ApiImplicitParam(name = "newPwd2", value = "确认密码", required = false),
		@ApiImplicitParam(name = "oldPwd", value = "原密码", required = false)
	})
	@RequestMapping(value = "updataPwd", method= {RequestMethod.POST})
	public abstract RespStatus updataPwd(@RequestBody Map<String, Object> params);
	
	@ApiOperation(value = "重置密码", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "resetPwd", method= {RequestMethod.POST})
	public abstract RespStatus resetPwd(@RequestBody UserDto dto);
	

	@ApiOperation(value = "查询用户返回UserDto", httpMethod = "POST" ,response = UserDto.class)
	@ApiImplicitParams({
	  @ApiImplicitParam(name = "cityCode", value = "城市名称", required = false),
	  @ApiImplicitParam(name = "agencyId", value = "机构Id", required = false),
	  @ApiImplicitParam(name = "type", value = "role,auth", required = false),
	  @ApiImplicitParam(name = "roleName", value = "角色名称", required = false),
	  @ApiImplicitParam(name = "authName", value = "权限名称", required = false)
	})
	@RequestMapping(value = "searchByType", method= {RequestMethod.POST})
	public abstract RespData<UserDto> searchByType(@RequestBody Map<String, Object> params);
	
	@ApiOperation(value = "查询用户返回Map", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "searchByType2", method= {RequestMethod.POST})
	public abstract RespData<Map<String, Object>> searchByType2(@RequestBody Map<String, Object> params);
	
	@ApiOperation(value = "生成用户默认账号密码", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "gennerAccountPwd", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String,Object>> gennerAccountPwd(@RequestBody UserDto dto);
	
	
}