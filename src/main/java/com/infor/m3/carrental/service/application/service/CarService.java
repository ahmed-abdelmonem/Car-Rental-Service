package com.infor.m3.carrental.service.application.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.infor.m3.carrental.service.dto.CarDTO;
import com.infor.m3.carrental.service.dto.UpdateCarAvailabilitDTO;
import com.infor.m3.carrental.service.exception.CarHasCurrentBookings;
import com.infor.m3.carrental.service.exception.DateFormatNotValidException;
import com.infor.m3.carrental.service.exception.DatesNotValidException;
import com.infor.m3.carrental.service.exception.EntityAlreadyExists;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * Service that handles car related calls using domain services
 * 
 * @author ahmed.abdelmonem
 */
public interface CarService {

	CarDTO registerCar(@NotNull CarDTO carDTO) throws EntityAlreadyExists;

	CarDTO updateCarAvailability(@NotNull Long carId, @NotNull UpdateCarAvailabilitDTO updateCarAvailabilitDTO)
			throws EntityNotFoundException, DatesNotValidException, CarHasCurrentBookings, DateFormatNotValidException;

	List<CarDTO> searchCars(@NotNull String rentFrom, @NotNull String rentTo, @NotNull BigDecimal maxPricePerHour,
			@NotNull Integer page, @NotNull Integer pageSize) throws DatesNotValidException, DateFormatNotValidException;

}
