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
import com.anjbo.bean.PreparationReplyrecordDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-25 16:16:14
 * @version 1.0
 */
@Api(value = "报备修改记录回复相关")
@RequestMapping("/preparationReplyrecord/v")
public interface IPreparationReplyrecordController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = PreparationReplyrecordDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<PreparationReplyrecordDto> page(@RequestBody PreparationReplyrecordDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = PreparationReplyrecordDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<PreparationReplyrecordDto> search(@RequestBody PreparationReplyrecordDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = PreparationReplyrecordDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<PreparationReplyrecordDto> find(@RequestBody PreparationReplyrecordDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = PreparationReplyrecordDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<PreparationReplyrecordDto> add(@RequestBody PreparationReplyrecordDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = PreparationReplyrecordDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody PreparationReplyrecordDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = PreparationReplyrecordDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody PreparationReplyrecordDto dto);
		
}