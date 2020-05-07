package com.anjbo.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.anjbo.config.domain.ZuulRoute;

/**
 * @Author KangLG 
 * @Date 2017年12月7日 下午5:05:08
 * @version 1.0
 */
@Configuration
public class LocalConfiguration implements EnvironmentAware {
	private RelaxedPropertyResolver propertyResolver;
	/** ZUUL路由 */
	public static final List<ZuulRoute> LIST_ZUUL_ROUTE = new LinkedList<ZuulRoute>();

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, (String)null);
		
		Map<String, Object> kvMap = this.propertyResolver.getSubProperties("zuul.routes.");
		ZuulRoute zuulRoute = null;
		for (Map.Entry<String, Object> e: kvMap.entrySet()) {
			if(e.getKey().endsWith(".name")){
				zuulRoute = new ZuulRoute();
				zuulRoute.setName(String.valueOf(e.getValue()));
			}else if(e.getKey().endsWith(".path")){
				zuulRoute.setPath(String.valueOf(e.getValue()).replace("**", ""));
				LIST_ZUUL_ROUTE.add(zuulRoute);
			}
		}
	}

}
