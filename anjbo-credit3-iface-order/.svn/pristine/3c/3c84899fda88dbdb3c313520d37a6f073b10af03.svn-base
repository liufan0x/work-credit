/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.element;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:31
 * @version 1.0
 */
@Api(value = "回款方式相关")
@RequestMapping("/paymentType/v")
public interface IPaymentTypeController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = PaymentTypeDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<PaymentTypeDto> page(@RequestBody PaymentTypeDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = PaymentTypeDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<PaymentTypeDto> search(@RequestBody PaymentTypeDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = PaymentTypeDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<PaymentTypeDto> find(@RequestBody PaymentTypeDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = PaymentTypeDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<PaymentTypeDto> add(@RequestBody PaymentTypeDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = PaymentTypeDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody PaymentTypeDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = PaymentTypeDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody PaymentTypeDto dto);
		
}