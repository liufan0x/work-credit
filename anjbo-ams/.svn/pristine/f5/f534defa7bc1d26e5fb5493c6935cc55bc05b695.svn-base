package com.anjbo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.anjbo.common.Constants;

/**
 * 登录拦截 跳转至登录页面
 * @author limh
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(Constants.LOGIN_USER_KEY);
		if (obj == null) {
			response.sendRedirect("/login");
			return false;
		}
		return true;
	}
}