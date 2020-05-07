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
import com.anjbo.bean.AuthorityDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@Api(value = "权限表相关")
@RequestMapping("/authority/v")
public interface IAuthorityController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = AuthorityDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<AuthorityDto> page(@RequestBody AuthorityDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = AuthorityDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<AuthorityDto> search(@RequestBody AuthorityDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = AuthorityDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<AuthorityDto> find(@RequestBody AuthorityDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = AuthorityDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<AuthorityDto> add(@RequestBody AuthorityDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = AuthorityDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody AuthorityDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = AuthorityDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody AuthorityDto dto);
	
	@ApiOperation(value = "加载城市机构权限", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/selectCityProductAuthority")
	public RespData<Map<String, Object>> selectCityProductAuthority();
	
}