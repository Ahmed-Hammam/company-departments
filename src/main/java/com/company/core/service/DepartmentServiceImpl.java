package com.company.core.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.company.core.dto.DepartmentDTO;
import com.company.core.entity.Department;
import com.company.core.exception.BusinessException;
import com.company.core.repository.DepartmentRepository;
import com.company.core.repository.EmployeeRepository;
import com.company.core.util.CustomStringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Override
	public Optional<Department> addDepartment(DepartmentDTO dto) {
		log.info("performing add new department");
		isValid(dto);
		log.info("valid data, check if department with the name {} already exist",dto.getName());
		if(Objects.nonNull(departmentRepo.findByName(dto.getName())))
			throw new BusinessException("COMPANY009");
		return Optional.of(departmentRepo.save(new Department(dto.getName()))); 
	}

	@Override
	public Optional<List<Object[]>> getDepartmentsOrderedByEmployeesCount() {
		log.info("retreiving all departments ordered by employees count.");
		return employeeRepo.getDepartmentsOrderByEmployeesCount();
	}

	private void isValid(DepartmentDTO dto) throws BusinessException{
		if(!StringUtils.hasText(dto.getName()))
			throw new BusinessException("COMPANY003");
		if(CustomStringUtils.isNumber(dto.getName()))
			throw new BusinessException("COMPANY003");
	}
}
