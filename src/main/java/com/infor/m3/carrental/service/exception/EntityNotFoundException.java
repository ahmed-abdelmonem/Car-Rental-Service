package com.infor.m3.carrental.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find entity withthe provided id.")
public class EntityNotFoundException extends Exception {
	static final long serialVersionUID = -3387516993334229948L;

	public EntityNotFoundException(String message) {
		super(message);
	}

}
