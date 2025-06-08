package com.example.employee.config;

import com.example.employee.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("=== CONFIGURING SECURITY FILTER CHAIN ===");

        http
                .csrf(csrf -> {
                    csrf.disable();
                    System.out.println("CSRF disabled");
                })
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/register", "/auth/login").permitAll();
                    auth.anyRequest().authenticated();
                    System.out.println("Authorization rules configured: /register and /login permitAll, others authenticated");
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    System.out.println("Session management set to STATELESS");
                })
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        System.out.println("JWT filter added before UsernamePasswordAuthenticationFilter");
        System.out.println("=== SECURITY FILTER CHAIN CONFIGURED ===");

        return http.build();
    }
}