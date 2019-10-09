package com.company.core.service;

import java.util.List;
import java.util.Optional;

import com.company.core.dto.EmployeeDTO;
import com.company.core.entity.Employee;

public interface EmployeeService {

	Optional<Employee> addEmployee(EmployeeDTO dto);
	Optional<List<Employee>> getAllEmployeesOrderedBySalary();
}
