package com.infor.m3.carrental.service.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infor.m3.carrental.service.application.service.BookingService;
import com.infor.m3.carrental.service.dto.BookingDTO;
import com.infor.m3.carrental.service.dto.CreateBookingDTO;
import com.infor.m3.carrental.service.exception.CarNotAvailableException;
import com.infor.m3.carrental.service.exception.DateFormatNotValidException;
import com.infor.m3.carrental.service.exception.DatesNotValidException;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * All operations with a booking will be routed by this controller.
 * 
 */
@Slf4j
@RestController
@RequestMapping("/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookingDTO createBooking(@Valid @RequestBody CreateBookingDTO createBookingDTO)
			throws EntityNotFoundException, CarNotAvailableException, DatesNotValidException,
			DateFormatNotValidException {

		log.debug("Called UserController.createBooking with booking :{}", createBookingDTO);

		return bookingService.createBooking(createBookingDTO);
	}

}
