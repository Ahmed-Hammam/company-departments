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
import com.company.core.service.DepartmentService;

@RequestMapping("/v0/api/departments")
@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	//TODO : controller layer validation request shouldn't be empty
	@PostMapping(consumes = "application/json")
	public ResponseEntity<DepartmentDTO> addDepartment(@Valid @RequestBody DepartmentDTO dto) {
		Assert.notNull(dto, "invalid request data");
		return departmentService.addDepartment(dto).map(e -> {
			return new DepartmentDTO(e.getId(),e.getName());})
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new RuntimeException("Invalid data !, please provide a valid name ."));
	}
	
	//A simple report that displays all departments sorted by employees count.
	//A simple report that displays all employees sorted by salary.
}
