package com.anjbo.controller.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name="anjbo-credit3-provider-third-api")
public interface ThirdApi extends IThirdApiController{
	
	

}
