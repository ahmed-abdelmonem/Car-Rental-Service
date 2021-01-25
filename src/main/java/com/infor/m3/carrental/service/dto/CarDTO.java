package com.infor.m3.carrental.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
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
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {
	
	@ApiModelProperty(readOnly = true)
	private Long id;
	
	@ApiModelProperty(readOnly = true)
	private String created;
	
	@NotBlank(message = "Plate number can not be blank or null!")
	private String plateNumber;
	
	@NotBlank(message = "Car model can not be blank or null!")
	private String model;
	
	@Min(value = 1950, message = "Car year cannot be before 1950 ")
	@NotNull(message = "Car year can not be blank or null!")
	private Integer year;
	
	@ApiModelProperty(readOnly = true)
	private BigDecimal pricePerHour;
	
	@ApiModelProperty(readOnly = true)
	private String availabeFrom;
	
	@ApiModelProperty(readOnly = true)
	private String availabeTo;
}
