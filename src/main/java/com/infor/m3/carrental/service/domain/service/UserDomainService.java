package com.infor.m3.carrental.service.domain.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.infor.m3.carrental.service.domain.entity.User;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * The service that manage {@link User} entities
 * 
 * @author ahmed.abdelmonem
 */
public interface UserDomainService {

	/**
	 * Create or update user
	 *
	 * @param user {@link User}
	 *
	 * @return The {@link User}
	 * @throws EntityAlreadyExists 
	 */
	User createOrUpdate(@NotNull User user);

	/**
	 * Gets the user with the provided id
	 *
	 * @param id User id
	 *
	 * @return The {@link User}
	 *
	 * @throws EntityNotFoundException
	 */
	User getById(@NotNull Long id) throws EntityNotFoundException;
	
	/**
	 * Gets the user with the provided email
	 *
	 * @param email User email
	 *
	 * @return The {@link User}
	 *
	 */
	Optional<User> getByEmail(@NotNull String email);


}
