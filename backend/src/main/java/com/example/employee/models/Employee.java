package com.example.employee.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

@Data
@Document(collection = "employees")
public class Employee {

    @Id
    private String id; // MongoDB's default _id is a String (ObjectId)
    
    @NotEmpty(message="Name is required")
    private String employeeName;
    
    @NotEmpty(message="Employee Designation is mandatory")
    private String employeeDesignation;
    
    @NotEmpty(message="Employee Job Role is mandatory")
    private String employeeJobRole;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeDesignation() {
		return employeeDesignation;
	}

	public void setEmployeeDesignation(String employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}

	public String getEmployeeJobRole() {
		return employeeJobRole;
	}

	public void setEmployeeJobRole(String employeeJobRole) {
		this.employeeJobRole = employeeJobRole;
	}
    
    
    
}
