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
import com.anjbo.bean.AgencyFeescaleDetailDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:58
 * @version 1.0
 */
@Api(value = "ID相关")
@RequestMapping("/agencyFeescaleDetail/v")
public interface IAgencyFeescaleDetailController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = AgencyFeescaleDetailDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<AgencyFeescaleDetailDto> page(@RequestBody AgencyFeescaleDetailDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = AgencyFeescaleDetailDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<AgencyFeescaleDetailDto> search(@RequestBody AgencyFeescaleDetailDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = AgencyFeescaleDetailDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<AgencyFeescaleDetailDto> find(@RequestBody AgencyFeescaleDetailDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = AgencyFeescaleDetailDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<AgencyFeescaleDetailDto> add(@RequestBody AgencyFeescaleDetailDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = AgencyFeescaleDetailDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody AgencyFeescaleDetailDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = AgencyFeescaleDetailDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody AgencyFeescaleDetailDto dto);
		
}