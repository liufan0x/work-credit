/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.order;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-10 16:09:44
 * @version 1.0
 */
@Api(value = "订单流水表相关")
@RequestMapping("/flow/v")
public interface IFlowController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<FlowDto> page(@RequestBody FlowDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<FlowDto> search(@RequestBody FlowDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<FlowDto> find(@RequestBody FlowDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<FlowDto> add(@RequestBody FlowDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody FlowDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody FlowDto dto);
		
	@ApiOperation(value = "查询最后一条订单详情", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "/selectEndOrderFlow",method= {RequestMethod.POST}) 
	public FlowDto selectEndOrderFlow(@RequestBody FlowDto dto);	
	
	@ApiOperation(value = "查询订单流水(有重复的)", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "/selectOrderFlowListRepeat")
	public RespData<FlowDto> selectOrderFlowListRepeat(@RequestBody FlowDto dto);
	
	@ApiOperation(value = "查询订单流水(无重复的)", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "/selectOrderFlowList")
	public RespData<FlowDto> selectOrderFlowList(@RequestBody FlowDto dto);
	
}