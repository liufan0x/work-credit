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
import com.anjbo.bean.risk.AuditJusticeDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@Api(value = "法务审批相关")
@RequestMapping("/auditJustice/v")
public interface IAuditJusticeController {

	@ApiOperation(value = "法务审批提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody AuditJusticeDto dto);
	
	@ApiOperation(value = "法务审批详情", httpMethod = "POST" ,response = AuditJusticeDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<AuditJusticeDto> processDetails(@RequestBody AuditJusticeDto dto);
		
}