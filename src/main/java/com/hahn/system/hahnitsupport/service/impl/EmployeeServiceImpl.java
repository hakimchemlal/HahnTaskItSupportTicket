package com.hahn.system.hahnitsupport.service.impl;

import com.hahn.system.hahnitsupport.dto.response.EmployeeResponse;
import com.hahn.system.hahnitsupport.entity.Employee;
import com.hahn.system.hahnitsupport.mapper.EmployeeMapper;
import com.hahn.system.hahnitsupport.repository.EmployeeRepository;
import com.hahn.system.hahnitsupport.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeResponse getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Employee with username " + username + " does not exist"));
        return employeeMapper.toResponse(employee);
    }
}
