package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRes {
    private Long idTicket;
    private String customerName;
    private Long startStationId;
    private Long endStationId;
    private String staffName;
    private Long idTrip;
    private LocalDateTime bookingTime;
    private Double price;
    private Integer seatQuantity;
    private String paymentMethod;
    private LocalTime startDepartureTime;
    private LocalDate departureDate;
}
