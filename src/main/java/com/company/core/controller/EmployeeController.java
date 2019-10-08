package com.company.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.core.dto.DepartmentDTO;
import com.company.core.dto.EmployeeDTO;
import com.company.core.service.EmployeeService;

@RequestMapping("/v0/api/employees")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping
	public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO dto) {
		Assert.notNull(dto, "invalid request data");
		return employeeService.addEmployee(dto).map(e->{
			return new EmployeeDTO(e.getId(), e.getName(), e.getSalary(), new DepartmentDTO(e.getDepartment().getId(),e.getDepartment().getName()));
		}).map(ResponseEntity::ok)
				.orElseThrow(() -> new RuntimeException("Invalid data !, please provide a employee details ."));
	}
}
