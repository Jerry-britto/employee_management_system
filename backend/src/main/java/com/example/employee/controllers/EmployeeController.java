package com.example.employee.controllers;

import java.util.List;
import com.example.employee.models.Employee;
import com.example.employee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;

	// CREATE
	@PostMapping
	public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody Employee employee) {
		Employee savedEmployee = empService.registerEmployee(employee);
		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	}

	// READ ALL
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(empService.getAllEmployees());
	}

	// READ BY ID
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
		return ResponseEntity.ok(empService.getEmployeeById(id));
	}
	
	// UPDATE
	@PutMapping
	public ResponseEntity<?> updateEmployee(@Valid @RequestBody Employee employee) {

	    // ID is required
	    if (employee.getId() == null) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Employee ID is required for update.");
	    }

	    return ResponseEntity.ok(empService.updateEmployee(employee));
	}


	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployeeRecord(@PathVariable String id) {
		empService.deleteEmployeeRecord(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
