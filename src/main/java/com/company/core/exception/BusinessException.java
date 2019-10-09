package com.company.core.exception;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = -1231809319798583242L;	
	
	private String errorCode;
	private String errorMessage;
	private String errorDetail;
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public BusinessException(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "Exception : "+errorMessage;
	}
}
