
package com.authentication.demo.controller;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.demo.Dto.UserDto;
import com.authentication.demo.config.JwtTokenUtil;
import com.authentication.demo.exception.InvalidCredentialsException;
import com.authentication.demo.repository.UserRepository;
import com.authentication.demo.request.JwtRequest;
import com.authentication.demo.response.JwtResponse;
import com.authentication.demo.serviceImpl.JwtUserDetailsServiceImpl;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {

		try {
			UserDto user = userRepository.findByUsername(username);
			if (ObjectUtils.isEmpty(user)) {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}

			else {
				StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
				encryptor.setPassword("souru_genius");
				StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
				decryptor.setPassword("souru_genius");
				String decryptedText = decryptor.decrypt(user.getPassword());
				if (username.equals(user.getUsername()) && password.equals(decryptedText)) {
				} else {
					throw new InvalidCredentialsException("INVALID_CREDENTIALS");
				}

			}

		} catch (DisabledException e) {
			throw new InvalidCredentialsException("INVALID_CREDENTIALS");
		} catch (BadCredentialsException e) {
			throw new InvalidCredentialsException("INVALID_CREDENTIALS");
		}
	}

}
