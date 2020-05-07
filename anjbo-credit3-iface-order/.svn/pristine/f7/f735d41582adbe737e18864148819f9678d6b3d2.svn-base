/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.process;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.bean.process.SignInsuranceDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2019-08-09 17:23:34
 * @version 1.0
 */
@Api(value = "签订投保单相关")
@RequestMapping("/signInsurance/v")
public interface ISignInsuranceController {

	@ApiOperation(value = "签订投保单提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody SignInsuranceDto dto);
	
	@ApiOperation(value = "签订投保单详情", httpMethod = "POST" ,response = SignInsuranceDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<SignInsuranceDto> processDetails(@RequestBody SignInsuranceDto dto);
		
}