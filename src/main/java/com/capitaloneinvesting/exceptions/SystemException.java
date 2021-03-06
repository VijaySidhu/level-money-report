package com.capitaloneinvesting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal System Error")
public class SystemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6281908617533686720L;

	public SystemException(String status, String message) {
		super(status + " " + message);
	}

}
