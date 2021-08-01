package com.quisitive.currencyconversionservice.dto;

import java.math.BigDecimal;

public class CurrencyConversionDto {
	private String from;
	private String to;
	private BigDecimal quantity;
	private BigDecimal conversionMultiple;
	private BigDecimal totalCalculatedAmount;
	
	
	
	public CurrencyConversionDto(String from, String to, BigDecimal quantity, BigDecimal conversionMultiple,
			BigDecimal totalCalculatedAmount) {
		super();
		this.from = from;
		this.to = to;
		this.quantity = quantity;
		this.conversionMultiple = conversionMultiple;
		this.totalCalculatedAmount = totalCalculatedAmount;
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
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}
	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}
	public BigDecimal getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}
	public void setTotalCalculatedAmount(BigDecimal totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
	}
	
	
}
