package com.company.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:messages.properties")
public class DepartmentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartmentManagementSystemApplication.class, args);
	}

}
