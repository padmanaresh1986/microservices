package com.quisitive.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.sampler.Sampler;

@Configuration
public class ApiGatewayConfig {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(req -> req.path("/currency-conversion-service/**").filters(f -> f
						.addRequestHeader("myHeader", "myHeaderValue").addRequestParameter("myParam", "myParamValue"))
						.uri("lb://currency-conversion-service"))
				.route(req -> req.path("/currency-exchange-service/**").filters(f -> f
						.addRequestHeader("myHeader", "myHeaderValue").addRequestParameter("myParam", "myParamValue"))
						.uri("lb://currency-exchange-service"))
				.build();
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
