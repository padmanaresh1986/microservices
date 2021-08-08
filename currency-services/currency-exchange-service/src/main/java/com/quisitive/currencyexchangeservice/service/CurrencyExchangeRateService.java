package com.quisitive.currencyexchangeservice.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quisitive.currencyexchangeservice.dto.CurrencyExchangeRateDto;
import com.quisitive.currencyexchangeservice.entity.CurrencyExchangeRateEntity;
import com.quisitive.currencyexchangeservice.repository.CurrencyExchangeRateRepository;

@Service
public class CurrencyExchangeRateService {
	
	CurrencyExchangeRateRepository repository;

	public CurrencyExchangeRateService(CurrencyExchangeRateRepository repository) {
		super();
		this.repository = repository;
	}
	
	
	public CurrencyExchangeRateDto getExchangeRateValue(String from, String to) {
		CurrencyExchangeRateDto currencyExchangeRateDto = null;
		CurrencyExchangeRateEntity entity = repository.findByFromAndTo(from, to);
		if(Objects.nonNull(entity)) {
			 currencyExchangeRateDto = new CurrencyExchangeRateDto(entity.getId(), entity.getFrom(), entity.getTo(),entity.getConversionMultiple());
		}
		return  currencyExchangeRateDto;
	}
	
	public List<CurrencyExchangeRateDto> getAllExchangeRates() {
		List<CurrencyExchangeRateDto> currencyExchangeRateDtos = null;
		List<CurrencyExchangeRateEntity> entities = repository.findAll();
		if (Objects.nonNull(entities)) {
			currencyExchangeRateDtos = entities
					.stream().filter(Objects::nonNull).map(entity -> new CurrencyExchangeRateDto(entity.getId(),
							entity.getFrom(), entity.getTo(), entity.getConversionMultiple()))
					.collect(Collectors.toList());
		}
		return currencyExchangeRateDtos;
	}
	
	public CurrencyExchangeRateDto save(CurrencyExchangeRateDto dto) {
		CurrencyExchangeRateEntity entity = repository
				.save(new CurrencyExchangeRateEntity(dto.getFrom(), dto.getTo(), dto.getConversionMultiple()));
		return new CurrencyExchangeRateDto(entity.getId(), entity.getFrom(), entity.getTo(),
				entity.getConversionMultiple());
	}


	public Boolean deleteAll() {
		repository.deleteAll();
		return true;
	}
	
	

}
