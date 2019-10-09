package com.company.core.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//A simple report that displays all employees sorted by salary.
	@GetMapping
	public ResponseEntity<List<EmployeeDTO>> emplyeesReport(
			@RequestParam(required=true , name="orderBy") String order) {
		return employeeService.getAllEmployeesOrderedBySalary()
				.map(employees->{
					return employees.stream()
							.map(e-> new EmployeeDTO(e.getId(), e.getName(),e.getSalary(),
									new DepartmentDTO(e.getDepartment().getId(), e.getDepartment().getName())))
							.collect(Collectors.toList());
				}).map(ResponseEntity::ok)
				.orElseThrow(() -> new RuntimeException("Invalid data !, please provide a employee details ."));
	}
}
