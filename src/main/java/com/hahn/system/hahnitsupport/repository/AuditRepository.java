package com.hahn.system.hahnitsupport.repository;

import com.hahn.system.hahnitsupport.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}