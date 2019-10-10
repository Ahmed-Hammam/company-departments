/*package com.company.core.service;

import java.util.Optional;
import java.util.function.Function;

import com.company.core.dto.BaseDTO;
import com.company.core.entity.BaseEntity;
import com.company.core.exception.BusinessException;

public class ResultProvider<M extends BaseDTO,R> {
	
	
	
	public R result(Optional<? extends BaseEntity> data ,Function<M,R> resultFunction, String err) throws BusinessException{
//		BaseDTO m = createObjects(BaseDTO.class);
		M m = (M) createObjects(BaseDTO.class);
		return data.map(e-> new M(e)).map(resultFunction).orElseThrow(()-> new BusinessException(err));
	}

	BaseDTO createObjects(Class<BaseDTO> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
	
}
*/