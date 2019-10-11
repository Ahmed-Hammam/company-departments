package com.company.core.util;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import com.company.core.dto.ResponseDTO;
import com.company.core.entity.OrderParams;
import com.company.core.exception.BusinessException;
import com.company.core.validator.IRestValidator;

public class ResultHandler<T,E,R,P> {

	public Function<T,R> doBusinessLogic(T t,Optional<E> entity, Function<E,T> mapper ,
			IRestValidator v, String expMsg, OrderParams params) {
		return resp -> {
			long start = System.currentTimeMillis();
			v.validate(expMsg).accept(t, params);
			if(!entity.isPresent()){
				long end = System.currentTimeMillis();
				R response = (R) new ResponseDTO(new ArrayList<>(1), end-start);
				return response;
			}
			T dto = entity.map(e-> mapper.apply(e))
					.orElseThrow(() -> new BusinessException(expMsg));
			long end = System.currentTimeMillis();
			R response = (R) new ResponseDTO(dto, end-start);
			return response;
		};
	}
	
	public Function<T,R> doBusinessLogic(P p,T t,Optional<E> entity, Function<E,T> mapper ,
			IRestValidator v, String expMsg, OrderParams params) {
		return resp -> {
			long start = System.currentTimeMillis();
			v.validate(expMsg).accept(p, params);
			if(!entity.isPresent()){
				long end = System.currentTimeMillis();
				R response = (R) new ResponseDTO(new ArrayList<>(1), end-start);
				return response;
			}
/*			List<T> dto = entity.map(sub -> {
				return sub.stream()
						.map(e-> mapper.apply(e))
						.collect(Collectors.toList());
					}).orElseThrow(() -> new BusinessException(expMsg));*/
			T dto = entity.map(e-> mapper.apply(e))
					.orElseThrow(() -> new BusinessException(expMsg));
			long end = System.currentTimeMillis();
			R response = (R) new ResponseDTO(dto, end-start);
			return response;
		};
	}
}
