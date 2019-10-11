package com.company.core.validator;

import java.util.function.BiConsumer;

import com.company.core.entity.OrderParams;
import com.company.core.exception.BusinessException;

@FunctionalInterface
public interface IRestValidator {

	BiConsumer<Object,OrderParams> validate(String expMsg) throws BusinessException;
}
