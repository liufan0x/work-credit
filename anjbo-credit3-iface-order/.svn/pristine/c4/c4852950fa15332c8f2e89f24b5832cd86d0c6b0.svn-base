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
import com.anjbo.bean.order.BaseReceivableForDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@Api(value = "计划回款信息相关")
@RequestMapping("/baseReceivableFor/v")
public interface IBaseReceivableForController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = BaseReceivableForDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<BaseReceivableForDto> page(@RequestBody BaseReceivableForDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = BaseReceivableForDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<BaseReceivableForDto> search(@RequestBody BaseReceivableForDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = BaseReceivableForDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<BaseReceivableForDto> find(@RequestBody BaseReceivableForDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = BaseReceivableForDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<BaseReceivableForDto> add(@RequestBody BaseReceivableForDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = BaseReceivableForDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody BaseReceivableForDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = BaseReceivableForDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody BaseReceivableForDto dto);
		
}