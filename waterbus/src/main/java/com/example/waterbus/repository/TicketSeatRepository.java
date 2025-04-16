package com.example.waterbus.repository;

import com.example.waterbus.domain.SeatTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketSeatRepository extends JpaRepository<SeatTicket,Long> {
}
