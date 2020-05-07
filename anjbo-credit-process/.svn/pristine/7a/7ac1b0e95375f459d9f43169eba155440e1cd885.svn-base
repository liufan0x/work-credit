package com.anjbo.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.common.RespData;
import com.anjbo.common.RespHelper;
import com.anjbo.service.EgService;

@Controller
@RequestMapping("/order")
public class EgController extends BaseController{
	
	@Resource
	private EgService egService;
	
	@ResponseBody
	@RequestMapping(value = "/test") 
	@SuppressWarnings("static-access")
	public RespData<Map<String, Object>> test(HttpServletRequest request,@RequestBody Map<String, Object> params){
		return new RespHelper().setSuccessData(new RespData<Map<String, Object>>(), egService.test());
	}
	
}
