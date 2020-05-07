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
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@Api(value = "罚息相关")
@RequestMapping("/receivablePay/v")
public interface IReceivablePayController {

	@ApiOperation(value = "罚息提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody ReceivablePayDto dto);
	
	@ApiOperation(value = "罚息详情", httpMethod = "POST" ,response = ReceivablePayDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<ReceivablePayDto> processDetails(@RequestBody ReceivablePayDto dto);
	
		
}