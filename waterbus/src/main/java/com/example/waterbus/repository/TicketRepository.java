package com.example.waterbus.repository;

import com.example.waterbus.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findAllByBookingTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
    @Query("SELECT SUM(t.price * t.seatQuantity) FROM Ticket t " +
            "WHERE DATE(t.bookingTime) = :day")
    Double getRevenueByExactDay(@Param("day") LocalDate day);

    @Query("SELECT SUM(t.price * t.seatQuantity) FROM Ticket t " +
            "WHERE FUNCTION('MONTH', t.bookingTime) = :month AND FUNCTION('YEAR', t.bookingTime) = :year")
    Double getRevenueByExactMonth(@Param("month") int month, @Param("year") int year);

    Optional<Ticket> findById(Long id);
}
