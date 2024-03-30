package com.hotelService.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException() {

		super("Hotel Id not found");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	
}
