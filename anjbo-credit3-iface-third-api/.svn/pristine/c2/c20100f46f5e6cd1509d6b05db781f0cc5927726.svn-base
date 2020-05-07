package com.anjbo.controller.lineparty;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Api
@RequestMapping("/platform")
public interface IPlatformController {
	
	@ApiOperation(value = "查询信息",httpMethod = "POST",response = Map.class)
	@RequestMapping(value = "/getInsuranceFile")
	public String getInsuranceFile(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "添加信息",httpMethod = "POST",response = Map.class)
	@RequestMapping(value = "/v/getInsertFile")
	public RespStatus getInsertFile(@RequestBody Map<String,Object> map);
	
}
