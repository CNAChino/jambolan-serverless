package com.three55.jambolanapi.exceptions;

public class DaoException extends RuntimeException {
	
	public DaoException(Throwable t) {
		super(t);
	}
	
	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(String msg, Throwable t) {
		super(msg, t);
	}
}
