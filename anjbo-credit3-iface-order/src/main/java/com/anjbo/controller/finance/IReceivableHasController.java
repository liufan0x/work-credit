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
import com.anjbo.bean.finance.ReceivableHasDto;
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
@Api(value = "待回款其他信息相关")
@RequestMapping("/receivableHas/v")
public interface IReceivableHasController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = ReceivableHasDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<ReceivableHasDto> page(@RequestBody ReceivableHasDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = ReceivableHasDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<ReceivableHasDto> search(@RequestBody ReceivableHasDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = ReceivableHasDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<ReceivableHasDto> find(@RequestBody ReceivableHasDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = ReceivableHasDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<ReceivableHasDto> add(@RequestBody ReceivableHasDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = ReceivableHasDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody ReceivableHasDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = ReceivableHasDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody ReceivableHasDto dto);
		
}