package com.company.core.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.core.dto.DepartmentDTO;
import com.company.core.dto.EmployeeDTO;
import com.company.core.entity.Employee;

public class EmployeeMapper {

	public static final Function<Employee,EmployeeDTO> mapEntitytoDTO(){
		return entity ->{
			return new EmployeeDTO(entity.getId(), entity.getName(), entity.getSalary(), 
					new DepartmentDTO(entity.getDepartment().getId(),entity.getDepartment().getName()));
		};
	}
	
	public static final Function<List<Employee>,List<EmployeeDTO>> mapListOfEntitytoListOfDTO(){
		return employees->{
			return employees.stream()
					.map(e-> new EmployeeDTO(e.getId(), e.getName(),e.getSalary(),
							new DepartmentDTO(e.getDepartment().getId(), e.getDepartment().getName())))
					.collect(Collectors.toList());
		};
	}
}
