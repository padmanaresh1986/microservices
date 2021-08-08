package com.quisitive.currencyexchangeservice.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
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
	
	@GetMapping("/exchange-rate/from/{from}/to/{to}")
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
	
	@GetMapping("/exchange-rate/all-rates")
	public  List<CurrencyExchangeRateDto> getAllExchangeRates() {
		return service.getAllExchangeRates();
	}
	
	@PostMapping("/exchange-rate/save")
	public CurrencyExchangeRateDto save(@RequestBody CurrencyExchangeRateDto dto) {
		logger.info("Got Request for save from {} to {} quantity {}", dto.getFrom(), dto.getTo(), dto.getConversionMultiple());
		return service.save(dto);
	}
	
	@GetMapping("/exchange-rate/load/{sourceCurrency}")
	public List<CurrencyExchangeRateDto> load(@PathVariable String sourceCurrency) {
		logger.info("Loading currency rates for source currency {} ", sourceCurrency);
		String exchangeRatesJsaon = new RestTemplate().getForObject ("https://api.exchangerate-api.com/v4/latest/"+sourceCurrency,String.class);
		Map<String,Number> rates = JsonPath.parse(exchangeRatesJsaon).read("$.rates");
		rates.entrySet().stream().filter(Objects::nonNull).map(entry -> {
			String k = entry.getKey();
			Number v = entry.getValue();
			CurrencyExchangeRateDto dto = null;
			if (v instanceof Integer) {
				dto = new CurrencyExchangeRateDto(null, sourceCurrency, k,
						new BigDecimal((Integer) v).round(MathContext.DECIMAL32));
			} else if (v instanceof Double) {
				dto = new CurrencyExchangeRateDto(null, sourceCurrency, k,
						new BigDecimal((Double) v).round(MathContext.DECIMAL32));
			}
			return dto;
		}).forEach(service::save);
		return service.getAllExchangeRates();
	}
	
	@GetMapping("/exchange-rate/clear")
	public Boolean clear() {
		logger.info("clearing all currency rates");
		return service.deleteAll();
	}
}
