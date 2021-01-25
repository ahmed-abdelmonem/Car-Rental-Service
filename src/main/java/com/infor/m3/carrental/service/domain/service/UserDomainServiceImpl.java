package com.infor.m3.carrental.service.domain.service;


import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infor.m3.carrental.service.domain.entity.User;
import com.infor.m3.carrental.service.domain.repository.UserRepository;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * The service that manage {@link User} entities
 * 
 * @author ahmed.abdelmonem
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {

	private final UserRepository userRepository;

	public UserDomainServiceImpl(UserRepository repository) {
		this.userRepository = repository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public User createOrUpdate(User user) {
		return userRepository.saveAndFlush(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getById(Long id) throws EntityNotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found ID: " + id));
	}

	@Override
	public Optional<User> getByEmail(@NotNull String email) {
		return userRepository.findByEmail(email);
	}
}
