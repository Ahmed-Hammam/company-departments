package com.company.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDTO {
	private Object data;
	private long execTime;
}
