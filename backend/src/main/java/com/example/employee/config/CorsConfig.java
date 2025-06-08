package com.example.employee.config;

import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
import java.util.*;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Change this to your frontend URL
        configuration.setAllowedOrigins(List.of("http://localhost:8080")); // React typically runs on 3000
        // Or if using Angular: "http://localhost:4200"
        // Or if using Vue: "http://localhost:8081"

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // IMPORTANT for cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}