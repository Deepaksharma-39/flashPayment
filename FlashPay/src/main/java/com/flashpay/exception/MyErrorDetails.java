package com.flashpay.exception;

import java.time.LocalDateTime;

import lombok.Data;

public @Data class MyErrorDetails {

	private LocalDateTime timestamp;
	private String message;
	private String description;
	
	public MyErrorDetails(LocalDateTime timestamp, String message, String description) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}
	
	public MyErrorDetails() {

	}
	@Override
	public String toString() {
		return "MyErrorDetails [timestamp=" + timestamp + ", message=" + message + ", description=" + description + "]";
	}
}
