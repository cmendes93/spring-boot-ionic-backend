package com.cmendes.cursomc.services.exceptions;

public class DataViolationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataViolationException(String msg) {
		super(msg);
	}
	
	public DataViolationException(String msg,Throwable cause) {
		super(msg, cause);
	}
}
