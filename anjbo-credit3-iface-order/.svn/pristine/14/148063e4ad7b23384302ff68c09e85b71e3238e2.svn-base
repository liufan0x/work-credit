/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.order;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.order.BaseCustomerDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:45
 * @version 1.0
 */
@Api(value = "客户信息相关")
@RequestMapping("/baseCustomer/v")
public interface IBaseCustomerController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = BaseCustomerDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<BaseCustomerDto> page(@RequestBody BaseCustomerDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = BaseCustomerDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<BaseCustomerDto> search(@RequestBody BaseCustomerDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = BaseCustomerDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<BaseCustomerDto> find(@RequestBody BaseCustomerDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = BaseCustomerDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<BaseCustomerDto> add(@RequestBody BaseCustomerDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = BaseCustomerDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody BaseCustomerDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = BaseCustomerDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody BaseCustomerDto dto);
	
	@ApiOperation(value = "查询orders集合", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "/allCustomerNos", method= {RequestMethod.POST})
	public abstract List<Map<String ,Object>> allCustomerNos(@RequestBody Map<Object, String> map);
		
}