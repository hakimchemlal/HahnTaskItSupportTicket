package com.hahn.system.hahnitsupport.repository;

import com.hahn.system.hahnitsupport.entity.Ticket;
import com.hahn.system.hahnitsupport.entity.Status;
import com.hahn.system.hahnitsupport.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(Status status);
    List<Ticket> findByCreatedBy(Employee employee);
}

