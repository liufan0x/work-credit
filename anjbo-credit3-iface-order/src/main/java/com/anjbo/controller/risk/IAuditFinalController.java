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
import com.anjbo.bean.risk.AuditFinalDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@Api(value = "风控终审相关")
@RequestMapping("/auditFinal/v")
public interface IAuditFinalController {


	@ApiOperation(value = "风控终审提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody AuditFinalDto dto);
	
	@ApiOperation(value = "风控终审详情", httpMethod = "POST" ,response = AuditFinalDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<AuditFinalDto> processDetails(@RequestBody AuditFinalDto dto);
	
	@ApiOperation(value = "上报首席风险官审批", httpMethod = "POST" ,response = AuditFinalDto.class)
	@RequestMapping(value = "processReportOfficer", method= {RequestMethod.POST})
	public abstract RespStatus processReportOfficer(@RequestBody AuditFinalDto finalAudit);
	
	@ApiOperation(value = "上报复核审批", httpMethod = "POST" ,response = AuditFinalDto.class)
	@RequestMapping(value = "processReportReview", method= {RequestMethod.POST})
	public abstract RespStatus processReportReview(@RequestBody AuditFinalDto finalAudit);
		
}