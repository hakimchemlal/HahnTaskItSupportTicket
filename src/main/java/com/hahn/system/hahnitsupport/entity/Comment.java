package com.hahn.system.hahnitsupport.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String bodyMessage;

    private LocalDateTime creationDate;

    @JsonIgnoreProperties("comments")
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
    }
}
