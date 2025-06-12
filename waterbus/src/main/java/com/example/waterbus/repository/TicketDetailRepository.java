package com.example.waterbus.repository;

import com.example.waterbus.entity.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketDetailRepository extends JpaRepository<TicketDetail,Long> {
    List<TicketDetail> findByTicket_IdTicket(Long ticketId);

}
