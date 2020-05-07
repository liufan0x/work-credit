package com.anjbo.controller.erongsuo;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="e融所")
@RequestMapping(value="/erongsuo")
public interface IErongSuoAbutmentController {

	 @ApiOperation(value = "测试接口",httpMethod = "POST",response = RespStatus.class)
	    @RequestMapping("/v/texts")
	    public RespStatus texts(@RequestBody Map<String,Object> map);
}
