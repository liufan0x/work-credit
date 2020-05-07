/*
 *Project: anjbo-credit3-serverZuul
 *File: com.anjbo.filter.AccessFilter.java  <2017年12月7日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.filter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import net.sf.json.JSONObject;

/**
 * @Author KangLG 
 * @Date 2017年12月7日 上午10:55:00
 * @version 1.0
 */
@Component
public class AccessFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if(!this.isSkip(request)){
			String login_key = new StringBuffer().append("LOGIN_").append("ANJBO-CREDIT").append("_USER").toString();
			String uid = request.getHeader("uid");
			if (null==request.getSession(false).getAttribute(login_key) && (uid == null || "".equals(uid)) ) {
				logger.warn("access token is empty");
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("code", "LOGIN_OVER_TIME");
				params.put("msg",  "登录超时");
				ctx.setResponseBody(JSONObject.fromObject(params).toString());
				ctx.setSendZuulResponse(false);
				return null;
			}
		}
		logger.info("access token ok");
		return null;
	}
	
	@SuppressWarnings("serial")
	private static final List<String> lstSkipUrl = new ArrayList<String>(){{
		add("login");
		add("logout");
	}};
	
	private boolean isSkip(HttpServletRequest request){
		String reqUrl = request.getRequestURL().toString();
		logger.info("send {} request to {}", request.getMethod(), reqUrl);
		if(!reqUrl.contains("/v/")){
			return true;
		}
		for (String str : lstSkipUrl) {
			if(reqUrl.contains(str)){
				return true;
			}
		}
		
		// session
		logger.info("============================Session:"+ request.getSession().getId());
		Enumeration<String> ens = request.getSession().getAttributeNames();
		String key;
		while (ens.hasMoreElements()) {
			key = (String)ens.nextElement();
			logger.info(String.format("%s: %s", key, request.getSession().getAttribute(key)));
		}		
		// Request Headers
		logger.info("============================Request Headers:");
		ens = request.getHeaderNames();
		while (ens.hasMoreElements()) {
			key = (String)ens.nextElement();
			logger.info(String.format("%s: %s", key, request.getHeader(key)));
		}
		return false;
	}

}
