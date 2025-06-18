package com.authentication.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.authentication.demo.Dto.UserDto;
import com.authentication.demo.repository.UserRepository;
import com.authentication.demo.response.UserDeleteResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;

	public UserDto save(UserDto userDto) {
		log.info("Inside UserServiceImpl in createUser()");	

		// code for Encrypt
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("souru_genius");
		String encryptedText = encryptor.encrypt(userDto.getPassword());
		userDto.setPassword(encryptedText);
		userRepository.save(userDto);
		return userDto;

	}

	public List<UserDto> getUsers() {
		log.info("Inside UserServiceImpl in getUsers()");
		return userRepository.findAll();

	}

	public UserDeleteResponse deleteUser(int id) {
		log.info("Inside UserServiceImpl in deleteUser()");
		Optional<UserDto> existingUser = userRepository.findById(id);
		if (existingUser.isPresent()) {
			UserDeleteResponse message = new UserDeleteResponse();
			userRepository.deleteById(id);
			message.setMessage("User Deleted");
			log.info("leaving deleteUser() in UserServiceImpl");
			return message;
		} else {
			throw new RuntimeException("user with this id " + id + " not found");
		}

	}	

}
