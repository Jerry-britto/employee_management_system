package com.example.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public User registerUser(User user) {
		
		userRepo.findB
		
		
		return userRepo.save(user);
	}

}
