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
import com.anjbo.bean.risk.AuditFirstForeclosureDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:32
 * @version 1.0
 */
@Api(value = "风控-初审-出款相关")
@RequestMapping("/auditFirstForeclosure/v")
public interface IAuditFirstForeclosureController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = AuditFirstForeclosureDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<AuditFirstForeclosureDto> page(@RequestBody AuditFirstForeclosureDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = AuditFirstForeclosureDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<AuditFirstForeclosureDto> search(@RequestBody AuditFirstForeclosureDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = AuditFirstForeclosureDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<AuditFirstForeclosureDto> find(@RequestBody AuditFirstForeclosureDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = AuditFirstForeclosureDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<AuditFirstForeclosureDto> add(@RequestBody AuditFirstForeclosureDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = AuditFirstForeclosureDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody AuditFirstForeclosureDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = AuditFirstForeclosureDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody AuditFirstForeclosureDto dto);
		
}