package com.quisitive.currencyconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.quisitive.currencyconversionservice.dto.CurrencyExchangeRateDto;

//@FeignClient(name="currency-exchange", url="localhost:8000")
@FeignClient(name="currency-exchange")
public interface CurrencyExchangeRateProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchangeRateDto getExchangeRateValue(@PathVariable String from, @PathVariable String to);

}
