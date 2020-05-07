/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.process;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.bean.process.UploadInsuranceDto;
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
@Api(value = "上传电子保单相关")
@RequestMapping("/uploadInsurance/v")
public interface IUploadInsuranceController {

	@ApiOperation(value = "上传电子保单提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody UploadInsuranceDto dto);
	
	@ApiOperation(value = "上传电子保单详情", httpMethod = "POST" ,response = UploadInsuranceDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<UploadInsuranceDto> processDetails(@RequestBody UploadInsuranceDto dto);
		
}