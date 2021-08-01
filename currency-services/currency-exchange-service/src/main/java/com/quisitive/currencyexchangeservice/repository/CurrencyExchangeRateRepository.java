package com.quisitive.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quisitive.currencyexchangeservice.entity.CurrencyExchangeRateEntity;

@Repository
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRateEntity, Long> {
	
	CurrencyExchangeRateEntity findByFromAndTo(String from, String to);

}
