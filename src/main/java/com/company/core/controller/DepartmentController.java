package com.company.core.controller;

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
import com.company.core.entity.Department;
import com.company.core.entity.OrderParams;
import com.company.core.exception.RestValidator;
import com.company.core.service.DepartmentService;

@RequestMapping("/v0/api/departments")
@RestController
public class DepartmentController {

	// http://localhost:8080/swagger-ui.html
	// http://localhost:8080/h2
	
	// https://dzone.com/articles/conditional-pagination-and-sorting-using-restful-w
	// http://progressivecoder.com/advanced-swagger-configuration-with-spring-boot/
	// https://www.baeldung.com/properties-with-spring
	// https://www.baeldung.com/spring-data-sorting
	// https://stackoverflow.com/questions/25486583/how-to-use-orderby-with-findall-in-spring-data
	// https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/
	// https://howtodoinjava.com/spring-boot2/h2-database-example/
	
	//TODO : refactor sort implementation like dzone example 
	//TODO : check which is better @NamedQuery or @Query
	//TODO : validate using exceptions and edit message.prperties
	//TODO : refactor code and avoid nullPointer if .map() contains null obj at controller
	//TODO : provide log
	//TODO : complete CRUD operation
	//TODO : connection pool implementation	
	//TODO : mapper from dto to entity and opposite
	
	@Autowired
	private DepartmentService departmentService;
	
	//TODO : controller layer validation request shouldn't be empty
	@PostMapping(consumes = "application/json")
	public ResponseEntity<DepartmentDTO> addDepartment(@Valid @RequestBody DepartmentDTO dto) {
		RestValidator.validatePostRequest(dto);
		return departmentService.addDepartment(dto).map(e -> {
			return new DepartmentDTO(e.getId(),e.getName());})
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new RuntimeException("Invalid data !, please provide a valid name ."));
	}
	
	//TODO : user DepartmentReportDTO
	//A simple report that displays all departments sorted by employees count.
	@GetMapping
	public ResponseEntity<?> getDepartmentsReport(@RequestParam("orderBy") String orderBy,
			@RequestParam(defaultValue="ASC", name="direction", required=false) String direction, 
			@RequestParam(name="page", defaultValue = "1", required=false) int page,
			@RequestParam(name="size", defaultValue = "10", required=false) int size){
		RestValidator.validateGetWithOrderParams(orderBy,OrderParams.EMPLOYEES_COUNT);
		return departmentService.getDepartmentsOrderedByEmployeesCount()
				.map(departments ->{
					return departments.stream().map(e-> new DepartmentDTO(((Department) e).getId(), ((Department) e).getName())).collect(Collectors.toList());
				})
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new RuntimeException("No departments found"));
	}
	
}
