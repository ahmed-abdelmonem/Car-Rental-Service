package com.infor.m3.carrental.service.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infor.m3.carrental.service.domain.entity.Car;

/**
 * The JPA repository for {@link Car} entities.
 * 
 * @author ahmed.abdelmonem
 */
public interface CarRepository extends JpaRepository<Car, Long> {

	/**
	 * Finds all available cars within rent duration and max rent per hour 
	 * the cars may have current booking - will be filtered in the service
	 */
	@Query("select c from Car c "
			+ "where c.availableFrom is not null and c.availableFrom <= :rentFrom "
			+ "and c.availableTo is not null and c.availableTo >= :rentTo "
			+ "and c.pricePerHour is not null and c.pricePerHour <= :maxPricePerHour "
			+ "order by c.pricePerHour desc")
	Page<Car> findCarsValidForRentDurationAndMaxPrice(LocalDateTime rentFrom, LocalDateTime rentTo,
			BigDecimal maxPricePerHour, Pageable pageable);
	
	/**
	 * find car with plate number
	 */
	Optional<Car> findByPlateNumber(String plateNumber);

}
