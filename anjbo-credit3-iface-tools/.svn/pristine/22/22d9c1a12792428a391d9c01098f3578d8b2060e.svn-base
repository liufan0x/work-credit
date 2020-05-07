/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.contract;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.anjbo.bean.contract.ContractListRecordDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 20:08:34
 * @version 1.0
 */
@Api(value = "合同列表相关")
@RequestMapping("/contractListRecord/v")
public interface IContractListRecordController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = ContractListRecordDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<ContractListRecordDto> page(@RequestBody ContractListRecordDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = ContractListRecordDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<ContractListRecordDto> search(@RequestBody ContractListRecordDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = ContractListRecordDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractListRecordDto> find(@RequestBody ContractListRecordDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = ContractListRecordDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractListRecordDto> add(@RequestBody ContractListRecordDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = ContractListRecordDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody ContractListRecordDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = ContractListRecordDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody ContractListRecordDto dto);
	
	@ApiOperation(value = "下载路径返回", httpMethod = "POST" ,response = RespDataObject.class)
	@RequestMapping(value = "pathReturn", method= {RequestMethod.POST})
	public abstract RespDataObject<String> pathReturn(@RequestBody ContractListRecordDto dto);
		
  }