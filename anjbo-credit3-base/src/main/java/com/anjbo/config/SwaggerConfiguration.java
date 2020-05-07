/*
 *Project: anjbo-credit3-parent-provider
 *File: com.anjbo.config.SwaggerConfiguration.java  <2017年12月7日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author KangLG 
 * @Date 2017年12月7日 下午4:54:12
 * @version 1.0
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration extends WebMvcConfigurerAdapter implements
		EnvironmentAware {

	private String basePackage;
    private String creatName;
    private String serviceName;
    private RelaxedPropertyResolver propertyResolver;
    private String description;

    public SwaggerConfiguration() {
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars*"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }

    @Bean
    public Docket createRestApi() {
        return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage(this.basePackage)).paths(PathSelectors.any()).build();
    }

    @SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title(this.serviceName + " Restful APIs").description(this.description).contact(this.creatName).version("1.0").build();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, (String)null);
        this.basePackage = this.propertyResolver.getProperty("swagger.basepackage");
        this.creatName = this.propertyResolver.getProperty("swagger.service.developer");
        this.serviceName = this.propertyResolver.getProperty("swagger.service.name");
        this.description = this.propertyResolver.getProperty("swagger.service.description");
    }
    
}
