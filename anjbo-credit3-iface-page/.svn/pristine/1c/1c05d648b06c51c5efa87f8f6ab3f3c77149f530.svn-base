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
import com.anjbo.bean.data.PageDataDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-28 20:20:05
 * @version 1.0
 */
@Api(value = "配置页面数据相关")
@RequestMapping("/pageData/v")
public interface IPageDataController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = PageDataDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<PageDataDto> page(@RequestBody PageDataDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = PageDataDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<PageDataDto> search(@RequestBody PageDataDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = PageDataDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<PageDataDto> find(@RequestBody PageDataDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = PageDataDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<PageDataDto> add(@RequestBody PageDataDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = PageDataDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody PageDataDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = PageDataDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody PageDataDto dto);
	
	@ApiOperation(value = "页面标签页数据保存", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "/savePageTabConfigDto")
	public abstract RespStatus savePageTabConfigDto(@RequestBody PageDataDto pageDataDto);

	@ApiOperation(value = "页面数据提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "/submitPageConfigDto")
	public abstract RespStatus submitPageConfigDto(@RequestBody PageDataDto pageDataDto);
	
}