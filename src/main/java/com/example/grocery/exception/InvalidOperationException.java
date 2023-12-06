package com.example.grocery.exception;

public class InvalidOperationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public InvalidOperationException(String message) {
		super(message);
	}
}
