package com.infor.m3.carrental.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Car has bookings within the old availability!")
public class CarHasCurrentBookings extends Exception {

	private static final long serialVersionUID = -8913530368712716835L;

	public CarHasCurrentBookings(String message) {
		super(message);
	}

}
