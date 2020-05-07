package com.anjbo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/expense")
public interface IExpenseController {

	@RequestMapping(value = "/add")
	public String addExpense(String userId, Integer money, String descption);
	
	@RequestMapping(value = "/list")
	public Object list(String userId);
	
	@RequestMapping(value = "/apply")
	public String apply(String taskId);
	
	@RequestMapping(value = "/reject")
	public String reject(String taskId);
	
	@RequestMapping(value = "/processDiagram")
	public void genProcessDiagram(HttpServletResponse httpServletResponse,String processId) throws Exception ;
	
}
