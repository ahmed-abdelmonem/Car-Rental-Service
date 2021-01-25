package com.infor.m3.carrental.service.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.infor.m3.carrental.service.domain.entity.Car;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * The service that manage {@link Car} entities
 * 
 * @author ahmed.abdelmonem
 */
public interface CarDomainService {

	/**
	 * Create a new car
	 *
	 * @param car {@link Car} to be created
	 *
	 * @return The {@link Car}
	 * @throws EntityAlreadyExists 
	 */
	Car createOrUpdate(@NotNull Car car);

	/**
	 * Gets the car with the provided id
	 *
	 * @param id Car id
	 *
	 * @return The {@link Car}
	 *
	 * @throws EntityNotFoundException
	 */
	Car getById(@NotNull Long id) throws EntityNotFoundException;
	
	/**
	 * Gets the car with the provided plate number
	 *
	 * @param plateNumber Car plate number
	 *
	 * @return The {@link Car}
	 *
	 */
	Optional<Car> getByPlateNumber(@NotNull String plateNumber);

	/**
	 * Gets car availability
	 *
	 * @param rentFrom required date from
	 * @param rentTo required date to
	 * @param maxPricePerHour max price per hour
	 * @param page page number
	 * @param pageSize page size
	 *
	 * @return page of available cars that fits the search criteria
	 *
	 */
	List<Car> findCarsAvailability(@NotNull LocalDateTime rentFrom, @NotNull LocalDateTime rentTo, 
			BigDecimal maxPricePerHour, int page, int pageSize);
	
	/**
	 * check if the car is available within the rent duration
	 *
	 * @param car the car
	 * @param rentFrom required date from
	 * @param rentTo required date to
	 *
	 */
	boolean isAvailableWithinRentPeriod(Car car, LocalDateTime rentFrom, LocalDateTime rentTo);
	
	/**
	 * check if the bookings of the car will fit within the new availability 
	 *
	 * @param car the car
	 * @param rentFrom required date from
	 * @param rentTo required date to
	 *
	 */
	boolean isBookingsFitTheNewAvailability(Car car, LocalDateTime availableFrom, LocalDateTime availableTo);

}
