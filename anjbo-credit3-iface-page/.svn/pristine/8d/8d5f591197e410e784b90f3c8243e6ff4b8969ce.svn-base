/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anjbo.bean.data.PageBusinfoDto;
import com.anjbo.bean.data.PageBusinfoTypeDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@Api(value = "页面影像资料相关")
@RequestMapping("/businfo/v")
public interface IPageBusinfoController {
	
	@ApiOperation(value = "页面影像资料", httpMethod = "POST" ,response = PageBusinfoTypeDto.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderNo", 	value = "订单号", required = true),
        @ApiImplicitParam(name = "productCode", value = "产品code", required = true),
	})
	@RequestMapping(value = "/pageBusinfoConfig")
	public abstract RespData<PageBusinfoTypeDto> pageBusinfoConfig(@RequestBody Map<String, Object> params);

	@ApiOperation(value = "获取单个影像资料集合", httpMethod = "POST" ,response = Map.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderNo", 	value = "订单号", required = true),
        @ApiImplicitParam(name = "typeId",  	value = "影像资料类型", required = true),
	})
	@RequestMapping(value = "/selectPageBusinfo")
	public RespData<PageBusinfoDto> selectPageBusinfo(@RequestBody PageBusinfoDto pageBusinfoDto);
	
	@ApiOperation(value = "页面影像保存", httpMethod = "POST" ,response = RespStatus.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderNo", 	value = "订单号", required = true),
        @ApiImplicitParam(name = "productCode", value = "产品code", required = true),
        @ApiImplicitParam(name = "typeId",  	value = "影像资料类型", required = true),
        @ApiImplicitParam(name = "urls",    	value = "影像资料", required = true),
	})
	@RequestMapping(value = "/pageBusinfoSave")
	public abstract RespStatus pageBusinfoSave(@RequestBody List<PageBusinfoDto> pageBusinfoDtos);
	

	@ApiOperation(value = "页面影像删除", httpMethod = "POST" ,response = RespStatus.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderNo", 	value = "订单号", required = true),
        @ApiImplicitParam(name = "productCode", value = "产品code", required = true),
        @ApiImplicitParam(name = "ids",     	value = "影像资料Ids,逗号隔开", required = true),
	})
	@RequestMapping(value = "/pageBusinfoDelete")
	public abstract RespStatus pageBusinfoDelete(@RequestBody PageBusinfoDto pageBusinfoDto);
	

	@ApiOperation(value = "页面影像移动", httpMethod = "POST" ,response = RespStatus.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderNo", 	value = "订单号", required = true),
        @ApiImplicitParam(name = "productCode", value = "产品code", required = true),
        @ApiImplicitParam(name = "ids",     	value = "影像资料Ids,逗号隔开", required = true),
        @ApiImplicitParam(name = "typeId",  	value = "移动到的影像资料类型", required = true),
	})
	@RequestMapping(value = "/pageBusinfoMove")
	public abstract RespStatus pageBusinfoMove(@RequestBody PageBusinfoDto pageBusinfoDto);
	
}