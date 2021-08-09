package com.quisitive.currencyexchangeservice.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quisitive.currencyexchangeservice.dto.CurrencyExchangeRate;
import com.quisitive.currencyexchangeservice.entity.CurrencyExchangeRateEntity;
import com.quisitive.currencyexchangeservice.repository.CurrencyExchangeRateRepository;

@Service
public class CurrencyExchangeRateService {
	
	CurrencyExchangeRateRepository repository;

	public CurrencyExchangeRateService(CurrencyExchangeRateRepository repository) {
		super();
		this.repository = repository;
	}
	
	
	public CurrencyExchangeRate getExchangeRateValue(String from, String to) {
		CurrencyExchangeRate currencyExchangeRateDto = null;
		CurrencyExchangeRateEntity entity = repository.findByFromAndTo(from, to);
		if(Objects.nonNull(entity)) {
			 currencyExchangeRateDto = new CurrencyExchangeRate(entity.getId(), entity.getFrom(), entity.getTo(),entity.getConversionMultiple());
		}
		return  currencyExchangeRateDto;
	}
	
	public List<CurrencyExchangeRate> getAllExchangeRates() {
		List<CurrencyExchangeRate> currencyExchangeRateDtos = null;
		List<CurrencyExchangeRateEntity> entities = repository.findAll();
		if (Objects.nonNull(entities)) {
			currencyExchangeRateDtos = entities
					.stream().filter(Objects::nonNull).map(entity -> new CurrencyExchangeRate(entity.getId(),
							entity.getFrom(), entity.getTo(), entity.getConversionMultiple()))
					.collect(Collectors.toList());
		}
		return currencyExchangeRateDtos;
	}
	
	public CurrencyExchangeRate save(CurrencyExchangeRate dto) {
		CurrencyExchangeRateEntity entity = repository
				.save(new CurrencyExchangeRateEntity(dto.getFrom(), dto.getTo(), dto.getConversionMultiple()));
		return new CurrencyExchangeRate(entity.getId(), entity.getFrom(), entity.getTo(),
				entity.getConversionMultiple());
	}


	public Boolean deleteAll() {
		repository.deleteAll();
		return true;
	}


	public void delete(Long id) {
		repository.deleteById(id);
	}

}
