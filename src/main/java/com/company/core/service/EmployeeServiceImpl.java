package com.company.core.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.company.core.dto.EmployeeDTO;
import com.company.core.entity.Department;
import com.company.core.entity.Employee;
import com.company.core.repository.DepartmentRepository;
import com.company.core.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public Optional<Employee> addEmployee(EmployeeDTO dto) {
		Assert.hasText(dto.getName(), "invalid employee name");
		Assert.isTrue(dto.getSalary()>1000, "invalid employee salary"); //TODO : handle to custom exception using @ControllerAdvice
		Assert.isTrue(dto.getDepartment() != null && dto.getDepartment().getId() != null, "invalid department");
		Optional<Department> optionalDepartment = departmentRepository.findById(dto.getDepartment().getId());
		Assert.isTrue(optionalDepartment.isPresent(), "Invalid department");
		return Optional.of(employeeRepository.save(new Employee(dto.getName(),dto.getSalary(),optionalDepartment.get()))); //TODO : mapper
	}

}
