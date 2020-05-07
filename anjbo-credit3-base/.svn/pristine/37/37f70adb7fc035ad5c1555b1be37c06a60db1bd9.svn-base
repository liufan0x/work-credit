package com.anjbo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.anjbo.common.Constants;

public class BaseController {
	
	protected Log logger = LogFactory.getLog(this.getClass());	
	
	@Autowired protected HttpServletRequest request;
	
	/**
	 * 获取当前登录人uid
	 * @param request
	 * @return
	 */
	public String getUid(){
		String uid = request.getHeader("uid") == null ? "" : (String) request.getHeader("uid");
		if(StringUtils.isNotEmpty(uid)){
			return uid;
		}else{
			if(request.getSession(false).getAttribute(Constants.LOGIN_USER_KEY) != null) {
				return request.getSession(false).getAttribute(Constants.LOGIN_USER_KEY).toString();
			}
			return null;
		}
	}
	
}
