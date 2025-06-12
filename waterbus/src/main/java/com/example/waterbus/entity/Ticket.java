package com.example.waterbus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @ManyToOne
    private Customer customer;
    private Long startStationId;
    private Long endStationId;
    private Long idStaff;
    private Long idTrip;

    private LocalDateTime bookingTime;
    private Double price;
    private Integer seatQuantity;
    private String paymentMethod;
}
