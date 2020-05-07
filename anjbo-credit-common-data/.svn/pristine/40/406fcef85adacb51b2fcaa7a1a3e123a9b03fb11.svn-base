package com.anjbo.processor;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

/**
 * 应用启动初始化
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午02:26:14
 */
@Controller
public class InitProcessor implements
		ApplicationListener<ContextRefreshedEvent> {
	private Logger logger = Logger.getLogger(InitProcessor.class);
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
		if (event.getApplicationContext().getParent() == null) {
			logger.info("应用启动初始化123...");
		}
	}
}
