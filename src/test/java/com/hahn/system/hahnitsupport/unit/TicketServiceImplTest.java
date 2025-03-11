package com.hahn.system.hahnitsupport.unit;

import com.hahn.system.hahnitsupport.entity.Audit;
import com.hahn.system.hahnitsupport.entity.Employee;
import com.hahn.system.hahnitsupport.entity.Status;
import com.hahn.system.hahnitsupport.entity.Ticket;
import com.hahn.system.hahnitsupport.repository.AuditRepository;
import com.hahn.system.hahnitsupport.repository.CommentRepository;
import com.hahn.system.hahnitsupport.repository.TicketRepository;
import com.hahn.system.hahnitsupport.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private Ticket ticket;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setUsername("testUser");

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Test Ticket");
        ticket.setStatus(Status.NEW);
        ticket.setCreatedBy(employee);
    }

    @Test
    void createTicket_ShouldCreateTicketAndAudit() {
        // Arrange
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(auditRepository.save(any(Audit.class))).thenReturn(new Audit());

        // Act
        Ticket result = ticketService.createTicket(ticket);

        // Assert
        assertNotNull(result);
        assertEquals("Test Ticket", result.getTitle());
        verify(ticketRepository).save(ticket);
        verify(auditRepository).save(any(Audit.class));
    }

    @Test
    void updateTicketStatus_ShouldUpdateStatusAndCreateAudit() {
        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(auditRepository.save(any(Audit.class))).thenReturn(new Audit());

        // Act
        Ticket result = ticketService.updateTicketStatus(1L, Status.IN_PROGRESS, employee);

        // Assert
        assertEquals(Status.IN_PROGRESS, result.getStatus());
        verify(auditRepository).save(any(Audit.class));
    }

    @Test
    void getTicketById_WhenTicketExists_ShouldReturnTicket() {
        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // Act
        Ticket result = ticketService.getTicketById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getTicketById_WhenTicketDoesNotExist_ShouldThrowException() {
        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> ticketService.getTicketById(1L));
    }

    @Test
    void addCommentToTicket_ShouldAddCommentAndReturnTicket() {
        // Arrange
        String commentMessage = "Test comment";
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // Act
        Ticket result = ticketService.addCommentToTicket(1L, commentMessage);

        // Assert
        assertNotNull(result);
        assertFalse(result.getComments().isEmpty());
        verify(ticketRepository).save(any(Ticket.class));
    }
}