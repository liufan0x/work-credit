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
import com.anjbo.bean.TblFinanceAfterloanListDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-12-04 15:49:31
 * @version 1.0
 */
@Api(value = "id相关")
@RequestMapping("/tblFinanceAfterloanList/v")
public interface ITblFinanceAfterloanListController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = TblFinanceAfterloanListDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<TblFinanceAfterloanListDto> page(@RequestBody TblFinanceAfterloanListDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = TblFinanceAfterloanListDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<TblFinanceAfterloanListDto> search(@RequestBody TblFinanceAfterloanListDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = TblFinanceAfterloanListDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<TblFinanceAfterloanListDto> find(@RequestBody TblFinanceAfterloanListDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = TblFinanceAfterloanListDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<TblFinanceAfterloanListDto> add(@RequestBody TblFinanceAfterloanListDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = TblFinanceAfterloanListDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody TblFinanceAfterloanListDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = TblFinanceAfterloanListDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody TblFinanceAfterloanListDto dto);
		
}