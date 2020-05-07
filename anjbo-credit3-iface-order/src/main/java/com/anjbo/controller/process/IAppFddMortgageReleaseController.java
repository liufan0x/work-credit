/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.process;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.process.AppFddMortgageReleaseDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:33
 * @version 1.0
 */
@Api(value = "抵押品出库相关")
@RequestMapping("/appFddMortgageRelease/v")
public interface IAppFddMortgageReleaseController {

	@ApiOperation(value = "抵押品出库提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody AppFddMortgageReleaseDto dto);
	
	@ApiOperation(value = "抵押品出库详情", httpMethod = "POST" ,response = AppFddMortgageReleaseDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<AppFddMortgageReleaseDto> processDetails(@RequestBody AppFddMortgageReleaseDto dto);
		
}