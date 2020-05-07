/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.DictDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-02 18:13:44
 * @version 1.0
 */
@Api(value = "字典表相关")
@RequestMapping("/dict/v")
public interface IDictController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = DictDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<DictDto> page(@RequestBody DictDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = DictDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<DictDto> search(@RequestBody DictDto dto);
	
	@ApiOperation(value = "查询", httpMethod = "POST" ,response = DictDto.class)
	@RequestMapping(value = "searchMap", method= {RequestMethod.POST})
	public abstract RespData<Map<String, Object>> searchMap(@RequestBody DictDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = DictDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<DictDto> find(@RequestBody DictDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = DictDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<DictDto> add(@RequestBody DictDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = DictDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody DictDto dto);
		
}