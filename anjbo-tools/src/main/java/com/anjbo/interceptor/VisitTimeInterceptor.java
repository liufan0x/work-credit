package com.anjbo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		StopWatch stopWatch = stopWatchLocal.get();
		String currentPath = request.getRequestURI();
		if (stopWatch.isRunning()) {
			stopWatch.stop();
			logger.info("access url=" + currentPath + ",total time="
					+ stopWatch.getTotalTimeMillis() + "ms");
		}
		stopWatchLocal.set(null);
	}
}
