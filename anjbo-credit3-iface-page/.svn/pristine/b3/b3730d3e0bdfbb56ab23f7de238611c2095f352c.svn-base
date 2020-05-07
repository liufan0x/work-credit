/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anjbo.bean.data.PageListDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@Api(value = "配置页面列表相关")
@RequestMapping("/list/v")
public interface IPageListController {
	
	@ApiOperation(value = "生成订单号", httpMethod = "POST" ,response = String.class)
	@RequestMapping(value = "/generateOrderNo")
	public RespDataObject<String> generateOrderNo();
	
	@ApiOperation(value = "列表数据", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/pageListData")
	public RespPageData<Map<String,Object>> pageListData(@RequestBody Map<String,Object> map);

	@ApiOperation(value = "撤回订单", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/withdraw")
	public RespStatus withdraw(@RequestBody PageListDto pageListDto);
	
	@ApiOperation(value = "取消订单", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/cancel")
	public RespStatus cancel(@RequestBody PageListDto pageListDto);
	
}