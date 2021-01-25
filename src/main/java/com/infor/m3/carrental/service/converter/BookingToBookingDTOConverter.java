package com.infor.m3.carrental.service.converter;

import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.infor.m3.carrental.service.domain.entity.Booking;
import com.infor.m3.carrental.service.dto.BookingDTO;
import com.infor.m3.carrental.service.dto.CarDTO;
import com.infor.m3.carrental.service.dto.UserDTO;

@Component
public class BookingToBookingDTOConverter implements Converter<Booking, BookingDTO> {

	@Override
	public BookingDTO convert(Booking booking) {

		return BookingDTO.builder()
				.id(booking.getId())
				.created(booking.getCreated().format(DateTimeFormatter.ISO_DATE_TIME))
				.beginning(booking.getBeginning().format(DateTimeFormatter.ISO_DATE_TIME))
				.end(booking.getEnd().format(DateTimeFormatter.ISO_DATE_TIME))
				.user(UserDTO.builder()
						.id(booking.getUser().getId())
						.name(booking.getUser().getName())
						.email(booking.getUser().getEmail())
						.build())
				.car(CarDTO.builder()
						.id(booking.getCar().getId())
						.plateNumber(booking.getCar().getPlateNumber())
						.model(booking.getCar().getModel())
						.year(booking.getCar().getYear())
						.build())
				.build();
	}

}
