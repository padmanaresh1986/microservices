package com.quisitive.currencyconversionservice.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.quisitive.currencyconversionservice.filter.SwaggerFilter;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2);
	}
	
	@Bean
	public FilterRegistrationBean<SwaggerFilter> swaggerFilter() {
	    FilterRegistrationBean<SwaggerFilter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter(new SwaggerFilter());
	    registrationBean.addUrlPatterns("/v2/api-docs");
	    return registrationBean;
	}

}
