package com.quisitive.currencyconversionservice.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.quisitive.currencyconversionservice.dto.CurrencyConversionDto;
import com.quisitive.currencyconversionservice.service.CurrencyConversionService;

@RestController
public class CurrencyConversionController {
	
	Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);
	private CurrencyConversionService service;

	public CurrencyConversionController(CurrencyConversionService service) {
		super();
		this.service = service;
	}

	@GetMapping("/convert/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionDto calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
				logger.info("Got Request from {} to {} quantity {}", from, to, quantity);
		CurrencyConversionDto dto = service.calculateCurrencyConversion(from, to, quantity);	
		logger.info("Sending Back Calculated Result  from {} to {} for quantity {} is {}", from, to , dto.getQuantity(), dto.getTotalCalculatedAmount());
		return dto;
	}
}
