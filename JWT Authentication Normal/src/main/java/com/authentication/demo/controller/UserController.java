package com.authentication.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.demo.Dto.UserDto;
import com.authentication.demo.response.UserDeleteResponse;
import com.authentication.demo.serviceImpl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class UserController {
	
	/****
	 * @author sourabh namdev
	 * @category for user
	 */

	@Autowired
	UserServiceImpl userServiceImpl;

	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}

	@PostMapping("/user/save")
	public UserDto save(@RequestBody UserDto userDto) {
		log.info("----user Controller----");
		userServiceImpl.save(userDto);
		return userDto;
	}

	@GetMapping("/getAll/users")
	public List<UserDto> getUsers() {
		log.info("Inside UserController in getUsers()");
		List<UserDto> users = userServiceImpl.getUsers();
		log.info("leaving getUsers() in UserController");
		return users;
	}

	@DeleteMapping("/user/delete/{id}")
	public UserDeleteResponse deleteUser(@PathVariable Integer id) {
		log.info("Inside UserController in deleteUser()");
		return userServiceImpl.deleteUser(id);
	}
}
