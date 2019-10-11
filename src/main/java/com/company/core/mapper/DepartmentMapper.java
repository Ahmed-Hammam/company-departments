package com.company.core.mapper;

import java.util.function.Function;

import com.company.core.dto.DepartmentDTO;
import com.company.core.entity.Department;

public class DepartmentMapper {

	public static final Function<Department,DepartmentDTO> mapEntitytoDTO(){
		return entity ->{
			return new DepartmentDTO(entity.getId(),entity.getName());
		};
	}
	
	public static final Function<DepartmentDTO,Department> mapDTOtoEntity(){
		return dto ->{
			return new Department(dto.getName());
		};
	}
}
