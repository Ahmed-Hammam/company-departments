package com.company.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DepartmentReportDTO {

	private Long id;
	private String name;
	private Long employeesCount;
}
