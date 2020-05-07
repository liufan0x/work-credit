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
import com.anjbo.bean.AgencyDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:57
 * @version 1.0
 */
@Api(value = "机构相关")
@RequestMapping("/agency/v")
public interface IAgencyController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<AgencyDto> page(@RequestBody AgencyDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<AgencyDto> search(@RequestBody AgencyDto dto);
	
	@ApiOperation(value = "查询,默认加上快鸽按揭", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "search2", method= {RequestMethod.POST})
	public abstract RespData<AgencyDto> search2(@RequestBody AgencyDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<AgencyDto> find(@RequestBody AgencyDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<AgencyDto> add(@RequestBody AgencyDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody AgencyDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody AgencyDto dto);
		
}