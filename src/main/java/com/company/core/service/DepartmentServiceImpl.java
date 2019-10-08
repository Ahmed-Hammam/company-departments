package com.company.core.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.company.core.dto.DepartmentDTO;
import com.company.core.entity.Department;
import com.company.core.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Override
	public Optional<Department> addDepartment(DepartmentDTO dto) {
		Assert.hasText(dto.getName(), "Name should contain any text");
		return Optional.of(departmentRepo.save(new Department(dto.getName()))); //TODO: mapper
	}

}
