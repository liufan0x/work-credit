/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.sgtong;



import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@Api(value = "还款计划")
@RequestMapping("/sgtongRepayment/v")
public interface ISgtongRepaymentController {
	
	@ApiOperation(value = "推送还款计划", httpMethod = "POST" )
	@RequestMapping(value = "tsYhkxx", method= {RequestMethod.POST})
	public abstract RespStatus tsYhkxx(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "查询还款计划上传结果", httpMethod = "POST" )
	@RequestMapping(value = "tsYhkxxResult", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String, Object>> tsYhkxxResult(@RequestBody Map<String,Object> map);
		
}