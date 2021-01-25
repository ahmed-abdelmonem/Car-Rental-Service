package com.infor.m3.carrental.service.domain.service;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infor.m3.carrental.service.domain.entity.Booking;
import com.infor.m3.carrental.service.domain.repository.BookingRepository;
import com.infor.m3.carrental.service.exception.CarNotAvailableException;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * The service that manage {@link Booking} entities
 * 
 * @author ahmed.abdelmonem
 */
@Service
public class BookingDomainServiceImpl implements BookingDomainService {

	private final BookingRepository bookingRepository;

	public BookingDomainServiceImpl(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public Booking create(@NotNull Booking booking) throws CarNotAvailableException {
		return bookingRepository.saveAndFlush(booking);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Booking getById(@NotNull Long id) throws EntityNotFoundException {
		return bookingRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Booking not found ID: " + id));
	}
}
