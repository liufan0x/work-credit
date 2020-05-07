package com.anjbo.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.Rc4Util;

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
		//接口不需要登陆
		try {
			String key = request.getHeader("key");
			key = Rc4Util.rc4Decrypt(key);
			key = key.substring(64, key.length());
			if(key.substring(0,24).equals("67dd646190413680e8c8874f") && Rc4Util.rc4Decrypt(key.substring(24, key.length())).equals(DateUtil.getNowyyyyMMdd(new Date())) ){
				return true;
			}
		} catch (Exception e) {
			
		}
		String uid = request.getAttribute("uid")==null ? "" :  request.getAttribute("uid")+"";
		String deviceId = request.getAttribute("deviceId")==null ? "" :  request.getAttribute("deviceId")+"";
		//登陆拦截
		System.out.println(Constants.LOGIN_USER_KEY);
		if (session.getAttribute(Constants.LOGIN_USER_KEY) != null || RedisOperator.getString(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO + MD5Utils.MD5Encode(uid) + ":" + MD5Utils.MD5Encode(deviceId)) != null) {
			return true;
		}else{
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
		        out = response.getWriter();  
				RespStatus resp = new RespStatus(RespStatusEnum.LOGIN_OVER_TIME.getCode(), RespStatusEnum.LOGIN_OVER_TIME.getMsg());
				JSONObject responseJSONObject = JSONObject.fromObject(resp);  
		        out.append(responseJSONObject.toString()); 
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    } finally {  
		        if (out != null) {  
		            out.close();  
		        }  
		    }
			return false;
		}
	}
	
	
	
}