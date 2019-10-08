package com.company.core.service;

import java.util.Optional;

import com.company.core.dto.DepartmentDTO;
import com.company.core.entity.Department;

public interface DepartmentService {

	Optional<Department> addDepartment(DepartmentDTO dto);
}
