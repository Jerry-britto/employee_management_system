package com.example.employee;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
	
	// sign up service
	User registerUser(User user);
	
	
}
