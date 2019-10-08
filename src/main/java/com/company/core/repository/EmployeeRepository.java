package com.company.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.core.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
