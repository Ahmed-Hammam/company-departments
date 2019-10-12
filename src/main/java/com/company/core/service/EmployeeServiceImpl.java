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
import com.company.core.util.CustomStringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public Optional<Employee> addEmployee(EmployeeDTO dto) {
		log.info("performing add new employee");
		isValid(dto);
		log.info("valid data, check if department with the id {} already exist",dto.getDepartment().getId());
		Optional<Department> optionalDepartment = departmentRepository.findById(dto.getDepartment().getId());
		log.info("valid department, adding new employee to department {} ",optionalDepartment.get());
		return Optional.of(employeeRepository.save(new Employee(dto.getName(),dto.getSalary(),optionalDepartment.get()))); //TODO : mapper
	}

	@Override
	public Optional<List<Employee>> getAllEmployeesOrderedBySalary() {
		log.info("retreiving all employees ordered by employees salary.");
		return employeeRepository.findAllByOrderBySalaryAsc();
	}

	private void isValid(EmployeeDTO dto) throws BusinessException{
		if(!StringUtils.hasText(dto.getName()))
			throw new BusinessException("COMPANY004");
		if(CustomStringUtils.isNumber(dto.getName()))
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
