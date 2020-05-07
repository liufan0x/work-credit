/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.order;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-11 10:57:21
 * @version 1.0
 */
@Api(value = "订单列表表相关")
@RequestMapping("/baseList/v")
public interface IBaseListController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<BaseListDto> page(@RequestBody BaseListDto dto);
	
	@ApiOperation(value = "资方列表", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "/fundList")
	public RespPageData<BaseListDto> fundList(@RequestBody BaseListDto orderListDto);
	
	@ApiOperation(value = "App分页查询", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/appList")
	public abstract RespDataObject<Map<String, Object>> appList(@RequestBody BaseListDto dto);
	
	@ApiOperation(value = "查询", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<BaseListDto> search(@RequestBody BaseListDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<BaseListDto> find(@RequestBody BaseListDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<BaseListDto> add(@RequestBody BaseListDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody BaseListDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody BaseListDto dto);
	
	@ApiOperation(value = "查询可关联的订单", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/selectAbleRelationOrder") 
	public RespData<Map<String, Object>> selectAbleRelationOrder(@RequestBody BaseListDto baseListDto);
	
}