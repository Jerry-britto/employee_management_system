package com.example.employee.filters;

import com.example.employee.utils.JwtUtil;
import com.example.employee.utils.ResourceNotFoundException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
            "/auth/register",
            "/auth/login",
            "/employee/records",
            "/employee/records/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        String method = request.getMethod();

        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request Path: " + requestPath);
        System.out.println("HTTP Method: " + method);
        System.out.println("Is Public Endpoint: " + isPublicEndpoint(requestPath));

        // Skip JWT validation for public endpoints
        if (isPublicEndpoint(requestPath)) {
            System.out.println("Skipping JWT validation for public endpoint: " + requestPath);
            chain.doFilter(request, response);
            return;
        }

        String jwt = null;
        String username = null;

        // Debug cookies
        if (request.getCookies() != null) {
            System.out.println("Available cookies:");
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                System.out.println("  - " + cookie.getName() + ": " + cookie.getValue());
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        } else {
            System.out.println("No cookies found in request");
        }

        if (jwt != null) {
            System.out.println("Found jwt token - " + jwt);
            try {
                if (jwtUtil.validateToken(jwt)) {
                    username = jwtUtil.getUsernameFromToken(jwt);
                    System.out.println("JWT is valid, username: " + username);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                username, null, null);
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        System.out.println("Authentication set in SecurityContext");
                    }
                } else {
                    System.out.println("JWT validation failed");
                }
            } catch (Exception e) {
                System.out.println("JWT validation error: " + e.getMessage());
            }
        } else {
            System.out.println("JWT token not found for protected endpoint: " + requestPath);
            throw new ResourceNotFoundException("JWT token not found");
        }

        System.out.println("=== END JWT FILTER DEBUG ===");
        chain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String requestPath) {
//        boolean isPublic = PUBLIC_ENDPOINTS.stream().anyMatch(requestPath::startsWith);
//        System.out.println("Checking if '" + requestPath + "' is public: " + isPublic);
//        return isPublic;


        return PUBLIC_ENDPOINTS.stream().anyMatch(publicPath ->
                pathMatcher.match(publicPath, requestPath));
    }
}