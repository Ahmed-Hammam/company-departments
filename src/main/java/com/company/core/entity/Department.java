package com.company.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EnableJpaAuditing
@Entity
public class Department implements Serializable{
	
	private static final long serialVersionUID = -5623192722748181234L;
	
	public Department(String name) {
		this.name = name;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
	@Column(name="name",nullable=false,unique=true)
	private String name;
}
