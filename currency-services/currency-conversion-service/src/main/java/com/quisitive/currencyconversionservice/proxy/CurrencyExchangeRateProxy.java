package com.quisitive.currencyconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.quisitive.currencyconversionservice.dto.CurrencyExchangeRateDto;


//@FeignClient(name="currency-exchange", url="https://springcloud1-currency-exchange-service.azuremicroservices.io")
@FeignClient(name="currency-exchange-service",path = "currency-exchange-service")
public interface CurrencyExchangeRateProxy {
	
	@GetMapping("/exchange-rate/from/{from}/to/{to}")
	public CurrencyExchangeRateDto getExchangeRateValue(@PathVariable String from, @PathVariable String to);

}
