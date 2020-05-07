/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.risk;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@Api(value = "推送金融机构相关")
@RequestMapping("/allocationFund/v")
public interface IAllocationFundController {

	@ApiOperation(value = "推送金融机构提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody List<AllocationFundDto> dto);
	
	@ApiOperation(value = "推送金融机构详情", httpMethod = "POST" ,response = AllocationFundDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespData<AllocationFundDto> processDetails(@RequestBody AllocationFundDto dto);
	
	@ApiOperation(value = "预匹配资方信息", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "preMatchedFund", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String,Object>> preMatchedFund(@RequestBody AllocationFundDto dto);
}