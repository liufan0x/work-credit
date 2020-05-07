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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.finance.LendingInterestDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@Api(value = "收利息相关")
@RequestMapping("/lendingInterest/v")
public interface ILendingInterestController {

	@ApiOperation(value = "收利息提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody LendingInterestDto dto);
	
	@ApiOperation(value = "收利息的详情", httpMethod = "POST" ,response = LendingInterestDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String, Object>> processDetails(@RequestBody LendingInterestDto dto);
		
}