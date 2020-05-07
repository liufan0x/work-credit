/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.finance;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@Api(value = "申请放款日志相关")
@RequestMapping("/financeLog/v")
public interface IFinanceLogController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = FinanceLogDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<FinanceLogDto> page(@RequestBody FinanceLogDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = FinanceLogDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<FinanceLogDto> search(@RequestBody FinanceLogDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = FinanceLogDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<FinanceLogDto> find(@RequestBody FinanceLogDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = FinanceLogDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<FinanceLogDto> add(@RequestBody FinanceLogDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = FinanceLogDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody FinanceLogDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = FinanceLogDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody FinanceLogDto dto);
		
}