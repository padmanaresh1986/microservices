package com.quisitive.currencyconversionservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quisitive.currencyconversionservice.config.ApplicationProperties;

@RestController
public class ApplicationPropertiesController {
	
	@Autowired
	private ApplicationProperties properties;
	
	@GetMapping("/application-properties")
	public Map<String, String> getApplicationProperties() {
		return Map.of("minimum", String.valueOf(properties.getMinimum()), "maximum",
				String.valueOf(properties.getMaximum()));
	}

}
