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
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@Api(value = "订单征信信息相关")
@RequestMapping("/credit/v")
public interface ICreditController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = CreditDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<CreditDto> page(@RequestBody CreditDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = CreditDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<CreditDto> search(@RequestBody CreditDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = CreditDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<CreditDto> find(@RequestBody CreditDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = CreditDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<CreditDto> add(@RequestBody CreditDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = CreditDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody CreditDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = CreditDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody CreditDto dto);
		
}