package com.company.core.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.company.core.dto.EmployeeDTO;
import com.company.core.entity.Department;
import com.company.core.entity.Employee;
import com.company.core.exception.BusinessException;
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
		isValid(dto);
		Optional<Department> optionalDepartment = departmentRepository.findById(dto.getDepartment().getId());
		return Optional.of(employeeRepository.save(new Employee(dto.getName(),dto.getSalary(),optionalDepartment.get()))); //TODO : mapper
	}

	@Override
	public Optional<List<Employee>> getAllEmployeesOrderedBySalary() {
		return employeeRepository.findAllByOrderBySalaryAsc();
	}

	private void isValid(EmployeeDTO dto) throws BusinessException{
		if(!StringUtils.hasText(dto.getName()))
			throw new BusinessException("COMPANY004");
		if(dto.getSalary() < 1000)
			throw new BusinessException("COMPANY005");
		if(Objects.isNull(dto.getDepartment()) || Objects.isNull(dto.getDepartment().getId()))
			throw new BusinessException("COMPANY006");
		boolean exists = departmentRepository.existsById(dto.getDepartment().getId());
		if(!exists)
			throw new BusinessException("COMPANY007");
	}
}
