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
import com.anjbo.bean.contract.ContractDto;
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
@Api(value = "合同信息相关")
@RequestMapping("/contract/v")
public interface IContractController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = ContractDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<ContractDto> page(@RequestBody ContractDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = ContractDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<ContractDto> search(@RequestBody ContractDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = ContractDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractDto> find(@RequestBody ContractDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = ContractDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractDto> add(@RequestBody ContractDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = ContractDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody ContractDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = ContractDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody ContractDto dto);
		
}