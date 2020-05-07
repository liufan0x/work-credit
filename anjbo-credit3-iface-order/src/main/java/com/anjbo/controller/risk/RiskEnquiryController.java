/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2019 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.risk;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.risk.RiskEnquiryDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2019-01-11 18:08:27
 * @version 1.0
 */
@Api(value = "询价表相关")
@RequestMapping("/tblRiskEnquiry/v")
public interface RiskEnquiryController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = RiskEnquiryDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<RiskEnquiryDto> page(@RequestBody RiskEnquiryDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = RiskEnquiryDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<RiskEnquiryDto> search(@RequestBody RiskEnquiryDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = RiskEnquiryDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<RiskEnquiryDto> find(@RequestBody RiskEnquiryDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = RiskEnquiryDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<RiskEnquiryDto> add(@RequestBody RiskEnquiryDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = RiskEnquiryDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody RiskEnquiryDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = RiskEnquiryDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody RiskEnquiryDto dto);
		
}