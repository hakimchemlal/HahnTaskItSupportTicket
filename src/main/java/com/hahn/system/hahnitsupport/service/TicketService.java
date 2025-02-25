package com.hahn.system.hahnitsupport.service;

import com.hahn.system.hahnitsupport.entity.*;
import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket);
    Ticket updateTicketStatus(Long ticketId, Status newStatus, Employee performedBy);
    Ticket addComment(Long ticketId, String comment, Employee performedBy);
    Ticket getTicketById(Long id);
    List<Ticket> getTicketsByStatus(Status status);
    List<Ticket> getTicketsForEmployee(Employee employee);
    List<Ticket> getAllTickets();
    Ticket addCommentToTicket(Long ticketId, String bodyMessage);
    List<Comment> getCommentsByTicketId(Long ticketId);
}