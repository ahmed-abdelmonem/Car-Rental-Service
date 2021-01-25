package com.infor.m3.carrental.service.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infor.m3.carrental.service.domain.entity.User;

/**
 * The JPA repository for {@link User} entities.
 * 
 * @author ahmed.abdelmonem
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * find user with email
	 */
	Optional<User> findByEmail(String email);
}
