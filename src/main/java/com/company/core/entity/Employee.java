package com.company.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Employee {

	@Id @GeneratedValue private Long id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="salary",nullable=false)
	private Long salary;
	
	@ManyToOne
	private Department department;

	public Employee(String name, Long salary, Department department) {
		this.name = name;
		this.salary = salary;
		this.department = department;
	}
	
	
}
