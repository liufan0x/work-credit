package com.anjbo.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.anjbo.bean.RespBean;
import com.anjbo.common.AnjboException;

@Aspect
@Configuration
public class ControllerAOPConfigure {

	protected Log logger = LogFactory.getLog(this.getClass());	
	
	
	@Pointcut("execution(* com.anjbo.bean.RespBean.*(..))")
	public void excudeService(){}
	
	@Around("excudeService()")
	public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
		long startTime = System.currentTimeMillis();
		RespBean<?> result;
		try {
			result = (RespBean<?>) pjp.proceed();
			logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
		} catch (Throwable e) {
			result = handlerException(pjp, e);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private RespBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
		// 已知异常
		if (e instanceof AnjboException) {
		} else {
			logger.error(pjp.getSignature() + " error ", e);
			// 未知异常是应该重点关注的，这里可以做其他操作，如通知邮件，单独写到某个文件等等。
		}
		return new RespBean(e);
	}



}

