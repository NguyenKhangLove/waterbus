package com.example.waterbus.repository;

import com.example.waterbus.entity.SeatTicket;
import com.example.waterbus.entity.SeatTicketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatTicketRepository extends JpaRepository<SeatTicket, SeatTicketId> {
    @Query("SELECT s.id.idSeat FROM SeatTicket s WHERE s.id.idTicket = :ticketId")
    List<Long> findSeatIdsByTicketId(@Param("ticketId") Long ticketId);
}
