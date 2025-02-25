package com.hahn.system.hahnitsupport.service.impl;

import com.hahn.system.hahnitsupport.entity.*;
import com.hahn.system.hahnitsupport.repository.CommentRepository;
import com.hahn.system.hahnitsupport.repository.TicketRepository;
import com.hahn.system.hahnitsupport.repository.AuditRepository;
import com.hahn.system.hahnitsupport.service.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final AuditRepository auditRepository;
    private final CommentRepository commentRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, AuditRepository auditRepository, CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.auditRepository = auditRepository;
        this.commentRepository= commentRepository;
    }

    @Override
    @Transactional
    public Ticket createTicket(Ticket ticket) {
        Ticket savedTicket = ticketRepository.save(ticket);
        Audit audit = new Audit();
        audit.setAction(AuditAction.CREATED);
        audit.setTicket(savedTicket);
        audit.setPerformedBy(savedTicket.getCreatedBy());
        audit.setNewValue("Ticket created");
        auditRepository.save(audit);
        return savedTicket;
    }

    @Override
    @Transactional
    public Ticket updateTicketStatus(Long ticketId, Status newStatus, Employee performedBy) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        Status oldStatus = ticket.getStatus();
        ticket.setStatus(newStatus);
        Ticket updatedTicket = ticketRepository.save(ticket);
        Audit audit = new Audit();
        audit.setAction(AuditAction.STATUS_UPDATED);
        audit.setTicket(updatedTicket);
        audit.setPerformedBy(performedBy);
        audit.setOldValue(oldStatus.name());
        audit.setNewValue(newStatus.name());
        auditRepository.save(audit);
        return updatedTicket;
    }

    @Override
    @Transactional
    public Ticket addComment(Long ticketId, String comment, Employee performedBy) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        Audit audit = new Audit();
        audit.setAction(AuditAction.COMMENT_ADDED);
        audit.setTicket(ticket);
        audit.setPerformedBy(performedBy);
        audit.setNewValue(comment);
        auditRepository.save(audit);
        return ticket;
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Override
    public List<Ticket> getTicketsByStatus(Status status) {
        return ticketRepository.findByStatus(status);
    }

    @Override
    public List<Ticket> getTicketsForEmployee(Employee employee) {
        return ticketRepository.findByCreatedBy(employee);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket addCommentToTicket(Long ticketId, String bodyMessage) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        Comment comment = new Comment();
        comment.setBodyMessage(bodyMessage);
        comment.setTicket(ticket);
        comment.setCreationDate(LocalDateTime.now());  // Ajoutez la date de création

        ticket.getComments().add(comment);

        return ticketRepository.save(ticket);
    }

    @Override
    public List<Comment> getCommentsByTicketId(Long ticketId) {
        return commentRepository.findByTicketId(ticketId);
    }
}