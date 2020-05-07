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
import com.anjbo.bean.MonitorArchiveDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:40:22
 * @version 1.0
 */
@Api(value = "风控工具-定时查档相关")
@RequestMapping("/monitorArchive/v")
public interface IMonitorArchiveController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = MonitorArchiveDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<MonitorArchiveDto> page(@RequestBody MonitorArchiveDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = MonitorArchiveDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<MonitorArchiveDto> search(@RequestBody MonitorArchiveDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = MonitorArchiveDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<MonitorArchiveDto> find(@RequestBody MonitorArchiveDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = MonitorArchiveDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<MonitorArchiveDto> add(@RequestBody MonitorArchiveDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = MonitorArchiveDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody MonitorArchiveDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = MonitorArchiveDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody MonitorArchiveDto dto);
		
}