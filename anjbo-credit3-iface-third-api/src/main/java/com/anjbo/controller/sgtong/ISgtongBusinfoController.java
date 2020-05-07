/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.sgtong;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import com.anjbo.bean.sgtong.SgtongBusinfoDto;
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
@Api(value = "影像资料表相关")
@RequestMapping("/sgtongBusinfo/v")
public interface ISgtongBusinfoController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = SgtongBusinfoDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<SgtongBusinfoDto> page(@RequestBody SgtongBusinfoDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = SgtongBusinfoDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<SgtongBusinfoDto> search(@RequestBody SgtongBusinfoDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = SgtongBusinfoDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<SgtongBusinfoDto> find(@RequestBody SgtongBusinfoDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = SgtongBusinfoDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<SgtongBusinfoDto> add(@RequestBody SgtongBusinfoDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = SgtongBusinfoDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody SgtongBusinfoDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = SgtongBusinfoDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody SgtongBusinfoDto dto);
	
	@ApiOperation(value = "查询影像资料列明", httpMethod = "POST" ,response = SgtongBusinfoDto.class)
	@RequestMapping(value = "/busInfoTypes", method= {RequestMethod.POST})
	RespDataObject<List<Map<String, Object>>> busInfoTypes(Map<String, Object> dto);
		
    @RequestMapping("/stgbatchAddImage")
    public RespStatus batchAddImage(@RequestBody List<Map<String,Object>> list);
	
    
    @ApiOperation(value = "批量删除影像资料",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(value = "ids",name = "数据库图片id,多个用,隔开")
    @RequestMapping("/stgbatchDeleteImg")
    public RespStatus stgbatchDeleteImg(@RequestBody Map<String,Object> map);
}