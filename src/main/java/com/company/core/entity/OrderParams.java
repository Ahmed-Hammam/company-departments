package com.company.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderParams {

	EMPLOYEES_COUNT("employees"),
	EMPLOYEE_SALARY("salary");
	
	private String orderValue;
	
}
