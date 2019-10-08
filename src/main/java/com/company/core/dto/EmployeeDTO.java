package com.company.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeDTO {

	private Long id;
	private String name;
	private Long salary;
	private DepartmentDTO department;
}
