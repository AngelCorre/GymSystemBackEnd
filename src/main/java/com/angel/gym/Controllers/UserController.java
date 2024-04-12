package com.angel.gym.Controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angel.gym.Controllers.Dto.UserDto;
import com.angel.gym.Models.UserEntity;
import com.angel.gym.Services.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user")
//@PreAuthorize("denyAll()")
@CrossOrigin("http://localhost:4200/")
public class UserController {

	@Autowired
	UserServiceImpl userService;
	SessionRegistry sessionRegistry;
	

	@PostMapping("/user")
	//@PreAuthorize("hasAuthority('CREATE')")
	public ResponseEntity<UserEntity> save(@RequestBody UserDto userDto) {

		return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.CREATED);

	}

	@PutMapping("/user/{id}")
	//@PreAuthorize("hasAuthority('UPDATE')")
	public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {

		return userService.updateUser(id, userDto);

	}

	@GetMapping("/user/{id}")
	//@PreAuthorize("permitAll()")
	public ResponseEntity<UserEntity> findUser(@PathVariable Long id) {

		return userService.findUser(id);

	}

	@GetMapping("/users")
	//@PreAuthorize("permitAll()")
	public List<UserEntity> findUsers() {

		return userService.findUsers();

	}

	@DeleteMapping("/user/{id}")
	//@PreAuthorize("hasAuthority('DELETE')")
	public void DeleteUser(@PathVariable Long id) {

		userService.DeleteUser(id);

	}
	
	@GetMapping("/index")
	//@PreAuthorize("hasAuthority('READ')")
	public ResponseEntity<?> index() {
		
		String sessionId = "";
		User userObject = null;
		
		List<Object> sessions = sessionRegistry.getAllPrincipals();
		
		for(Object session : sessions) {
			
			if (session instanceof User) {
				
				userObject = (User) session;
				
			}
			
			List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false);
			
			for(SessionInformation sessionInformation : sessionInformations) {
				
				sessionId = sessionInformation.getSessionId();
				
			}
			
		}
		
		Map<String, Object> response = new HashMap<>();
		
		response.put("response", "helloworld");
		response.put("sessionId", sessionId);
		response.put("sessionUser", userObject);
		
		
		return ResponseEntity.ok(response);
		
	}



}
