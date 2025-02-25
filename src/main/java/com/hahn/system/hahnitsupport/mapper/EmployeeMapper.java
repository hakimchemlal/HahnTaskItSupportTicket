package com.hahn.system.hahnitsupport.mapper;

import com.hahn.system.hahnitsupport.dto.request.EmployeeRequest;
import com.hahn.system.hahnitsupport.dto.response.EmployeeResponse;
import com.hahn.system.hahnitsupport.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEntity(EmployeeRequest request);

    EmployeeResponse toResponse(Employee employee);


    List<EmployeeResponse> toResponseList(List<Employee> employees);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(EmployeeRequest request, @MappingTarget Employee employee);
}
