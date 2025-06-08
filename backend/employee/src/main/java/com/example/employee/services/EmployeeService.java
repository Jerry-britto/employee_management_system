package com.example.employee;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
	
	Employee registerEmployee(Employee employee);
	
	List<Employee> getAllEmployees();
	
	Employee getEmployeeById(String Id);
	
	Employee updateEmployee(Employee employee);
	
	void deleteEmployeeRecord(String id);
}
