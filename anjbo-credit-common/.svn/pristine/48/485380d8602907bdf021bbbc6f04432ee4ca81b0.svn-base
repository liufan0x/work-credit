package com.anjbo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.anjbo.bean.user.UserDto;
import com.anjbo.controller.BaseController;

/**
 * 全局拦截功能
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午02:26:25
 */
public class VisitTimeInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(VisitTimeInterceptor.class);
	private static ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		StopWatch stopWatch = new StopWatch(handler.toString());
		stopWatchLocal.set(stopWatch);
		stopWatch.start(handler.toString());

//		InputStream is = request.getInputStream();
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		String bb = br.readLine();
//		System.out.print("llllllllllllllllll: " + bb);
		
		request.setAttribute("uid",request.getHeader("uid"));
		request.setAttribute("deviceId",request.getHeader("deviceId"));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2, ModelAndView arg3)
	throws Exception {
		StopWatch stopWatch = stopWatchLocal.get();
		String currentPath = request.getRequestURI();
		
//		UserDto user = null;
//		String orderNo = "";
//		try {
//			BaseController baseController = new BaseController();
//			user = baseController.getUserDto(request);
//		} catch (Exception e) {
//			user = new UserDto();
//		}
//		if(user == null){
//			user = new UserDto();
//		}
		
		if (stopWatch.isRunning()) {
			stopWatch.stop();
			logger.info("access url=" + currentPath + ",total time="
					+ stopWatch.getTotalTimeMillis() + "ms"+"；操作人Uid:"+request.getAttribute("uid"));
		} 
		stopWatchLocal.set(null);
	}
}
