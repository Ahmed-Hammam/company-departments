package com.company.core.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.core.dto.DepartmentReportDTO;
import com.company.core.entity.Department;

public class DepartmentReportMapper {

	/*public static final Function<Object[],DepartmentReportDTO> mapEntitytoDTO(){
			return e -> {DepartmentReportDTO reportDTO = new DepartmentReportDTO(
					((Department) e[0]).getName(),(Long) e[1]);
				return reportDTO;
			};
	}*/
	
	public static final Function<List<Object[]>,List<DepartmentReportDTO>> mapEntitytoDTO(){
		return arr -> arr.stream().map(
				e->{DepartmentReportDTO reportDTO = new DepartmentReportDTO(
				((Department) e[0]).getName(),(Long) e[1]);
			return reportDTO;})
				.collect(Collectors.toList());
		}
}
