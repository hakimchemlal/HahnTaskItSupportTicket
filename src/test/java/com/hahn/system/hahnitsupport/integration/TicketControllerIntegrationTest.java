package com.hahn.system.hahnitsupport.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahn.system.hahnitsupport.entity.Employee;
import com.hahn.system.hahnitsupport.entity.Status;
import com.hahn.system.hahnitsupport.entity.Ticket;
import com.hahn.system.hahnitsupport.repository.EmployeeRepository;
import com.hahn.system.hahnitsupport.service.TicketService;
import com.hahn.system.hahnitsupport.web.TicketController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee testEmployee;
    private Ticket testTicket;
    private User userDetails;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee();
        testEmployee.setId(1L);
        testEmployee.setUsername("testUser");

        testTicket = new Ticket();
        testTicket.setId(1L);
        testTicket.setTitle("Test Ticket");
        testTicket.setStatus(Status.NEW);
        testTicket.setCreatedBy(testEmployee);

        userDetails = new User(
                "testUser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
        );
        when(employeeRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(testEmployee));
    }


    @Test
    @WithMockUser(roles = "IT_SUPPORT")
    void addComment_ShouldAddCommentToTicket() throws Exception {
        // Arrange
        String comment = "Test comment";
        when(ticketService.addCommentToTicket(eq(1L), anyString()))
                .thenReturn(testTicket);

        // Act & Assert
        mockMvc.perform(post("/api/tickets/add/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comment))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    /*@Test
    @WithMockUser(username = "testUser", roles = "EMPLOYEE")
    void createTicket_ShouldCreateNewTicket() throws Exception {
        // Arrange
        Ticket inputTicket = new Ticket();
        inputTicket.setTitle("Test Ticket");
        inputTicket.setDescription("Test Description");

        when(ticketService.createTicket(any()))
                .thenReturn(testTicket);

        // Act & Assert
        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputTicket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Ticket"));

        // Verify
        verify(employeeRepository).findByUsername("testUser");
        verify(ticketService).createTicket(any());
    }*/


}