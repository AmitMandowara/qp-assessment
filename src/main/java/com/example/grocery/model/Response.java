package com.example.grocery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
	private int statusCode;
	private T data;
	private String errorMessage;
	
	public Response(int statusCode, T data) {
		this.statusCode = statusCode;
		this.data = data;
	}

	public Response(int statusCode, T data, String errorMessage) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}
	
}
