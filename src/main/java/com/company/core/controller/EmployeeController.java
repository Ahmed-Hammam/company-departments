package com.company.core.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.core.dto.DepartmentDTO;
import com.company.core.dto.EmployeeDTO;
import com.company.core.dto.ResponseDTO;
import com.company.core.entity.OrderParams;
import com.company.core.exception.BusinessException;
import com.company.core.exception.RestValidator;
import com.company.core.service.EmployeeService;

@RequestMapping("/v0/api/employees")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping
	public ResponseEntity<ResponseDTO> addEmployee(@Valid @RequestBody EmployeeDTO dto) {
		long start = System.currentTimeMillis();
		RestValidator.validatePostRequest(dto);
		EmployeeDTO empDTO = employeeService.addEmployee(dto).map(e->{
			return new EmployeeDTO(e.getId(), e.getName(), e.getSalary(), 
					new DepartmentDTO(e.getDepartment().getId(),e.getDepartment().getName()));
		}).orElseThrow(() -> new BusinessException("COMPANY008"));
		long end = System.currentTimeMillis();
		ResponseDTO response = new ResponseDTO(empDTO, end-start);
		return ResponseEntity.ok(response);
	}
	
	//A simple report that displays all employees sorted by salary.
	@GetMapping
	public ResponseEntity<ResponseDTO> emplyeesReport(
			@RequestParam(required=true , name="orderBy") String orderBy) {
		long start = System.currentTimeMillis();
		RestValidator.validateGetWithOrderParams(orderBy,OrderParams.EMPLOYEE_SALARY);
		List<EmployeeDTO> empDTOs = employeeService.getAllEmployeesOrderedBySalary()
				.map(employees->{
					return employees.stream()
							.map(e-> new EmployeeDTO(e.getId(), e.getName(),e.getSalary(),
									new DepartmentDTO(e.getDepartment().getId(), e.getDepartment().getName())))
							.collect(Collectors.toList());
				}).orElseThrow(() -> new BusinessException("COMPANY008"));
		long end = System.currentTimeMillis();
		ResponseDTO response = new ResponseDTO(empDTOs, end-start);
		return ResponseEntity.ok(response);
	}
}
