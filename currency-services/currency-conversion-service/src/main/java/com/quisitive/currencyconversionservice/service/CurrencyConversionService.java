package com.quisitive.currencyconversionservice.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.quisitive.currencyconversionservice.dto.CurrencyConversionDto;
import com.quisitive.currencyconversionservice.dto.CurrencyExchangeRateDto;
import com.quisitive.currencyconversionservice.proxy.CurrencyExchangeRateProxy;

@Service
public class CurrencyConversionService {
	
	CurrencyExchangeRateProxy proxy;
	
	public CurrencyConversionService(CurrencyExchangeRateProxy proxy) {
		super();
		this.proxy = proxy;
	}

	public CurrencyConversionDto calculateCurrencyConversion(String from, String to, BigDecimal quantity) {
		CurrencyExchangeRateDto exchangeRateValue = proxy.getExchangeRateValue(from, to);
		return new CurrencyConversionDto(from, to, quantity, exchangeRateValue.getConversionMultiple(),
				quantity.multiply(exchangeRateValue.getConversionMultiple()));
	}
}
