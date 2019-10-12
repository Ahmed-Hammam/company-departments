package com.company.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.core.dto.EmployeeDTO;
import com.company.core.dto.ResponseDTO;
import com.company.core.entity.Employee;
import com.company.core.entity.OrderParams;
import com.company.core.exception.BusinessException;
import com.company.core.mapper.EmployeeMapper;
import com.company.core.service.EmployeeService;
import com.company.core.util.ResultHandler;

import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/v0/api/employees")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping
	public ResponseEntity<ResponseDTO> addEmployee(@Valid @RequestBody EmployeeDTO dto) {
		log.info("receiving request for adding new department with payload {}",dto);
		return ResponseEntity.ok(new ResultHandler<EmployeeDTO, Employee, ResponseDTO,Void>()
				.doBusinessLogic(dto, employeeService.addEmployee(dto), 
						EmployeeMapper.mapEntitytoDTO(), 
						(reqData, order) -> {if(Objects.isNull(reqData)){
							throw new BusinessException("COMPANY008");}}
						,"COMPANY008", null, false , 0)
				.apply(dto));
	}
	
	//A simple report that displays all employees sorted by salary.
	@ApiImplicitParam(name = "orderBy", 
            required = true, 
            dataType = "string",
			value = "OrderBy Value that is used to order employees according to salary"
			+ ", expected value = salary")
	@GetMapping
	public ResponseEntity<ResponseDTO> emplyeesReport(
			@RequestParam(required=true , name="orderBy") String orderBy) {
		log.info("receiving request for generating employees report ordered by {}",orderBy);
		List<EmployeeDTO> empDTOs = new ArrayList<>();
		return ResponseEntity.ok(new ResultHandler<List<EmployeeDTO>, List<Employee>, 
				ResponseDTO,String>().doBusinessLogic(orderBy,empDTOs, 
						employeeService.getAllEmployeesOrderedBySalary(), 
						EmployeeMapper.mapListOfEntitytoListOfDTO(), 
						(reqData,orderParam)->{
							if(Objects.isNull(reqData) || !orderParam.getOrderValue().equals(reqData))
								throw new BusinessException("COMPANY002");
						}
						,"COMPANY008", OrderParams.EMPLOYEE_SALARY).apply(empDTOs)); 
	}
}
