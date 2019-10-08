package com.company.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.core.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
