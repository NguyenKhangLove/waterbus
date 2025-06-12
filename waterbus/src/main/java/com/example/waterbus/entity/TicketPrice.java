package com.example.waterbus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Table(name = "ticket_price")
@Data
public class TicketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrice;
    @ManyToOne
    private Category category;
    private Double price;
    private LocalDate createdDate;
}
