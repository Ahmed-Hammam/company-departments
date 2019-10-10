package com.company.core.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.core.dto.DepartmentDTO;
import com.company.core.dto.DepartmentReportDTO;
import com.company.core.dto.ResponseDTO;
import com.company.core.entity.Department;
import com.company.core.entity.OrderParams;
import com.company.core.exception.BusinessException;
import com.company.core.exception.RestValidator;
import com.company.core.service.DepartmentService;

@RequestMapping("/v0/api/departments")
@RestController
public class DepartmentController {

	// http://localhost:8080/swagger-ui.html
	// http://localhost:8080/h2
	
	// https://www.javadevjournal.com/spring-boot/spring-boot-hikari/
	// https://dzone.com/articles/conditional-pagination-and-sorting-using-restful-w
	// http://progressivecoder.com/advanced-swagger-configuration-with-spring-boot/
	// https://www.baeldung.com/properties-with-spring
	// https://www.baeldung.com/spring-data-sorting
	// https://stackoverflow.com/questions/25486583/how-to-use-orderby-with-findall-in-spring-data
	// https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/
	// https://howtodoinjava.com/spring-boot2/h2-database-example/
	
//	TODO : refactor sort implementation like dzone example 
	//TODO : provide log
	//TODO : complete CRUD operation
	//TODO : connection pool implementation	
	//TODO : mapper from dto to entity and opposite
	
	
	@Autowired
	private DepartmentService departmentService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ResponseDTO> addDepartment(@Valid @RequestBody DepartmentDTO dto) {
		long start = System.currentTimeMillis();
		RestValidator.validatePostRequest(dto);
		DepartmentDTO depDTO = departmentService.addDepartment(dto)
				.map(e -> {return new DepartmentDTO(e.getId(),e.getName());})
				.orElseThrow(() -> new BusinessException("COMPANY008"));
		long end = System.currentTimeMillis();
		ResponseDTO response = new ResponseDTO(depDTO, end-start);
		return ResponseEntity.ok(response);
	}
	
	//A simple report that displays all departments sorted by employees count.
	@GetMapping
	public ResponseEntity<ResponseDTO> getDepartmentsReport(@RequestParam("orderBy") String orderBy,
			@RequestParam(defaultValue="ASC", name="direction", required=false) String direction, 
			@RequestParam(name="page", defaultValue = "1", required=false) int page,
			@RequestParam(name="size", defaultValue = "10", required=false) int size){
		
		long start = System.currentTimeMillis();
		RestValidator.validateGetWithOrderParams(orderBy,OrderParams.EMPLOYEES_COUNT);
		
		Optional<List<Object[]>> optionalDepartments = 
				departmentService.getDepartmentsOrderedByEmployeesCount();
		
		if(!optionalDepartments.isPresent())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		List<DepartmentReportDTO> depReport = optionalDepartments.map(departments -> {
			return departments.stream()
			.map(e-> {DepartmentReportDTO reportDTO = new DepartmentReportDTO(
					((Department) e[0]).getName(),(Long) e[1]);
				return reportDTO;
			})
			.collect(Collectors.toList());
		}).orElseThrow(() -> new BusinessException("COMPANY008"));
		long end = System.currentTimeMillis();
		ResponseDTO response = new ResponseDTO(depReport, end-start);
		return ResponseEntity.ok(response);
		/*return departmentService.getDepartmentsOrderedByEmployeesCount()
				.map(departments ->{return departments.stream()
						.map(e-> new DepartmentDTO(((Department) e).getId(), 
							((Department) e).getName())).collect(Collectors.toList());})
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new BusinessException("COMPANY008"));*/
	}
	
}
