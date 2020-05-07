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
import com.anjbo.bean.contract.ContractListDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 18:11:02
 * @version 1.0
 */
@Api(value = "合同列表相关")
@RequestMapping("/contractList/v")
public interface IContractListController {

	@ApiOperation(value = "生成PDF", httpMethod ="POST", response = String.class)
	@RequestMapping(value = "showPDF")
	public abstract RespDataObject<String> showPDF(@RequestBody ContractListDto dto);
	
	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = ContractListDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<ContractListDto> page(@RequestBody ContractListDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = ContractListDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<ContractListDto> search(@RequestBody ContractListDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = ContractListDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractListDto> find(@RequestBody ContractListDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = ContractListDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractListDto> add(@RequestBody ContractListDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = ContractListDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody ContractListDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = ContractListDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody ContractListDto dto);
		
	@ApiOperation(value = "重置", httpMethod = "POST" ,response = ContractListDto.class)
	@RequestMapping(value = "reset", method= {RequestMethod.POST})
	public abstract RespStatus reset(@RequestBody ContractListDto dto);
	
	/*@ApiOperation(value = "关联订单信息", httpMethod = "POST" ,response = ContractListDto.class)
	@RequestMapping(value = "associatedOrder", method= {RequestMethod.POST})
	public abstract RespDataObject<ContractListDto> associatedOrder(@RequestBody ContractListDto dto);*/
	
	
}