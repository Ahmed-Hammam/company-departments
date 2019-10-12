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

import com.company.core.dto.DepartmentDTO;
import com.company.core.dto.DepartmentReportDTO;
import com.company.core.dto.ResponseDTO;
import com.company.core.entity.Department;
import com.company.core.entity.OrderParams;
import com.company.core.exception.BusinessException;
import com.company.core.mapper.DepartmentMapper;
import com.company.core.mapper.DepartmentReportMapper;
import com.company.core.service.DepartmentService;
import com.company.core.util.ResultHandler;

import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/v0/api/departments")
@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ResponseDTO> addDepartment(@Valid @RequestBody DepartmentDTO dto) {
		log.info("receiving request for adding new department with payload {}",dto);
		return ResponseEntity.ok(new ResultHandler<DepartmentDTO, Department, ResponseDTO,Void>()
				.doBusinessLogic(dto, departmentService.addDepartment(dto), 
						DepartmentMapper.mapEntitytoDTO(), 
						(reqData, order) -> {if(Objects.isNull(reqData)){
							throw new BusinessException("COMPANY008");}}
						,"COMPANY008", null, false , 0)
				.apply(dto)); 
	}
	
	//A simple report that displays all departments sorted by employees count.
	@ApiImplicitParam(name = "orderBy", 
            required = true, 
            dataType = "string",
			value = "OrderBy Value that is used to order departments according to employees count"
			+ ", expected value = employees")
	@GetMapping
	public ResponseEntity<ResponseDTO> getDepartmentsReport(@RequestParam("orderBy") String orderBy){
		log.info("receiving request for generating departments report ordered by {}",orderBy);
		List<DepartmentReportDTO> depReport = new ArrayList<>();
		return ResponseEntity.ok(new ResultHandler<List<DepartmentReportDTO>, List<Object[]>, 
				ResponseDTO,String>().doBusinessLogic(orderBy,depReport, departmentService.getDepartmentsOrderedByEmployeesCount(), 
						DepartmentReportMapper.mapEntitytoDTO(), 
						(reqData,orderParam)->{
							if(Objects.isNull(reqData) || !orderParam.getOrderValue().equals(reqData))
								throw new BusinessException("COMPANY002");
						}
						,"COMPANY008", OrderParams.EMPLOYEES_COUNT).apply(depReport)); 
	}
	
}
