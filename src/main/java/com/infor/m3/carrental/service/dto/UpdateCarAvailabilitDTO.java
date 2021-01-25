package com.infor.m3.carrental.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Car data transfer object used to return/pass car data from/to end points.
 * 
 * @author ahmed.abdelmonem
 *
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdateCarAvailabilitDTO {
	
	@NotNull(message = "Available from date can not be null!")
	private String availableFrom;
	
	@NotNull(message = "Available to date can not be null!")
	private String availableTo;
	
	@NotNull(message = "Price per hour can not be null!")
	@DecimalMin(value = "0", message = "The price per hour can not be negative!")
	private BigDecimal pricePerHour;
}
