package com.three55.jambolanapi.exceptions;

public class RecordNotFoundException extends RuntimeException {
	
	public RecordNotFoundException(String message) {
		super(message);
	}
	
	public RecordNotFoundException(String message, Throwable t) {
		super(message, t);
	}

}
