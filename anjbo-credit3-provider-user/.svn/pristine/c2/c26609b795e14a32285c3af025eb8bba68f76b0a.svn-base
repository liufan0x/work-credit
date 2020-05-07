/*
 *Project: anjbo-credit3-user-provider
 *File: com.anjbo.config.KaptchaConfiguration.java  <2017年12月8日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Component
public class KaptchaConfiguration {
	@Bean  
    public DefaultKaptcha getDefaultKaptcha(){  
        com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();  
        Properties properties = new Properties();  
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");  
        properties.setProperty("kaptcha.border.color", "105,179,90");  
        properties.setProperty("kaptcha.image.width", "120");  
        properties.setProperty("kaptcha.image.height", "40");  
        
        properties.setProperty("kaptcha.textproducer.char.string", "123456789");  
        properties.setProperty("kaptcha.textproducer.char.space", "8");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");  
        properties.setProperty("kaptcha.textproducer.font.color", "black");  
        properties.setProperty("kaptcha.textproducer.font.size", "30");  
        properties.setProperty("kaptcha.session.key", "code");          
        Config config = new Config(properties);  
        defaultKaptcha.setConfig(config);  
          
        return defaultKaptcha;  
    }  
}
