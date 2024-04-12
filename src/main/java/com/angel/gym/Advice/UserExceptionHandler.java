package com.angel.gym.Advice;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public String handleInvalidArguments(DataIntegrityViolationException exception) {
		
		
		String error = exception.getMessage();
		
		return error;
		
	}
	
}
