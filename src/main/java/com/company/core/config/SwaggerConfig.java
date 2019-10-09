package com.company.core.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.Contact;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.company.core.controller"))
                .paths(PathSelectors.any())
                .build();
//                .apiInfo(getApiInfo());
    }

    /*private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Saga Pattern Implementation using Axon and Spring Boot",
                "App to demonstrate Saga Pattern using Axon and Spring Boot",
                "1.0.0",
                "Terms of Service",
                new Contact("Saurabh Dashora", "progressivecoder.com", "coder.progressive@gmail.com"),
                "",
                "",
                Collections.emptyList());
    }*/

}