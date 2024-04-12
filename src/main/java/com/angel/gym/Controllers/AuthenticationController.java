package com.angel.gym.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angel.gym.Controllers.Dto.AuthCreateUser;
import com.angel.gym.Controllers.Dto.AuthLoginRequest;
import com.angel.gym.Controllers.Dto.AuthResponse;
import com.angel.gym.Services.UserDetailsServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@PostMapping("/sign-up")
	public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUser authCreateUser) {
		
		return new ResponseEntity<>(userDetailsServiceImpl.createUser(authCreateUser), HttpStatus.CREATED);
		
		
		
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
		
		return new ResponseEntity<>(userDetailsServiceImpl.loginUser(userRequest), HttpStatus.OK);
		
		
		
	}

}
