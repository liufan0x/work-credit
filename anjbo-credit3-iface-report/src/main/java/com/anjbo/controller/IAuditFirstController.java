package com.anjbo.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anjbo.common.RespPageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "初审人员效率统计")
@RequestMapping("/auditFirst/v")
public interface IAuditFirstController{

	
	@ApiOperation(value = "查询初审人员效率统计", httpMethod = "POST",response = Map.class)
	@RequestMapping("query")
	public   RespPageData<Map<String, Object>> query( @RequestBody Map<String,Object> paramMap);
	
	
}
