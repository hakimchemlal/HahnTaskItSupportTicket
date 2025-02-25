package com.hahn.system.hahnitsupport.service;

import com.hahn.system.hahnitsupport.dto.response.EmployeeResponse;

public interface EmployeeService {
    EmployeeResponse getEmployeeByUsername(String username);
}
