package com.infor.m3.carrental.service.application.service;

import com.infor.m3.carrental.service.dto.UserDTO;
import com.infor.m3.carrental.service.exception.EntityAlreadyExists;

/**
 * Service that handles user related calls commands using domain services
 * 
 * @author ahmed.abdelmonem
 */
public interface UserService {
	
	UserDTO registerUser(UserDTO userDTO) throws EntityAlreadyExists;
	

}
