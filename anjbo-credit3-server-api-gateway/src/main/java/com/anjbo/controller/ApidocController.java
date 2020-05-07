/*
 *Project: anjbo-credit3-serverZuul
 *File: com.anjbo.controller.ApidocController.java  <2017年12月7日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.config.LocalConfiguration;

/**
 * @Author KangLG 
 * @Date 2017年12月7日 下午5:04:32
 * @version 1.0
 */
@RequestMapping({"","/credit/apidoc", "/credit/apidoc.html", "/credit/apidoc.htm"})
@Controller
public class ApidocController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpServletRequest request){
		request.setAttribute("listZuulRoute", LocalConfiguration.LIST_ZUUL_ROUTE);
		return "apidoc/index";
	}
	
}
