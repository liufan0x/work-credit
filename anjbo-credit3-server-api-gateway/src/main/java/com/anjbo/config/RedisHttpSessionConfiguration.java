package com.anjbo.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@RefreshScope
@EnableRedisHttpSession
public class RedisHttpSessionConfiguration implements EnvironmentAware {
	private RelaxedPropertyResolver propertyResolver;
	private String host;
	private int port;
	private String password;
		
	/**
	 * Allows specifying a strategy for configuring and validating Redis
	 * #redis-cli config set notify-keyspace-events Egx
	 * @Author KangLG<2017年12月27日>
	 * @return
	 */
	@Bean
	public static ConfigureRedisAction configureRedisAction() {
	    return ConfigureRedisAction.NO_OP;
	}
	
	@Bean  
    public JedisConnectionFactory connectionFactory() {  
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName(this.host);
		connectionFactory.setPort(this.port);
		connectionFactory.setPassword(this.password);
        return connectionFactory;  
    }  
	
	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.redis.");		
		this.host = this.propertyResolver.getProperty("host", "localhost");
		this.port = Integer.valueOf(this.propertyResolver.getProperty("port", "6379"));
		this.password = this.propertyResolver.getProperty("password", "");
	}
	
}