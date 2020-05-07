/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anjbo.bean.config.PageConfigDto;
import com.anjbo.bean.config.PageListConfigDto;
import com.anjbo.bean.config.PageTabRegionFormConfigDto;
import com.anjbo.bean.data.PageDataDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@Api(value = "页面配置相关")
@RequestMapping("/config/v")
public interface IPageConfigController {
	

	@ApiOperation(value = "列表配置", httpMethod = "POST" ,response = PageListConfigDto.class)
	@RequestMapping(value = "/pageListConfig")
	public abstract RespDataObject<PageListConfigDto> pageListConfig(@RequestBody PageListConfigDto pageListConfigDto);
	
	@ApiOperation(value = "页面配置", httpMethod = "POST" ,response = PageConfigDto.class)
	@RequestMapping(value = "/pageConfig")
	public abstract RespDataObject<PageConfigDto> pageConfig(@RequestBody Map<String, Object> params);
	
	@ApiOperation(value = "页面区域配置", httpMethod = "POST" ,response = PageConfigDto.class)
	@RequestMapping(value = "/pageTabRegionConfigDto")
	public abstract RespData<PageTabRegionFormConfigDto> pageTabRegionConfigDto(@RequestBody Map<String, Object> params);
	
}