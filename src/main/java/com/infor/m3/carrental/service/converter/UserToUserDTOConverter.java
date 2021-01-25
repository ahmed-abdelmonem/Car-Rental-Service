package com.infor.m3.carrental.service.converter;

import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.infor.m3.carrental.service.domain.entity.User;
import com.infor.m3.carrental.service.dto.UserDTO;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

	@Override
	public UserDTO convert(User user) {

		return UserDTO.builder()
				.id(user.getId())
				.created(user.getCreated().format(DateTimeFormatter.ISO_DATE_TIME))
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}

}
