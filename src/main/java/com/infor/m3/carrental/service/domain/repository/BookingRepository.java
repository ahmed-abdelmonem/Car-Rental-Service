package com.infor.m3.carrental.service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infor.m3.carrental.service.domain.entity.Booking;

/**
 * The JPA repository for {@link Booking} entities.
 * 
 * @author ahmed.abdelmonem
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
