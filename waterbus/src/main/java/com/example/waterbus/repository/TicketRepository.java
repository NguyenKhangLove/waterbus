package com.example.waterbus.repository;

import com.example.waterbus.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findAllByBookingTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

}
