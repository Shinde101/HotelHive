package com.rating_service.excetion;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super("Given Id is not available");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
