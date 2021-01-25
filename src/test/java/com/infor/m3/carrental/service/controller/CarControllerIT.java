package com.infor.m3.carrental.service.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.infor.m3.carrental.service.domain.entity.Car;
import com.infor.m3.carrental.service.dto.CarDTO;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
public class CarControllerIT extends CarRentalBaseTestIT{
	
	@Test
    public void canRegisterCar() throws EntityNotFoundException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
		// get user from db
		Car car = carDomainService.getById(carId);
		
		assertEquals(PLATE_NUMBER, car.getPlateNumber());
		assertEquals(MODEL, car.getModel());
		assertEquals(YEAR, car.getYear());
	}
	
	@Test
    public void registerUserWithPlateNumberAlreadyRegistered(){
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		
		registerCar(PLATE_NUMBER, MODEL, YEAR);
		try {
			registerCar(PLATE_NUMBER, "volvo", 2012);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("Entity Already Exists"));
		}
	}
	
	@Test
    public void registerUserWithYearBefore1950() {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 1910;
		
		try {
			registerCar(PLATE_NUMBER, MODEL, YEAR);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("Validation failed"));
		}
	}
	
	@Test
    public void canUpdateCarAvailability() throws EntityNotFoundException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		final String AVAILABLE_FROM = "2021-05-15T21:00";
		final String AVAILABLE_TO = "2021-10-20T21:00";
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
		
		// update availability
		updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);
		
		Car car = carDomainService.getById(carId);
		
		assertEquals(PLATE_NUMBER, car.getPlateNumber());
		assertEquals(MODEL, car.getModel());
		assertEquals(YEAR, car.getYear());
		assertEquals(AVAILABLE_FROM, car.getAvailableFrom().toString());
		assertEquals(AVAILABLE_TO, car.getAvailableTo().toString());
		assertTrue((PRICE_PER_HOUR.compareTo(car.getPricePerHour()) == 0));
	}
	
	@Test
    public void updateCarAvailabilityWithDatesBeforeNow() throws EntityNotFoundException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		final String AVAILABLE_FROM = "2020-05-15T21:00";
		final String AVAILABLE_TO = "2020-10-20T21:00";
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
				
		try {
			updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("dates are not valid"));
		}
	}
	
	@Test
    public void updateCarAvailabilityWithDateFromAfterDateTo() throws EntityNotFoundException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		final String AVAILABLE_FROM = "2021-05-25T21:00";
		final String AVAILABLE_TO = "2021-05-20T21:00";
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
				
		try {
			updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("dates are not valid"));
		}
	}
	
	@Test
    public void canGetAvailableCars() throws EntityNotFoundException, URISyntaxException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		final String AVAILABLE_FROM = "2021-05-15T21:00";
		final String AVAILABLE_TO = "2021-10-20T21:00";
		final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(100);
		
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
		
		// update availability
		updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);
		// search for availability
		List<CarDTO> availableCars = searchCars("2021-07-17T21:00", "2021-07-25T21:00", "100", "0", "5");
		assertNotNull(availableCars);
		assertEquals(1, availableCars.size());
	}
	
	@Test
    public void createMultibleCarsAndCheckAvailability() throws URISyntaxException {
		
		for (int i = 0; i < 6; i++) {
			long carId = registerCar("ABC23" + i, "BMW", 1990);
			// update availability
			updateCarAvailability(carId, "2021-05-15T21:00", "2021-10-20T21:00", BigDecimal.valueOf(100));
		}
		
		// page 0 size 5
		List<CarDTO> availableCarsPage1 = searchCars("2021-07-17T21:00", "2021-07-25T21:00", "100", "0", "5");
		assertNotNull(availableCarsPage1);
		assertEquals(5, availableCarsPage1.size());
		// page 1 size 5
		List<CarDTO> availableCarsPage2 = searchCars("2021-07-17T21:00", "2021-07-25T21:00", "100", "1", "5");
		assertNotNull(availableCarsPage2);
		assertEquals(1, availableCarsPage2.size());
		// page 0 size 10
		List<CarDTO> availableCarsPage1Size10 = searchCars("2021-07-17T21:00", "2021-07-25T21:00", "100", "0", "10");
		assertNotNull(availableCarsPage1Size10);
		assertEquals(6, availableCarsPage1Size10.size());
	}
	
	@Test
	public void updateCarAvailabilityWithCurrentBooking() throws EntityNotFoundException {
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
		createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);
		
		try {
			// update availability with new availability intersects with the current booking
			updateCarAvailability(carId, "2021-05-15T21:00", "2021-06-17T21:00", PRICE_PER_HOUR);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("Car has bookings within the old availability"));
		}
		// update availability with new availability not intersects with the current booking
		updateCarAvailability(carId, "2021-05-15T21:00", "2021-07-17T21:00", PRICE_PER_HOUR);
		Car car = carDomainService.getById(carId);
		assertEquals("2021-07-17T21:00", car.getAvailableTo().toString());
	}
	
	@Test
	public void searchCarsWithCurrectBookingIntersectsWithRentPeriod() throws EntityNotFoundException, URISyntaxException {
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
		final String BOOKING_BEGINNING = "2021-07-15T21:00";
		final String BOOKING_END = "2021-07-20T21:00";

		// register user
		long userId = registerUser(NAME, EMAIL);
		// register car
		long carId = registerCar(PLATE_NUMBER, MODEL, YEAR);
		// update car availability
		updateCarAvailability(carId, AVAILABLE_FROM, AVAILABLE_TO, PRICE_PER_HOUR);
		
		// search for availability
		List<CarDTO> availableCars = searchCars("2021-07-17T21:00", "2021-07-25T21:00", "100", "0", "5");
		assertNotNull(availableCars);
		assertEquals(1, availableCars.size());
		// create booking
		createBooking(userId, carId, BOOKING_BEGINNING, BOOKING_END);
		// search for availability
		List<CarDTO> availableCarsAfterBooking = searchCars("2021-07-17T21:00", "2021-07-25T21:00", "100", "0", "5");
		assertNotNull(availableCarsAfterBooking);
		assertEquals(0, availableCarsAfterBooking.size());
	}
	
	@Test
    public void carWithoutAvailabilityShouldntAppearOnSearch() throws EntityNotFoundException, URISyntaxException {
		final String PLATE_NUMBER = "34AS2";
		final String MODEL = "BMW";
		final int YEAR = 2010;
		
		registerCar(PLATE_NUMBER, MODEL, YEAR);
		
		// search for availability
		List<CarDTO> availableCars = searchCars("2021-07-17T21:00", "2021-07-25T21:00", "100", "0", "5");
		assertNotNull(availableCars);
		assertEquals(0, availableCars.size());
	}
}
