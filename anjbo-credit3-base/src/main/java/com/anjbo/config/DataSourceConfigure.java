package com.anjbo.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RefreshScope  
@Configuration
public class DataSourceConfigure {

	@Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource(){  
        return DataSourceBuilder.create().build();  
    }
	
}
