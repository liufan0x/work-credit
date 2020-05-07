package com.anjbo.controller.ccb;

import com.anjbo.common.RespDataObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Api
@RequestMapping("/ccb/v")
public interface ICCBController {
	
	@ApiOperation(value = "匹配客户经理信息",httpMethod = "POST",response = Map.class)
	@RequestMapping(value = "/getMatchCusManager")
	public RespDataObject<Map<String, Object>> getMatchCusManager(@RequestBody Map<String,Object> map);
	
	
}
