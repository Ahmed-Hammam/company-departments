package com.company.core.validator;

import java.util.Objects;
import java.util.function.BiConsumer;

import com.company.core.entity.OrderParams;
import com.company.core.exception.BusinessException;

public class PostValidator implements IRestValidator{

	@Override
	public BiConsumer<Object, OrderParams> validate(String expMsg) throws BusinessException {
		return (reqData, order) -> {
			if(Objects.isNull(reqData))
				throw new BusinessException(expMsg);
		};
	}

}
