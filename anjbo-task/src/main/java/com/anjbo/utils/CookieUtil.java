package com.anjbo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * CookieUtil Cookie管理工具
 * @author Mark
 *
 */
public class CookieUtil {

	private static final Logger log = Logger.getLogger(CookieUtil.class);

	/**
	 * 根据cookie名称获取cookie值
	 * @author Jerry
	 * @version v1.0 2013-11-26 下午05:16:42 
	 * @param request
	 * @param cookieName
	 */
	public static String getValueFromCookieName(HttpServletRequest request,String cookieName) {
		String value = "";
		Cookie[] cookies = request.getCookies();
		log.debug("cookies\t" + cookies);
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					value = cookie.getValue();
					log.debug("cookieName:" + cookieName + "\t从客户端cookie中获取到value:" + value);
					return value;
				}
			}
		}
		return value;
	}


	/**
	 * 验证session次数
	 * 
	 * @return Boolean
	 */
	public static boolean authMaxLimit(HttpSession session, String max,
			String sessionName) {
		// 次数限制
		int phoneLimit = Integer.parseInt(max);
		String sessionKey = "sessionid_try_time_" + sessionName;
		Integer tryTime = (Integer) session.getAttribute(sessionKey);
		if (tryTime == null)
			tryTime = 0;
		session.setAttribute(sessionKey, ++tryTime);// 尝试次数+1
		if (tryTime > phoneLimit) {// 如果已经超限制
			log.info(sessionName + "\tsendDownloadSMS try max times!");
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 设置cookie,自由设置过期时间
	 * 
	 * @return Boolean
	 */
	public static boolean setCookie(HttpServletResponse response,Cookie cookie) {
		cookie.setPath("/");
		response.addCookie(cookie);
		return true;
	}
	
	/**
	 * 设置cookie,设置根目录下，过期时间为默认
	 * @return Boolean
	 */
	public static boolean setCookie(HttpServletResponse response,String name,String value) {
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
		response.addCookie(cookie);
		return true;
	}

}
