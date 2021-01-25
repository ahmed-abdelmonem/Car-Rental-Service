package com.infor.m3.carrental.service.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.infor.m3.carrental.service.domain.entity.Car;
import com.infor.m3.carrental.service.domain.service.CarDomainService;
import com.infor.m3.carrental.service.dto.CarDTO;
import com.infor.m3.carrental.service.dto.UpdateCarAvailabilitDTO;
import com.infor.m3.carrental.service.exception.CarHasCurrentBookings;
import com.infor.m3.carrental.service.exception.DateFormatNotValidException;
import com.infor.m3.carrental.service.exception.DatesNotValidException;
import com.infor.m3.carrental.service.exception.EntityAlreadyExists;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;
import com.infor.m3.carrental.service.util.Utilities;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ahmed.abdelmonem
 */
@Slf4j
@Service
public class CarServiceImpl implements CarService {

	private final CarDomainService carDomainService;

	private final ConversionService conversionService;

	public CarServiceImpl(CarDomainService carDomainService, ConversionService conversionService) {
		this.carDomainService = carDomainService;
		this.conversionService = conversionService;
	}

	@Override
	public CarDTO registerCar(@NotNull CarDTO carDTO) throws EntityAlreadyExists {
		if (carDomainService.getByPlateNumber(carDTO.getPlateNumber()).isPresent()) {
			throw new EntityAlreadyExists("Car Already Exists!");
		}

		Car car = carDomainService
				.createOrUpdate(new Car(carDTO.getPlateNumber(), carDTO.getModel(), carDTO.getYear()));
		return conversionService.convert(car, CarDTO.class);
	}

	@Override
	public CarDTO updateCarAvailability(@NotNull Long carId, UpdateCarAvailabilitDTO updateCarAvailabilitDTO)
			throws EntityNotFoundException, DatesNotValidException, CarHasCurrentBookings, DateFormatNotValidException {
		
		Car car = carDomainService.getById(carId);
		
		LocalDateTime availableFrom = Utilities.toLocalDateTime(updateCarAvailabilitDTO.getAvailableFrom());
		LocalDateTime availableTo = Utilities.toLocalDateTime(updateCarAvailabilitDTO.getAvailableTo());
		
		if (!Utilities.isValidDates(availableFrom, availableTo)) {
			log.debug("Provided dates are not valid! date from: {}, date to: {}", availableFrom, availableTo);
			throw new DatesNotValidException("Provided dates are not valid!");
		}

		if (!carDomainService.isBookingsFitTheNewAvailability(car, availableFrom, availableTo)) {
			log.debug("Car has bookings within the old availability! date from: {}, date to: {}", availableFrom,
					availableFrom);
			throw new CarHasCurrentBookings("Car has bookings within the old availability!");
		}

		car.setAvailableFrom(availableFrom);
		car.setAvailableTo(availableTo);
		car.setPricePerHour(updateCarAvailabilitDTO.getPricePerHour());

		return conversionService.convert(carDomainService.createOrUpdate(car), CarDTO.class);
	}

	@Override
	public List<CarDTO> searchCars(@NotNull String rentFrom, @NotNull String rentTo,
			@NotNull BigDecimal maxPricePerHour, @NotNull Integer page, @NotNull Integer pageSize)
			throws DatesNotValidException, DateFormatNotValidException {
		LocalDateTime rentFromDateTime = Utilities.toLocalDateTime(rentFrom);
		LocalDateTime rentToDateTime = Utilities.toLocalDateTime(rentTo);
		
		if (!Utilities.isValidDates(rentFromDateTime, rentToDateTime)) {
			log.debug("Provided dates are not valid! date from: {}, date to: {}", rentFromDateTime, rentToDateTime);
			throw new DatesNotValidException("Provided dates are not valid!");
		}

		return carDomainService.findCarsAvailability(rentFromDateTime, rentToDateTime, maxPricePerHour, page, pageSize)
				.stream().map(car -> conversionService.convert(car, CarDTO.class)).collect(Collectors.toList());
	}
}
