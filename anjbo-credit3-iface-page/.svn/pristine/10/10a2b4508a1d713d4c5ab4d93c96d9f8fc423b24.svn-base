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
import com.anjbo.bean.data.PageFlowDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-28 20:20:06
 * @version 1.0
 */
@Api(value = "订单流水表相关")
@RequestMapping("/pageFlow/v")
public interface IPageFlowController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = PageFlowDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<PageFlowDto> page(@RequestBody PageFlowDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = PageFlowDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<PageFlowDto> search(@RequestBody PageFlowDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = PageFlowDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<PageFlowDto> find(@RequestBody PageFlowDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = PageFlowDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<PageFlowDto> add(@RequestBody PageFlowDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = PageFlowDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody PageFlowDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = PageFlowDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody PageFlowDto dto);
		
}