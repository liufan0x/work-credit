package com.anjbo.controller.api;

import org.springframework.cloud.netflix.feign.FeignClient;


@FeignClient(name="anjbo-credit3-provider-order")
public interface OrderApi extends IOrderApiController{

	

}
