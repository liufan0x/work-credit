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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.anjbo.bean.sgtong.SgtongBorrowerInformationDto;
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
@Api(value = "借款人信息相关")
@RequestMapping("/sgtongBorrowerInformation/v")
public interface ISgtongBorrowerInformationController {
	
	@ApiOperation(value = "Sftp上传测试", httpMethod ="POST", response = RespStatus.class)
	@RequestMapping(value = "showSftp")
	public abstract RespDataObject<String> showSftp(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "余额查询", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "/text")
	public abstract RespDataObject<Map<String, Object>> text(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "测试", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "/texts")
	public abstract RespDataObject<Map<String, Object>> texts(@RequestBody Map<String,Object> map);


	@ApiOperation(value = "推送资料信息", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "/pushBorrowerInformation", method= {RequestMethod.POST})
	public abstract RespData<SgtongBorrowerInformationDto> pushBorrowerInformation(@RequestBody  SgtongBorrowerInformationDto dto);
	
	@ApiOperation(value = "推送影像资料", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "/pushBusinfo", method= {RequestMethod.POST})
	public abstract RespData<SgtongBorrowerInformationDto> pushBusinfo(@RequestBody  SgtongBorrowerInformationDto dto);
	
	
	@ApiOperation(value = "查询推送审核信息", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "/searchPushStatus", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String,Object>> searchPushStatus(@RequestBody  SgtongBorrowerInformationDto dto);
	
	@ApiOperation(value = "查询推送状态", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "/searchPushStatus2", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String,Object>> searchPushStatus2(@RequestBody  SgtongBorrowerInformationDto dto);
	
	@ApiOperation(value = "查询陕国资料投默认信息", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/searchSgtInfo", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String,Object>> searchSgtInfo(@RequestBody  Map<String,Object> dto);
	
	
	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<SgtongBorrowerInformationDto> page(@RequestBody SgtongBorrowerInformationDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<SgtongBorrowerInformationDto> search(@RequestBody SgtongBorrowerInformationDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<SgtongBorrowerInformationDto> find(@RequestBody SgtongBorrowerInformationDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<SgtongBorrowerInformationDto> add(@RequestBody SgtongBorrowerInformationDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody SgtongBorrowerInformationDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody SgtongBorrowerInformationDto dto);
	
	@ApiOperation(value = "调进件批量申请接口（异步）【2101】", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "sgtLending", method= {RequestMethod.POST})
	public abstract RespStatus sgtLending(@RequestBody SgtongBorrowerInformationDto dto);
		
}