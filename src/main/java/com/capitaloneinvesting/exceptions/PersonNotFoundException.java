package com.capitaloneinvesting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This person is not found in the system")
public class PersonNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5902510986154951287L;

	public PersonNotFoundException(String message) {
		super(message);
	}

}