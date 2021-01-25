package com.infor.m3.carrental.service.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.infor.m3.carrental.service.domain.entity.User;
import com.infor.m3.carrental.service.exception.EntityNotFoundException;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
public class UserControllerIT extends CarRentalBaseTestIT{
	
	@Test
    public void canRegisterUser() throws EntityNotFoundException {
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		
		long userId = registerUser(NAME, EMAIL);
		// get user from db
		User user = userDomainService.getById(userId);
		
		assertEquals(NAME, user.getName());
		assertEquals(EMAIL, user.getEmail());
	}
	
	@Test
    public void registerUserWithEmailAlreadyRegistered() {
		final String NAME = "User Name";
		final String EMAIL = "user@gmail.com";
		
		registerUser(NAME, EMAIL);
		try {
			registerUser("New name", EMAIL);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("Entity Already Exists"));
		}
	}
	
	@Test
    public void registerUserWithInvalidEmail() {
		final String NAME = "User Name";
		final String EMAIL = "invalid";
		
		try {
			registerUser(NAME, EMAIL);
			fail();
		}catch (Exception e) {
			assertTrue(e.getMessage().contains("Validation failed"));
		}
	}

}
