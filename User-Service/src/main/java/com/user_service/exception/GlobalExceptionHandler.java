package com.user_service.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user_service.entities.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	ApiResponse apiResponse;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		apiResponse.setMessage(message);
		apiResponse.setStatus(HttpStatus.NOT_FOUND);
		apiResponse.setSuccess(true);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
}