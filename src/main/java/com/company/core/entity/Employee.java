package com.company.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Employee implements Serializable{

	private static final long serialVersionUID = 7917474017048055708L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	private Long id;
	
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
