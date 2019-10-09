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
import com.company.core.entity.Department;
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
	//https://howtodoinjava.com/spring-boot2/h2-database-example/
	
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
	
	//TODO : user DepartmentReportDTO
	//A simple report that displays all departments sorted by employees count.
	@GetMapping(params = { "orderBy", "direction", "page","size" })
	public ResponseEntity<List<DepartmentDTO>> getDepartmentsReport(){
		
		return departmentService.getDepartmentsOrderedByEmployeesCount()
				.map(departments ->{
					return departments.stream().map(e-> new DepartmentDTO(((Department) e).getId(), ((Department) e).getName())).collect(Collectors.toList());
				})
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new RuntimeException("No departments found"));
	}
	
}
