package com.example.employee.services;

import java.util.List;

import com.example.employee.services.EmployeeService;
import com.example.employee.models.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import com.example.employee.utils.ResourceNotFoundException;

@Service
@AllArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

    @Override
    public Employee registerEmployee(Employee Employee) {
        return empRepo.save(Employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    @Override
    public Employee getEmployeeById(String id) {
        return empRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found"));
    }

    @Override
    public void deleteEmployeeRecord(String id) {
        // Optionally, check if Employee exists before deleting to throw exception if not found
        if (!empRepo.existsById(id)) {
            throw new ResourceNotFoundException("Employee with ID " + id + " not found");
        }
        empRepo.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {

        Employee existingEmp = empRepo.findById(employee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + employee.getId() + " not found"));

        existingEmp.setEmployeeName(employee.getEmployeeName());
        existingEmp.setEmployeeDesignation(employee.getEmployeeDesignation());
        existingEmp.setEmployeeJobRole(employee.getEmployeeJobRole());

        return empRepo.save(existingEmp);
    }
}
