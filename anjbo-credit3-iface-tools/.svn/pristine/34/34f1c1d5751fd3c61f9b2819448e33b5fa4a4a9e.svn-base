/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.TblOrderBaseHouseLendingDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-29 09:37:27
 * @version 1.0
 */
@Api(value = "id相关")
@RequestMapping("/tblOrderBaseHouseLending/v")
public interface ITblOrderBaseHouseLendingController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = TblOrderBaseHouseLendingDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<TblOrderBaseHouseLendingDto> page(@RequestBody TblOrderBaseHouseLendingDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = TblOrderBaseHouseLendingDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<TblOrderBaseHouseLendingDto> search(@RequestBody TblOrderBaseHouseLendingDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = TblOrderBaseHouseLendingDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<TblOrderBaseHouseLendingDto> find(@RequestBody TblOrderBaseHouseLendingDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = TblOrderBaseHouseLendingDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<TblOrderBaseHouseLendingDto> add(@RequestBody TblOrderBaseHouseLendingDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = TblOrderBaseHouseLendingDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody TblOrderBaseHouseLendingDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = TblOrderBaseHouseLendingDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody TblOrderBaseHouseLendingDto dto);
		
}