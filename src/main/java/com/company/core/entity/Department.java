package com.company.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EnableJpaAuditing
@Entity
public class Department {
	
	public Department(String name) {
		this.name = name;
	}
	
	@Id @GeneratedValue private Long id;
	@Column(name="name",nullable=false,unique=true)
	private String name;
}
