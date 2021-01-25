package com.infor.m3.carrental.service.application.service;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.infor.m3.carrental.service.domain.entity.Booking;
import com.infor.m3.carrental.service.domain.entity.Car;
import com.infor.m3.carrental.service.domain.entity.User;
import com.infor.m3.carrental.service.domain.service.BookingDomainService;
import com.infor.m3.carrental.service.domain.service.CarDomainService;
import com.infor.m3.carrental.service.domain.service.UserDomainService;
import com.infor.m3.carrental.service.dto.BookingDTO;
import com.infor.m3.carrental.service.dto.CreateBookingDTO;
import com.infor.m3.carrental.service.exception.CarNotAvailableException;
import com.infor.m3.carrental.service.exception.DateFormatNotValidException;
import com.infor.m3.carrental.service.exception.DatesNotValidException;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;
import com.infor.m3.carrental.service.util.Utilities;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ahmed.abdelmonem
 */
@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

	private final BookingDomainService bookingDomainService;

	private final UserDomainService userDomainService;

	private final CarDomainService carDomainService;

	private final ConversionService conversionService;

	public BookingServiceImpl(BookingDomainService bookingDomainService, UserDomainService userDomainService,
			CarDomainService carDomainService, ConversionService conversionService) {
		this.bookingDomainService = bookingDomainService;
		this.userDomainService = userDomainService;
		this.carDomainService = carDomainService;
		this.conversionService = conversionService;
	}

	@Override
	public BookingDTO createBooking(@NotNull CreateBookingDTO createBookingDTO)
			throws EntityNotFoundException, CarNotAvailableException, DatesNotValidException, DateFormatNotValidException {
		
		LocalDateTime beginningDateTime = Utilities.toLocalDateTime(createBookingDTO.getBeginning());
		LocalDateTime endDateTime = Utilities.toLocalDateTime(createBookingDTO.getEnd());
		
		if(!Utilities.isValidDates(beginningDateTime, endDateTime)) {
			log.debug("Provided dates are not valid! date from: {}, date to: {}", beginningDateTime, endDateTime);
			throw new DatesNotValidException("Provided dates are not valid!");
		}
		
		// get user with the provided id
		User user = userDomainService.getById(createBookingDTO.getUserId());
		// get car with the provided id
		Car car = carDomainService.getById(createBookingDTO.getCarId());
		// check car availability before creating booking
		if(!carDomainService.isAvailableWithinRentPeriod(car, beginningDateTime, endDateTime)) {
			throw new CarNotAvailableException("Car Not available within the rent period. Car ID: " + car.getId());
		}
		
		// create booking for user and car
		Booking booking = bookingDomainService.create(new Booking(beginningDateTime, endDateTime, user, car));

		return conversionService.convert(booking, BookingDTO.class);
	}

}
