package com.quisitive.currencyexchangeservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quisitive.currencyexchangeservice.dto.CurrencyExchangeRateDto;
import com.quisitive.currencyexchangeservice.service.CurrencyExchangeRateService;

@RestController()
public class CurrencyExchangeRateController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeRateController.class);

	CurrencyExchangeRateService service;

	public CurrencyExchangeRateController(CurrencyExchangeRateService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchangeRateDto getExchangeRateValue(@PathVariable String from, @PathVariable String to) {
		logger.info("Got Request from {} to {}", from, to);
		CurrencyExchangeRateDto exchangeRateValue = service.getExchangeRateValue(from, to);
		if(exchangeRateValue ==null) {
			throw new RuntimeException
				("Unable to Find data for " + from + " to " + to);
		}
		logger.info("Sending Back Exchange Rate from  {} to {} is {}", from, to , exchangeRateValue.getConversionMultiple());
		return exchangeRateValue;
	}
	
	@GetMapping("/currency-exchange/all-rates")
	public  List<CurrencyExchangeRateDto> getAllExchangeRates() {
		return service.getAllExchangeRates();
	}
	
	@PostMapping("/currency-exchange/save")
	public CurrencyExchangeRateDto save(@RequestBody CurrencyExchangeRateDto dto) {
		logger.info("Got Request for save from {} to {} quantity {}", dto.getFrom(), dto.getTo(), dto.getConversionMultiple());
		return service.save(dto);
	}
}
