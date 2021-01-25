package com.infor.m3.carrental.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Car Not available within the rent period")
public class CarNotAvailableException extends Exception {

	private static final long serialVersionUID = 1455707904484023326L;

	public CarNotAvailableException(String message) {
		super(message);
	}

}
