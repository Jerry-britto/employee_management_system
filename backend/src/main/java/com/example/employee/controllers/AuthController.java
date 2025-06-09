package com.example.employee.controllers;

import com.example.employee.models.AuthUser;
import com.example.employee.repository.UserRepository;
import com.example.employee.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerPost(@Valid @RequestBody AuthUser user) {
        System.out.println("POST /register endpoint hit");
        System.out.println("Request body: " + user.getUsername() +" "+user.getPassword());
        AuthUser existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existingUser != null){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginPost(@Valid @RequestBody AuthUser user, HttpServletResponse response) {
        System.out.println("POST /login endpoint hit");
        System.out.println("Request body: " + user.getUsername()+" "+user.getPassword());

        AuthUser existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        String jwt = jwtUtil.generateToken(user.getUsername());
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 24 hours
        response.addCookie(cookie);

        return ResponseEntity.ok("Successfully logged in");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("POST /auth/logout endpoint triggered"); // Fixed the log message too

        // Clear the JWT cookie
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire immediately
        response.addCookie(cookie);

        String username = "";
        try {
            String token = extractJwtFromRequest(request);
            if (token != null) {
                username = jwtUtil.getUsernameFromToken(token); // Use autowired instance
                System.out.println("User " + username + " logged out successfully");
            }
        } catch (Exception e) {
            System.out.println("Could not extract user info during logout: " + e.getMessage());
        }

        String responseMessage = username.isEmpty() ?
                "Logged out successfully" :
                username + " logged out successfully";

        return ResponseEntity.ok(responseMessage); // Return the constructed message
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(HttpServletRequest request) {
        try {
            String token = extractJwtFromRequest(request);
            if (token != null) {
                String username = jwtUtil.getUsernameFromToken(token);
                return ResponseEntity.ok("Authenticated as: " + username);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}