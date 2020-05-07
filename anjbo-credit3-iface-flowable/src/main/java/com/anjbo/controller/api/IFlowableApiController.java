package com.anjbo.controller.api;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "流程相关相关")
@RequestMapping("/flowable/v")
public interface IFlowableApiController {
	
//	@ApiOperation(value = "启动流程", httpMethod = "POST" ,response = Map.class)
//	@RequestMapping(value = "startProcess", method= {RequestMethod.POST})
//	void startProcess(@RequestBody Map<String, Object> params);
//
//	@ApiOperation(value = "完成当前流程", httpMethod = "POST" ,response = Map.class)
//	@RequestMapping(value = "completeProcess", method= {RequestMethod.POST})
//	Map<String, Object> completeProcess(@RequestBody Map<String, Object> params);
//	
//	@ApiOperation(value = "转交处理人", httpMethod = "POST" ,response = Map.class)
//	@RequestMapping(value = "transferHandlePeople", method= {RequestMethod.POST})
//	Map<String, Object> transferHandlePeople(@RequestBody Map<String, Object> params);
//	
//	@ApiOperation(value = "转交流程", httpMethod = "POST" ,response = Map.class)
//	@RequestMapping(value = "transferProcess", method= {RequestMethod.POST})
//	Map<String, Object> transferProcess(@RequestBody Map<String, Object> params);
//	
//	@ApiOperation(value = "流水列表", httpMethod = "POST" ,response = Map.class)
//	@RequestMapping(value = "flowList", method= {RequestMethod.POST})
//	Map<String, Object> flowList(@RequestBody Map<String, Object> params);
//	
//	@ApiOperation(value = "待处理订单", httpMethod = "POST" ,response = Map.class)
//	@RequestMapping(value = "taskList", method= {RequestMethod.POST})
//	Map<String, Object> taskList(@RequestBody Map<String, Object> params);
	
}
