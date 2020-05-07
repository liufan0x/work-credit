/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.risk;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.risk.AuditFirstDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:32
 * @version 1.0
 */
@Api(value = "风控初审相关")
@RequestMapping("/auditFirst/v")
public interface IAuditFirstController {

	@ApiOperation(value = "风控初审保存", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSave", method= {RequestMethod.POST})
	public abstract RespStatus processSave(@RequestBody AuditFirstDto dto);
	
	@ApiOperation(value = "风控初审提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody AuditFirstDto dto);
	
	@ApiOperation(value = "风控初审详情", httpMethod = "POST" ,response = AuditFirstDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<AuditFirstDto> processDetails(@RequestBody AuditFirstDto dto);
		
}