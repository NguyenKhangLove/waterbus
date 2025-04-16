package com.example.waterbus.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "seat_ticket")
@Data
public class SeatTicket {
    @Id
    private Long idSeat;
    @Id
    private Long idTicket;
}
