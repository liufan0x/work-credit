/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.order;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.order.BusinfoDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:47
 * @version 1.0
 */
@Api(value = "业务资料相关")
@RequestMapping("/businfo/v")
public interface IBusinfoController {

	@ApiOperation(value = "分页查询", httpMethod = "POST" ,response = BusinfoDto.class)
	@RequestMapping(value = "/page")
	public abstract RespPageData<BusinfoDto> page(@RequestBody BusinfoDto dto);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = BusinfoDto.class)
	@RequestMapping(value = "search", method= {RequestMethod.POST})
	public abstract RespData<BusinfoDto> search(@RequestBody BusinfoDto dto);

	@ApiOperation(value = "查找", httpMethod = "POST" ,response = BusinfoDto.class)
	@RequestMapping(value = "find", method= {RequestMethod.POST})
	public abstract RespDataObject<BusinfoDto> find(@RequestBody BusinfoDto dto);

	@ApiOperation(value = "新增", httpMethod = "POST" ,response = BusinfoDto.class)
	@RequestMapping(value = "add", method= {RequestMethod.POST})
	public abstract RespDataObject<BusinfoDto> add(@RequestBody BusinfoDto dto);

	@ApiOperation(value = "编辑", httpMethod = "POST" ,response = BusinfoDto.class)
	@RequestMapping(value = "edit", method= {RequestMethod.POST})
	public abstract RespStatus edit(@RequestBody BusinfoDto dto);

	@ApiOperation(value = "删除", httpMethod = "POST" ,response = BusinfoDto.class)
	@RequestMapping(value = "delete", method= {RequestMethod.POST})
	public abstract RespStatus delete(@RequestBody BusinfoDto dto);
	
	@ApiOperation(value = "影像资料一键下载", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "/getBusinfoAndTypeNames")
	public abstract RespDataObject<String> getBusinfoAndTypeNames(@RequestBody Map<String, Object> map);
		
}