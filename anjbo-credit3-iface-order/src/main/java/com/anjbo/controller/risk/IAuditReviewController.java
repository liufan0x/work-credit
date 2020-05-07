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
import com.anjbo.bean.risk.AuditReviewDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@Api(value = "风控复核审批相关")
@RequestMapping("/auditReview/v")
public interface IAuditReviewController {

	@ApiOperation(value = "风控复核审批提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody AuditReviewDto dto);
	
	@ApiOperation(value = "风控复核审批提交到首席风险官", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processReportOfficer", method= {RequestMethod.POST})
	public RespStatus processReportOfficer(@RequestBody AuditReviewDto reviewAuditDto);
	
	@ApiOperation(value = "风控复核审批详情", httpMethod = "POST" ,response = AuditReviewDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<AuditReviewDto> processDetails(@RequestBody AuditReviewDto dto);
	
		
}