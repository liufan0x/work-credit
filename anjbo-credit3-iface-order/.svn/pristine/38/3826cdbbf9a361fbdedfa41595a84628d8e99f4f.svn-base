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
import com.anjbo.bean.process.AppFacesignDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:33
 * @version 1.0
 */
@Api(value = "面签相关")
@RequestMapping("/appFacesign/v")
public interface IAppFacesignController {

	@ApiOperation(value = "面签提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody AppFacesignDto dto);
	
	@ApiOperation(value = "面签详情", httpMethod = "POST" ,response = AppFacesignDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<AppFacesignDto> processDetails(@RequestBody AppFacesignDto dto);
	
	@ApiOperation(value = "建立签约关系", httpMethod = "POST" ,response = AppFacesignDto.class)
	@RequestMapping(value = "signVerificationCode", method= {RequestMethod.POST})
	public abstract RespStatus signVerificationCode(@RequestBody AppFacesignDto dto);
	
	@ApiOperation(value = "签约", httpMethod = "POST" ,response = AppFacesignDto.class)
	@RequestMapping(value = "sign", method= {RequestMethod.POST})
	public abstract RespStatus sign(@RequestBody AppFacesignDto dto);
		
}