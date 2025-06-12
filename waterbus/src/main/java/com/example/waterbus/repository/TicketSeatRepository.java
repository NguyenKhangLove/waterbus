package com.example.waterbus.repository;

import com.example.waterbus.entity.SeatTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketSeatRepository extends JpaRepository<SeatTicket,Long> {
}
