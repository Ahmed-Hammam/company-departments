package com.company.core.exception;

import java.util.Objects;

import com.company.core.entity.OrderParams;

public class RestValidator {

	public static final void validatePostRequest(Object obj) {
		if(Objects.isNull(obj))
			throw new BusinessException("COMPANY001");
	}
	
	public static final void validateGetWithOrderParams(Object param, OrderParams correctValue) {
		if(Objects.isNull(param) || !correctValue.getOrderValue().equals(param))
			throw new BusinessException("COMPANY002");
	}
}
