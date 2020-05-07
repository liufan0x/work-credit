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
import com.anjbo.bean.ModelConfigDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:42:47
 * @version 1.0
 */
@Api(value = "风控模型表相关")
@RequestMapping("/modelConfig/v")
public interface IModelConfigController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = ModelConfigDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<ModelConfigDto> page(@RequestBody ModelConfigDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = ModelConfigDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<ModelConfigDto> search(@RequestBody ModelConfigDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = ModelConfigDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<ModelConfigDto> find(@RequestBody ModelConfigDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = ModelConfigDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<ModelConfigDto> add(@RequestBody ModelConfigDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = ModelConfigDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody ModelConfigDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = ModelConfigDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody ModelConfigDto dto);
	
	@ApiOperation(value = "全部禁用", httpMethod = "POST" ,response = ModelConfigDto.class)
	@RequestMapping(value = "updateByFundId", method= {RequestMethod.POST})
	public abstract RespStatus updateByFundId(@RequestBody ModelConfigDto dto);
		
}