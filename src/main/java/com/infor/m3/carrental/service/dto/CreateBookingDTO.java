package com.infor.m3.carrental.service.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Booking data transfer object used to pass create booking data to end
 * points.
 * 
 * @author ahmed.abdelmonem
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBookingDTO {
	
	@NotNull(message = "User can not be null!")
	private Long userId;
	
	@NotNull(message = "Car can not be null!")
	private Long carId;
	
	@NotNull(message = "Date from can not be null!")
	private String beginning;

	@NotNull(message = "Date to can not be null!")
	private String end;

}
