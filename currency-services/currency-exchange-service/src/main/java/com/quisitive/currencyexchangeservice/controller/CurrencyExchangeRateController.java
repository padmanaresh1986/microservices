package com.quisitive.currencyexchangeservice.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jayway.jsonpath.JsonPath;
import com.quisitive.currencyexchangeservice.dto.CurrencyExchangeRate;
import com.quisitive.currencyexchangeservice.exception.ExchangeRateNotFoundException;
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
	public CurrencyExchangeRate getExchangeRateValue(@PathVariable String from, @PathVariable String to) {
		logger.info("Got Request from {} to {}", from, to);
		CurrencyExchangeRate exchangeRateValue = service.getExchangeRateValue(from, to);
		if (Objects.isNull(exchangeRateValue)) {
			throw new ExchangeRateNotFoundException("Unable to Find data for " + from + " to " + to);
		}
		logger.info("Sending Back Exchange Rate from  {} to {} is {}", from, to,
				exchangeRateValue.getConversionMultiple());
		return exchangeRateValue;
	}
	
	@GetMapping("/exchange-rate/all-rates")
	public  List<CurrencyExchangeRate> getAllExchangeRates() {
		return service.getAllExchangeRates();
	}
	
	@PostMapping("/exchange-rate/save")
	public ResponseEntity<Object> save(@Valid @RequestBody CurrencyExchangeRate dto) {
		logger.info("Got Request for save from {} to {} quantity {}", dto.getFrom(), dto.getTo(),
				dto.getConversionMultiple());
		CurrencyExchangeRate savedDto = service.save(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedDto.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/exchange-rate/load/{sourceCurrency}")
	public List<CurrencyExchangeRate> load(@PathVariable String sourceCurrency) {
		logger.info("Loading currency rates for source currency {} ", sourceCurrency);
		String exchangeRatesJsaon = new RestTemplate().getForObject ("https://api.exchangerate-api.com/v4/latest/"+sourceCurrency,String.class);
		Map<String,Number> rates = JsonPath.parse(exchangeRatesJsaon).read("$.rates");
		rates.entrySet().stream().filter(Objects::nonNull).map(entry -> {
			String k = entry.getKey();
			Number v = entry.getValue();
			CurrencyExchangeRate dto = null;
			if (v instanceof Integer) {
				dto = new CurrencyExchangeRate(null, sourceCurrency, k,
						new BigDecimal((Integer) v).round(MathContext.DECIMAL32));
			} else if (v instanceof Double) {
				dto = new CurrencyExchangeRate(null, sourceCurrency, k,
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
	
	@DeleteMapping("/exchange-rate/delete/{id}")
	public void delete(@PathVariable Long id) {
		 service.delete(id);
	}
}
