package com.infor.m3.carrental.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User data transfer object used to return/pass user data from/to end points.
 * 
 * @author ahmed.abdelmonem
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	
	@ApiModelProperty(readOnly = true)
	private Long id;
	
	@ApiModelProperty(readOnly = true)
	private String created;

	@NotBlank
	@NotBlank(message = "User name can not be blank or null!")
	private String name;
	
	@Email
	@NotBlank(message = "User email can not be blank or null!")
	private String email;
}
