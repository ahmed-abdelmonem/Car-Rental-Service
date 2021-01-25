package com.infor.m3.carrental.service.application.service;

import javax.validation.constraints.NotNull;

import com.infor.m3.carrental.service.dto.BookingDTO;
import com.infor.m3.carrental.service.dto.CreateBookingDTO;
import com.infor.m3.carrental.service.exception.CarNotAvailableException;
import com.infor.m3.carrental.service.exception.DateFormatNotValidException;
import com.infor.m3.carrental.service.exception.DatesNotValidException;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * Service that handles booking related calls using domain services
 * 
 * @author ahmed.abdelmonem
 */
public interface BookingService {

	BookingDTO createBooking(@NotNull CreateBookingDTO createBookingDTO) throws EntityNotFoundException,
			CarNotAvailableException, DatesNotValidException, DateFormatNotValidException;

}
