package com.infor.m3.carrental.service.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.Test;

import com.infor.m3.carrental.service.domain.entity.Booking;
import com.infor.m3.carrental.service.domain.entity.Car;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
public class BookingControllerIT extends CarRentalBaseTestIT {

	@Test
	public void canCreateBooking() throws EntityNotFoundException {
		// user
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		// car
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		// Availability
		final String AVAILABLE_FROM = "2021-05-15T21:00";
		final String AVAILABLE_TO = "2021-10-20T21:00";
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		// booking
		final String BOOKING_BEGINNING = "2021-06-15T21:00";
		final String BOOKING_END = "2021-06-20T21:00";

		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
		// update car availability
		updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);
		// create booking
		long bookingId = createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);

		Booking booking = bookingDomainService.getById(bookingId);

		assertEquals(userId, booking.getUser().getId());
		assertEquals(carId, booking.getCar().getId());
		assertEquals(BOOKING_BEGINNING, booking.getBeginning().toString());
		assertEquals(BOOKING_END, booking.getEnd().toString());
	}

	@Test
	public void createBookingForCarWithoutAvailabilityDates() throws EntityNotFoundException {
		// user
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		// car
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		// booking
		final String BOOKING_BEGINNING = "2021-06-15T21:00";
		final String BOOKING_END = "2021-06-20T21:00";

		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);

		try {
			// create booking
			createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Car Not available"));
		}
	}

	@Test
	@Transactional
	public void canCreateAnotherBookingNotIntersectsWithTheCarBookings() throws EntityNotFoundException {
		// user
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		// car
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		// Availability
		final String AVAILABLE_FROM = "2021-05-15T21:00";
		final String AVAILABLE_TO = "2021-10-20T21:00";
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		// bookings
		final String FIRST_BOOKING_BEGINNING = "2021-06-15T21:00";
		final String FIRST_BOOKING_END = "2021-06-20T21:00";
		final String SECOND_BOOKING_BEGINNING = "2021-07-15T21:00";
		final String SECOND_BOOKING_END = "2021-07-23T21:00";


		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
		// update car availability
		updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);
		// create booking
		long firstBookingId = createBooking(userId, carId, FIRST_BOOKING_BEGINNING, FIRST_BOOKING_END);

		Booking firstBooking = bookingDomainService.getById(firstBookingId);

		assertEquals(userId, firstBooking.getUser().getId());
		assertEquals(carId, firstBooking.getCar().getId());
		assertEquals(FIRST_BOOKING_BEGINNING, firstBooking.getBeginning().toString());
		assertEquals(FIRST_BOOKING_END, firstBooking.getEnd().toString());
		
		// create another booking
		long secondBookingId = createBooking(userId, carId, SECOND_BOOKING_BEGINNING, SECOND_BOOKING_END);

		Booking secondBooking = bookingDomainService.getById(secondBookingId);

		assertEquals(userId, secondBooking.getUser().getId());
		assertEquals(carId, secondBooking.getCar().getId());
		assertEquals(SECOND_BOOKING_BEGINNING, secondBooking.getBeginning().toString());
		assertEquals(SECOND_BOOKING_END, secondBooking.getEnd().toString());
		
		Car car = carDomainService.getById(carId);
		assertEquals(2, car.getBookings().size());
	}
	
	@Test
	public void createBookingForCarThatHasCurrentBookingSamePeriod() throws EntityNotFoundException {
		// user
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		// car
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		// Availability
		final String AVAILABLE_FROM = "2021-05-15T21:00";
		final String AVAILABLE_TO = "2021-10-20T21:00";
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		// booking
		final String BOOKING_BEGINNING = "2021-06-15T21:00";
		final String BOOKING_END = "2021-06-20T21:00";

		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
		// update car availability
		updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);
		// create booking
		long bookingId = createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);

		Booking booking = bookingDomainService.getById(bookingId);

		assertEquals(userId, booking.getUser().getId());
		assertEquals(carId, booking.getCar().getId());
		assertEquals(BOOKING_BEGINNING, booking.getBeginning().toString());
		assertEquals(BOOKING_END, booking.getEnd().toString());

		// create another booking intersects with the first one
		try {
			// create booking
			createBooking(userId, carId, "2021-06-17T21:00", "2021-06-25T21:00");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Car Not available"));
		}
	}
	
	@Test
	public void createBookingOutsideCarAvailabilityDates() throws EntityNotFoundException {
		// user
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		// car
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		// Availability
		final String AVAILABLE_FROM = "2021-05-15T21:00";
		final String AVAILABLE_TO = "2021-10-20T21:00";
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		// booking
		final String BOOKING_BEGINNING = "2021-04-15T21:00";
		final String BOOKING_END = "2021-11-20T21:00";

		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
		// update car availability
		updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);

		try {
			// create booking
			createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Car Not available"));
		}
	}
}
