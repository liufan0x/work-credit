/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.finance;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@Api(value = "申请放款的订单相关")
@RequestMapping("/applyLoan/v")
public interface IApplyLoanController {

	@ApiOperation(value = "申请放款提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody ApplyLoanDto dto);
	
	@ApiOperation(value = "申请放款详情", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String, Object>> processDetails(@RequestBody ApplyLoanDto dto);
	
	@ApiOperation(value = "校验银行卡户名", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "processValidBankCardName", method= {RequestMethod.POST})
	public abstract RespStatus processValidBankCardName(@RequestBody ApplyLoanDto loanDto);
		
}