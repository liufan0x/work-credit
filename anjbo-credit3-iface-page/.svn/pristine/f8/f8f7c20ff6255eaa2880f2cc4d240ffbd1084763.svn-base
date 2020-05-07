package com.anjbo.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;

@RequestMapping("/api/v")
@Api(value = "ConfigPage对外查询api")
public interface IConfigPageApiController {
	
	List<Map<String,Object>> selectProductCMByorderNos(@PathVariable("orderNos") String orderNos);

}