package com.example.waterbus.service;

import com.example.waterbus.domain.Ticket;
import com.example.waterbus.domain.TicketDetail;
import com.example.waterbus.dto.res.RevenueRes;
import com.example.waterbus.dto.res.TicketRes;
import com.example.waterbus.repository.TicketDetailRepository;
import com.example.waterbus.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketDetailRepository ticketDetailRepository;

    public List<TicketRes> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .map(ticket -> new TicketRes(
                        ticket.getIdTicket(),
                        ticket.getCustomer().getId(),
                        ticket.getCustomer().getFullName(),
                        ticket.getStartStationId(),
                        ticket.getEndStationId(),
                        ticket.getIdStaff(),
                        ticket.getIdTrip(),
                        ticket.getBookingTime(),
                        ticket.getPrice(),
                        ticket.getSeatQuantity(),
                        ticket.getPaymentMethod()
                ))
                .collect(Collectors.toList());
    }

    public List<TicketRes> getTicketsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Ticket> tickets = ticketRepository.findAllByBookingTimeBetween(startOfDay, endOfDay);

        return tickets.stream()
                .map(ticket -> new TicketRes(
                        ticket.getIdTicket(),
                        ticket.getCustomer().getId(),
                        ticket.getCustomer().getFullName(),
                        ticket.getStartStationId(),
                        ticket.getEndStationId(),
                        ticket.getIdStaff(),
                        ticket.getIdTrip(),
                        ticket.getBookingTime(),
                        ticket.getPrice(),
                        ticket.getSeatQuantity(),
                        ticket.getPaymentMethod()
                ))
                .collect(Collectors.toList());
    }


    public RevenueRes getRevenueByDay(LocalDate day) {
        Double total = ticketRepository.getRevenueByExactDay(day);
        return new RevenueRes(day.toString(), total != null ? total : 0.0);
    }

    public RevenueRes getRevenueByMonth(int month, int year) {
        Double total = ticketRepository.getRevenueByExactMonth(month, year);
        return new RevenueRes(String.format("%02d/%d", month, year), total != null ? total : 0.0);
    }
}
