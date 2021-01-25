package com.infor.m3.carrental.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Entity Already Exists!")
public class EntityAlreadyExists extends Exception {

	private static final long serialVersionUID = -8444267182934630560L;

	public EntityAlreadyExists(String message) {
		super(message);
	}

}
