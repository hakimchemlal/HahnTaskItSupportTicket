package com.hahn.system.hahnitsupport.web;

import com.hahn.system.hahnitsupport.entity.*;
import com.hahn.system.hahnitsupport.service.TicketService;
import com.hahn.system.hahnitsupport.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final EmployeeRepository employeeRepository;

    public TicketController(TicketService ticketService, EmployeeRepository employeeRepository) {
        this.ticketService = ticketService;
        this.employeeRepository = employeeRepository;
    }

    // Création d'un ticket (accessible aux Employees)
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket, @AuthenticationPrincipal User userDetails) {
        Employee employee = employeeRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        ticket.setCreatedBy(employee);
        return ticketService.createTicket(ticket);
    }

    // Consultation de ses propres tickets
    @GetMapping("/my")
    public List<Ticket> getMyTickets(@AuthenticationPrincipal User userDetails) {
        Employee employee = employeeRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ticketService.getTicketsForEmployee(employee);
    }

    // Consultation de tous les tickets (accessible à IT Support)
    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // Consultation d'un ticket par son ID
    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    // Recherche par ID ou par statut
    @GetMapping("/search")
    public List<Ticket> searchTickets(@RequestParam(required = false) Long ticketId,
                                      @RequestParam(required = false) Status status) {
        if (ticketId != null) {
            return List.of(ticketService.getTicketById(ticketId));
        } else if (status != null) {
            return ticketService.getTicketsByStatus(status);
        } else {
            return ticketService.getAllTickets();
        }
    }

    // Mise à jour du statut d'un ticket (accessible à IT Support)
    @PutMapping("/{id}/status")
    public Ticket updateTicketStatus(@PathVariable Long id,
                                     @RequestParam Status newStatus,
                                     @AuthenticationPrincipal User userDetails) {
        Employee employee = employeeRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ticketService.updateTicketStatus(id, newStatus, employee);
    }

    // Ajout d'un commentaire (accessible à IT Support)
    @PostMapping("/{id}/comments")
    public Ticket addComment(@PathVariable Long id,
                             @RequestParam String comment,
                             @AuthenticationPrincipal User userDetails) {
        Employee employee = employeeRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ticketService.addComment(id, comment, employee);
    }

    @PostMapping("/add/{ticketId}/comments")
    public ResponseEntity<Ticket> addComment(@PathVariable Long ticketId,
                                             @RequestBody String comment) {
        try {
            Ticket savedComment = ticketService.addCommentToTicket(
                    ticketId,
                    comment
            );
            return ResponseEntity.ok(savedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<Comment>> getCommentsByTicketId(@PathVariable Long ticketId) {
        List<Comment> comments = ticketService.getCommentsByTicketId(ticketId);
        return ResponseEntity.ok(comments);
    }
}