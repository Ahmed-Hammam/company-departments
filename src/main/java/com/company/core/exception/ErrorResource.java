package com.company.core.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name="error")
public class ErrorResource {

	private String errorCode;
	
	private String errorMessage;
	
	// to tell jackson to ignore this attribute during serialization if its value is null
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String errorDetail;

	public ErrorResource(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorDetail() {
		return errorDetail;
	}
	
}
