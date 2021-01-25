package com.infor.m3.carrental.service.controller;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.infor.m3.carrental.service.domain.repository.BookingRepository;
import com.infor.m3.carrental.service.domain.repository.CarRepository;
import com.infor.m3.carrental.service.domain.repository.UserRepository;
import com.infor.m3.carrental.service.domain.service.BookingDomainService;
import com.infor.m3.carrental.service.domain.service.CarDomainService;
import com.infor.m3.carrental.service.domain.service.UserDomainService;
import com.infor.m3.carrental.service.dto.BookingDTO;
import com.infor.m3.carrental.service.dto.CarDTO;
import com.infor.m3.carrental.service.dto.CreateBookingDTO;
import com.infor.m3.carrental.service.dto.UpdateCarAvailabilitDTO;
import com.infor.m3.carrental.service.dto.UserDTO;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class CarRentalBaseTestIT {
	
	private final String USERS_PATH = "/users";
	private final String CARS_PATH = "/cars";
	private final String CARS_AVAILABILITY_PATH = "/cars/availability";
	private final String BOOKINGS_PATH = "/bookings";

	private RestTemplate restTemplate = new RestTemplate();

	@LocalServerPort
	private int port;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	protected UserDomainService userDomainService;
	
	@Autowired
	protected CarDomainService carDomainService;
	
	@Autowired
	protected BookingDomainService bookingDomainService;

	@Before
	public void clean() {
		bookingRepository.deleteAll();
		userRepository.deleteAll();
		carRepository.deleteAll();
	}

	protected long registerUser(String name, String email) {
		UserDTO userDTO = UserDTO.builder()
				.name(name)
				.email(email)
				.build();
		UserDTO createdUser = performCall(HttpMethod.POST, userDTO, USERS_PATH, UserDTO.class);
		return createdUser.getId();
	}
	
	protected long registerCar(String plateNumber, String model, Integer year) {
		CarDTO carDTO = CarDTO.builder()
				.plateNumber(plateNumber)
				.model(model)
				.year(year)
				.build();
		CarDTO createdCar = performCall(HttpMethod.POST, carDTO, CARS_PATH, CarDTO.class);
		return createdCar.getId();
	}
	
	protected void updateCarAvailability(long carId, String availableFrom, String availableTo, BigDecimal pricePerHour) {
		UpdateCarAvailabilitDTO updateCarAvailabilitDTO = UpdateCarAvailabilitDTO.builder()
				.availableFrom(availableFrom)
				.availableTo(availableTo)
				.pricePerHour(pricePerHour)
				.build();
		performCall(HttpMethod.PUT, updateCarAvailabilitDTO, CARS_AVAILABILITY_PATH + "/" + carId, CarDTO.class);
	}
	
	@SuppressWarnings("unchecked")
	protected List<CarDTO> searchCars(String rentFrom, String rentTo, String maxPricePerHour, String page,
			String pageSize) throws URISyntaxException {
		
		URIBuilder uriBuilder = new URIBuilder(CARS_PATH);
		uriBuilder.addParameter("rentFrom", rentFrom);
		uriBuilder.addParameter("rentTo", rentTo);
		uriBuilder.addParameter("maxPricePerHour", maxPricePerHour);
		uriBuilder.addParameter("page", page);
		uriBuilder.addParameter("pageSize", pageSize);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return performCall(HttpMethod.GET, headers, uriBuilder.build().toString(), List.class);
	}
	
	protected long createBooking(long userId, Long carId, String beginning, String end) {
		CreateBookingDTO createBookingDTO = CreateBookingDTO.builder()
				.userId(userId)
				.carId(carId)
				.beginning(beginning)
				.end(end)
				.build();
		BookingDTO createdBooking = performCall(HttpMethod.POST, createBookingDTO, BOOKINGS_PATH, BookingDTO.class);
		return createdBooking.getId();
	}

	protected <I, O> O performCall(HttpMethod httpMethod, I input, String path, Class<O> response) {
		HttpEntity<I> httpEntity = new HttpEntity<>(input);
		ResponseEntity<O> responseEntity = restTemplate.exchange("http://localhost:" + port + path, httpMethod,
				httpEntity, response);
		return responseEntity.getBody();
	}

}
