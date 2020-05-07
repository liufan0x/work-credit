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
import com.anjbo.bean.process.AuditManagerDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@Api(value = "分配订单相关")
@RequestMapping("/auditManager/v")
public interface IAuditManagerController {
	
	@ApiOperation(value = "分配订单提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody AuditManagerDto dto);
	
	@ApiOperation(value = "分配订单详情", httpMethod = "POST" ,response = AuditManagerDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<AuditManagerDto> processDetails(@RequestBody AuditManagerDto dto);
	
}