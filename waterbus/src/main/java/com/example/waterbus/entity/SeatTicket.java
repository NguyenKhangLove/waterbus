package com.example.waterbus.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat_ticket")
@Data
@NoArgsConstructor
public class SeatTicket {
    @EmbeddedId
    private SeatTicketId id;
    public SeatTicket(Long idSeat, Long idTicket) {
        this.id = new SeatTicketId(idSeat, idTicket);
    }
}


