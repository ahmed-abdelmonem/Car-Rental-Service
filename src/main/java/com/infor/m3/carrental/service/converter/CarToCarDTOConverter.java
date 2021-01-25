package com.infor.m3.carrental.service.converter;

import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.infor.m3.carrental.service.domain.entity.Car;
import com.infor.m3.carrental.service.dto.CarDTO;
import com.infor.m3.carrental.service.dto.CarDTO.CarDTOBuilder;

@Component
public class CarToCarDTOConverter implements Converter<Car, CarDTO> {

	@Override
	public CarDTO convert(Car car) {

		CarDTOBuilder builder = CarDTO.builder()
				.id(car.getId())
				.created(car.getCreated().format(DateTimeFormatter.ISO_DATE_TIME))
				.plateNumber(car.getPlateNumber())
				.model(car.getModel())
				.year(car.getYear())
				.pricePerHour(car.getPricePerHour());
		
		if(car.getAvailableFrom() != null && car.getAvailableTo() != null) {
			builder
			.availabeFrom(car.getAvailableFrom().format(DateTimeFormatter.ISO_DATE_TIME))
			.availabeTo(car.getAvailableTo().format(DateTimeFormatter.ISO_DATE_TIME));
		}

		return builder.build();
	}

}
