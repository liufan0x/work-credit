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
import com.anjbo.bean.finance.LendingPayDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@Api(value = "待付利息的订单相关")
@RequestMapping("/lendingPay/v")
public interface ILendingPayController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = LendingPayDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<LendingPayDto> page(@RequestBody LendingPayDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = LendingPayDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<LendingPayDto> search(@RequestBody LendingPayDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = LendingPayDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<LendingPayDto> find(@RequestBody LendingPayDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = LendingPayDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<LendingPayDto> add(@RequestBody LendingPayDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = LendingPayDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody LendingPayDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = LendingPayDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody LendingPayDto dto);
		
}