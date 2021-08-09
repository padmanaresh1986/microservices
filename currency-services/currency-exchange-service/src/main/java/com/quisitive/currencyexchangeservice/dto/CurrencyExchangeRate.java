package com.quisitive.currencyexchangeservice.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;


public class CurrencyExchangeRate {
	private Long id;
	
	@NotNull
	@Length(min = 3,max = 3)
	private String from;
	
	@NotNull
	@Length(min = 3,max = 3)
	private String to;
	
	@NotNull
	@Positive
	private BigDecimal conversionMultiple;
	
	
	public CurrencyExchangeRate() {
		super();
	}
	public CurrencyExchangeRate(Long id, String from, String to, BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}
	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}
	
	@Override
	public String toString() {
		return "CurrencyExchangeRateDto [id=" + id + ", from=" + from + ", to=" + to + ", conversionMultiple="
				+ conversionMultiple + "]";
	}
	
	
}
