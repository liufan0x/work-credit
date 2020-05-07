/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.sgtong;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.anjbo.bean.sgtong.SgtongContractInformationDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@Api(value = "合同信息表相关")
@RequestMapping("/sgtongContractInformation/v")
public interface ISgtongContractInformationController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = SgtongContractInformationDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<SgtongContractInformationDto> page(@RequestBody SgtongContractInformationDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = SgtongContractInformationDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<SgtongContractInformationDto> search(@RequestBody SgtongContractInformationDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = SgtongContractInformationDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<SgtongContractInformationDto> find(@RequestBody SgtongContractInformationDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = SgtongContractInformationDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<SgtongContractInformationDto> add(@RequestBody SgtongContractInformationDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = SgtongContractInformationDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody SgtongContractInformationDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = SgtongContractInformationDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody SgtongContractInformationDto dto);
		
}