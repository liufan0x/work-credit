/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.element;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:31
 * @version 1.0
 */
@Api(value = "赎楼方式相关")
@RequestMapping("/foreclosureType/v")
public interface IForeclosureTypeController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = ForeclosureTypeDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<ForeclosureTypeDto> page(@RequestBody ForeclosureTypeDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = ForeclosureTypeDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<ForeclosureTypeDto> search(@RequestBody ForeclosureTypeDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = ForeclosureTypeDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<ForeclosureTypeDto> find(@RequestBody ForeclosureTypeDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = ForeclosureTypeDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<ForeclosureTypeDto> add(@RequestBody ForeclosureTypeDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = ForeclosureTypeDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody ForeclosureTypeDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = ForeclosureTypeDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody ForeclosureTypeDto dto);
		
}