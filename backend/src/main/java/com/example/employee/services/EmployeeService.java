package com.example.employee.services;

import java.util.List;

import com.example.employee.models.Employee;
import org.springframework.stereotype.Service;

//@Service
public interface EmployeeService {
	Employee registerEmployee(Employee employee);
	List<Employee> getAllEmployees();
	Employee getEmployeeById(String id);
	Employee updateEmployee(Employee employee);
	void deleteEmployeeRecord(String id);
}
