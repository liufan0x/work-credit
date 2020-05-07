package com.anjbo.controller.umeng;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("友盟统计")
@RequestMapping("/umeng/v")
public interface IUmengController {

	@ApiOperation(value = "推送文本", httpMethod = "POST", response = Map.class)
	@ApiImplicitParams({ 
		@ApiImplicitParam(value = "uid", name = "用户uid", required = true),
		@ApiImplicitParam(value = "message", name = "推送的消息", required = true) })
	@RequestMapping("/pushText")
	public RespStatus pushText(@RequestBody Map<String, Object> params);

}
