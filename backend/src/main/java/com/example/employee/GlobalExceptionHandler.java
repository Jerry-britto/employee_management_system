package com.example.employee;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.employee.utils.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	  @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage())
	        );
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
	  
	  @ExceptionHandler(ResourceNotFoundException.class)
	  public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
	      Map<String, String> error = new HashMap<>();
	      error.put("error", ex.getMessage());
	      return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	  }

}


