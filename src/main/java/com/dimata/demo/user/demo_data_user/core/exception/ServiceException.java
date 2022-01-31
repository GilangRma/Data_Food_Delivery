package com.dimata.demo.user.demo_data_user.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 4400593043450453592L;

	private final HttpStatus status;
	private final String messageError;
	
	public ServiceException(String message, HttpStatus status) {
		this.status = status;
		this.messageError = message;
	}

	@Override
	public String getMessage() {
		return messageError + " [" + status.value() + "]";
	}
	
}
