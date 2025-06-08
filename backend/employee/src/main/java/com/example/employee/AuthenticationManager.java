package com.example.employee;

import javax.naming.AuthenticationException;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface AuthenticationManager {
		
	Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
