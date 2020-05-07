/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.contract;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.contract.ContractGroupDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 12:22:20
 * @version 1.0
 */
@Api(value = "合同分组相关")
@RequestMapping("/contractGroup/v")
public interface IContractGroupController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = ContractGroupDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<ContractGroupDto> page(@RequestBody ContractGroupDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = ContractGroupDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<ContractGroupDto> search(@RequestBody ContractGroupDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = ContractGroupDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractGroupDto> find(@RequestBody ContractGroupDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = ContractGroupDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractGroupDto> add(@RequestBody ContractGroupDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = ContractGroupDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody ContractGroupDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = ContractGroupDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody ContractGroupDto dto);
		
}