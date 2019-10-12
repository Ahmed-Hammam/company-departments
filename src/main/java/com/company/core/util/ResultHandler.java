package com.company.core.util;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.company.core.dto.ResponseDTO;
import com.company.core.entity.OrderParams;
import com.company.core.exception.BusinessException;

public class ResultHandler<T,E,R,P> {

	public Function<T,R> doBusinessLogic(T t,Optional<E> entity, Function<E,T> mapper ,
			BiConsumer<Object,OrderParams> v, String expMsg, OrderParams params, boolean skipValidation 
			, long oldStart) {
		return resp -> {
			long start = 0;
			if (!skipValidation) {
				start = System.currentTimeMillis();
				R invalidResponse = validateEntity(t, entity, v, params, start);
				if (Objects.nonNull(invalidResponse)) {
					return invalidResponse;
				}
			}
			start = oldStart;
			T dto = entity.map(e-> mapper.apply(e))
					.orElseThrow(() -> new BusinessException(expMsg));
			long end = System.currentTimeMillis();
			R response = (R) new ResponseDTO(dto, end-start);
			return response;
		};
	}

	
	public Function<T,R> doBusinessLogic(P p,T t,Optional<E> entity, Function<E,T> mapper ,
			BiConsumer<Object,OrderParams> v, String expMsg, OrderParams params) {
		return resp -> {
			long start = System.currentTimeMillis();
			R invalidResponse = validateEntity(p, entity, v, params, start);
			if(Objects.isNull(invalidResponse)){
			return this.doBusinessLogic(t, entity, mapper, v, expMsg, params, true, start)
			.apply(t);
			}
/*			List<T> dto = entity.map(sub -> {
				return sub.stream()
						.map(e-> mapper.apply(e))
						.collect(Collectors.toList());
					}).orElseThrow(() -> new BusinessException(expMsg));*/
			/*T dto = entity.map(e-> mapper.apply(e))
					.orElseThrow(() -> new BusinessException(expMsg));
			long end = System.currentTimeMillis();
			R response = (R) new ResponseDTO(dto, end-start);
			return response;*/
			return invalidResponse;
		};
	}

	private R validateEntity(Object data, Optional<E> entity, BiConsumer<Object, OrderParams> v, OrderParams params,
			long start) {
		v.accept(data, params);
		if(!entity.isPresent()){
			long end = System.currentTimeMillis();
			R response = (R) new ResponseDTO(new ArrayList<>(1), end-start);
			return response;
		}
		return null;
	}
}
