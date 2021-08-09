package com.quisitive.currencyexchangeservice.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@SuppressWarnings("all")
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorResponse response = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(true));
		return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ExchangeRateNotFoundException.class)
	public ResponseEntity<Object> handleExchangeRateNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorResponse response = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(true));
		return new ResponseEntity(response,HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse response = new ErrorResponse(new Date(), "Input Validation Failed", ex.getBindingResult().getAllErrors().toString());
		return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
	}

	
}
