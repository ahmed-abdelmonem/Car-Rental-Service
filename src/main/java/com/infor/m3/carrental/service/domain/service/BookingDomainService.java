package com.infor.m3.carrental.service.domain.service;

import javax.validation.constraints.NotNull;

import com.infor.m3.carrental.service.domain.entity.Booking;
import com.infor.m3.carrental.service.exception.CarNotAvailableException;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * The service that manage {@link Booking} entities
 * 
 * @author ahmed.abdelmonem
 */
public interface BookingDomainService {
	
	/**
	 * Create a new booking
	 *
	 * @param booking {@link Booking} to be created
	 *
	 * @return The {@link Booking}
	 * @throws CarNotAvailableException 
	 */
	Booking create(@NotNull Booking booking) throws CarNotAvailableException;

	/**
	 * Gets the booking with the provide id
	 *
	 * @param id booking id
	 *
	 * @return The {@link Booking}
	 *
	 * @throws EntityNotFoundException
	 */
	Booking getById(@NotNull Long id) throws EntityNotFoundException;

}
