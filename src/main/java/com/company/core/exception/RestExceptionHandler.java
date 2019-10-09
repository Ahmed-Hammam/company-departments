package com.company.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	private static Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@Autowired
	private Environment env;
	
	@ExceptionHandler({Exception.class})
	protected ResponseEntity<Object> handleGeneralException(Exception e, WebRequest webRequest){
		LOG.error("general exception occured");
		String errorMessage = env.getProperty("MWASALATY00000");
		ErrorResource errorResource = new ErrorResource("MWASALATY00000", errorMessage);
		return handleExceptionInternal(e, errorResource, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
	}
	
	@ExceptionHandler({BusinessException.class})
	protected ResponseEntity<Object> handleMwasalatyException(Exception e,WebRequest webRequest){
		LOG.error("mwasalty exception occured");
		BusinessException exception = (BusinessException)e;
		String errorMessage = null;
		
		// validation
		if(null != exception.getErrorMessage())
			errorMessage = exception.getErrorMessage();
		else
			errorMessage = env.getProperty(exception.getErrorCode());
		
		if(null == errorMessage)
			errorMessage = env.getProperty("MWASALATY00000");
		
		ErrorResource errorResource = new ErrorResource(exception.getErrorCode(),errorMessage);	// JSON that will be returned
		return handleExceptionInternal(exception, errorResource, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
	}
}