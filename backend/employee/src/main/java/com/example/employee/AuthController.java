package com.example.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> signup(@RequestBody User user) {
		User registeredUser = userService.registerUser(user);
		return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		    
			return ResponseEntity.ok("login successful");	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
