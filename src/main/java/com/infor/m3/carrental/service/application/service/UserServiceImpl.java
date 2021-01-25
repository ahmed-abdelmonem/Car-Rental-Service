package com.infor.m3.carrental.service.application.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.infor.m3.carrental.service.domain.entity.User;
import com.infor.m3.carrental.service.domain.service.UserDomainService;
import com.infor.m3.carrental.service.dto.UserDTO;
import com.infor.m3.carrental.service.exception.EntityAlreadyExists;

/**
 * 
 * @author ahmed.abdelmonem
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserDomainService userDomainService;

	private final ConversionService conversionService;

	public UserServiceImpl(UserDomainService userDomainService, ConversionService conversionService) {
		this.userDomainService = userDomainService;
		this.conversionService = conversionService;
	}

	@Override
	public UserDTO registerUser(UserDTO userDTO) throws EntityAlreadyExists {
		if (userDomainService.getByEmail(userDTO.getEmail()).isPresent()) {
			throw new EntityAlreadyExists("User Already Exists!");
		}
		User newUser = userDomainService.createOrUpdate(new User(userDTO.getName(), userDTO.getEmail()));
		return conversionService.convert(newUser, UserDTO.class);
	}

}
