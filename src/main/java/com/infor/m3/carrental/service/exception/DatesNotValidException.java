package com.infor.m3.carrental.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Provided dates are not valid!")
public class DatesNotValidException extends Exception {

	private static final long serialVersionUID = -4166444525805725969L;

	public DatesNotValidException(String message) {
		super(message);
	}

}
