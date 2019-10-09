package com.company.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.core.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{


	/*
	 * select d.* , count(e.id)
from companydb.employee as e
left join companydb.department d on d.id = e.department_id
group by d.name
order by count(e.id)
	 */
	//TODO : select d.name and count(e.id)
	//TODO : think about @NamedQuery maybe better performance or think to restructure query
	@Query("SELECT d FROM Employee e LEFT JOIN e.department d GROUP BY d.name ORDER BY COUNT(e.id)")
	Optional<List<?>> getDepartmentsOrderByEmployeesCount();
	
	Optional<List<Employee>> findAllByOrderBySalaryAsc();
}
