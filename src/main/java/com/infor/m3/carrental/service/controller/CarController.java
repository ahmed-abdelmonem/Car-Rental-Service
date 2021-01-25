package com.infor.m3.carrental.service.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infor.m3.carrental.service.application.service.CarService;
import com.infor.m3.carrental.service.dto.CarDTO;
import com.infor.m3.carrental.service.dto.UpdateCarAvailabilitDTO;
import com.infor.m3.carrental.service.exception.CarHasCurrentBookings;
import com.infor.m3.carrental.service.exception.DateFormatNotValidException;
import com.infor.m3.carrental.service.exception.DatesNotValidException;
import com.infor.m3.carrental.service.exception.EntityAlreadyExists;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * All operations with a car will be routed by this controller.
 * 
 */
@Slf4j
@RestController
@RequestMapping("/cars")
public class CarController {

	private final CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CarDTO registerCar(@Valid @RequestBody CarDTO carDTO) throws EntityAlreadyExists {

		log.debug("Called CarController.registerOrUpdateCar with car :{}", carDTO);

		return carService.registerCar(carDTO);
	}

	@PutMapping("/availability/{carId}")
	public CarDTO updateCarAvailability(@PathVariable long carId,
			@Valid @RequestBody UpdateCarAvailabilitDTO updateCarAvailabilitDTO)
			throws EntityNotFoundException, DatesNotValidException, CarHasCurrentBookings, DateFormatNotValidException {

		log.debug("Called CarController.updateCarAvailability car ID :{}", carId);

		return carService.updateCarAvailability(carId, updateCarAvailabilitDTO);
	}

	@GetMapping
	public List<CarDTO> searchCars(@RequestParam String rentFrom, @RequestParam String rentTo,
			@RequestParam BigDecimal maxPricePerHour, @RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "5") Integer pageSize)
			throws DatesNotValidException, DateFormatNotValidException {

		log.debug("Called CarController.searchCars with parameters :{}, {}, {}", rentFrom, rentTo, maxPricePerHour);

		return carService.searchCars(rentFrom, rentTo, maxPricePerHour, page, pageSize);
	}

}
