package com.company.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.core.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	@Query("SELECT d,COUNT(e.id)  FROM Employee e LEFT JOIN e.department d GROUP BY d.name ORDER BY COUNT(e.id)")
	Optional<List<Object[]>> getDepartmentsOrderByEmployeesCount();
	
	Optional<List<Employee>> findAllByOrderBySalaryAsc();
}
