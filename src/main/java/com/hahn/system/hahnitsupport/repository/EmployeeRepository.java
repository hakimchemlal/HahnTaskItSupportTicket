package com.hahn.system.hahnitsupport.repository;


import com.hahn.system.hahnitsupport.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    boolean existsById(Long id);

    Optional<Employee> findByUsername(String username);

}
