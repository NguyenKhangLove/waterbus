package com.example.waterbus.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ticket_detail")
@Data
public class TicketDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetail;
    @ManyToOne
    private Ticket ticket;
    @ManyToOne
    private Category category;
    private String fullName;
    private Integer birthYear;
}
