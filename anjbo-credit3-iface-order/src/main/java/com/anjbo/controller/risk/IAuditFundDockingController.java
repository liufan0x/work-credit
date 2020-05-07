/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.risk;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.AuditFundDockingDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@Api(value = "资料推送相关")
@RequestMapping("/auditFundDocking/v")
public interface IAuditFundDockingController {

	@ApiOperation(value = "资料推送提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody AuditFundDockingDto dto);
	
	@ApiOperation(value = "资料推送详情", httpMethod = "POST" ,response = AuditFundDockingDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespData<AllocationFundDto> processDetails(@RequestBody AllocationFundDto dto);
		
}