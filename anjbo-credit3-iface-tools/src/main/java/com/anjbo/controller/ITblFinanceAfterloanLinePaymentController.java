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
import com.anjbo.bean.TblFinanceAfterloanLinePaymentDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-29 09:37:16
 * @version 1.0
 */
@Api(value = "线上线下还款记录")
@RequestMapping("/financeAfterloanLinePayment/v")
public interface ITblFinanceAfterloanLinePaymentController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = TblFinanceAfterloanLinePaymentDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<TblFinanceAfterloanLinePaymentDto> page(@RequestBody TblFinanceAfterloanLinePaymentDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = TblFinanceAfterloanLinePaymentDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<TblFinanceAfterloanLinePaymentDto> search(@RequestBody TblFinanceAfterloanLinePaymentDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = TblFinanceAfterloanLinePaymentDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<TblFinanceAfterloanLinePaymentDto> find(@RequestBody TblFinanceAfterloanLinePaymentDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = TblFinanceAfterloanLinePaymentDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<TblFinanceAfterloanLinePaymentDto> add(@RequestBody TblFinanceAfterloanLinePaymentDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = TblFinanceAfterloanLinePaymentDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody TblFinanceAfterloanLinePaymentDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = TblFinanceAfterloanLinePaymentDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody TblFinanceAfterloanLinePaymentDto dto);
		
	@ApiOperation(value = "查询陕国还款状态", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/searchSgtLinePaymentStatus", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String,Object>> searchSgtLinePaymentStatus(@RequestBody  Map<String,Object> dto);
	
	@ApiOperation(value = "重新推送还款", httpMethod = "POST" ,response = TblFinanceAfterloanLinePaymentDto.class)
	@RequestMapping(value = "repushPayment", method= {RequestMethod.POST})
	public abstract RespDataObject<TblFinanceAfterloanLinePaymentDto> repushPayment(@RequestBody TblFinanceAfterloanLinePaymentDto dto);
	
	@ApiOperation(value = "获取提前清贷剩余相关金额", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/getPrepaymentBalance", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String,Object>> getPrepaymentBalance(@RequestBody  Map<String,Object> dto);
	
	
}