package com.anjbo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.anjbo.bean.system.EgDto;
import com.anjbo.common.Constants;

@Controller
public class BaseController {

	/**
	 * 获取当前登录用户信息
	 * @param request
	 * @return
	 */
	public EgDto getUserDto(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (EgDto) session.getAttribute(Constants.LOGIN_USER_KEY);
	}
	
}
