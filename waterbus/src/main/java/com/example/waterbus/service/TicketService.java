package com.example.waterbus.service;

import com.example.waterbus.entity.*;
import com.example.waterbus.dto.res.RevenueRes;
import com.example.waterbus.dto.res.TicketInfoRes;
import com.example.waterbus.dto.res.TicketRes;
import com.example.waterbus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketDetailRepository ticketDetailRepository;
    @Autowired
    private SeatTicketRepository seatTicketRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private StaffRepository staffRepository;

    public List<TicketRes> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .map(ticket -> {
                    LocalTime startDepartureTime = ticketRepository.getStartTime(
                            ticket.getIdTrip(),
                            ticket.getStartStationId(),
                            ticket.getEndStationId()
                    );

                    Trip trip = tripRepository.findById(ticket.getIdTrip()).orElse(null);
                    LocalDate departureDate = (trip != null) ? trip.getDepartureDate() : null;

                    Staff staff = staffRepository.findById(ticket.getIdStaff()).orElse(null);
                    String staffName = (staff != null) ? staff.getFullName() : "N/A";

                    return new TicketRes(
                            ticket.getIdTicket(),
                            ticket.getCustomer().getFullName(),
                            ticket.getStartStationId(),
                            ticket.getEndStationId(),
                            staffName,
                            ticket.getIdTrip(),
                            ticket.getBookingTime(),
                            ticket.getPrice(),
                            ticket.getSeatQuantity(),
                            ticket.getPaymentMethod(),
                            startDepartureTime,
                            departureDate
                    );
                }).collect(Collectors.toList());
    }

    public List<TicketRes> getTicketsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Ticket> tickets = ticketRepository.findAllByBookingTimeBetween(startOfDay, endOfDay);

        return tickets.stream()
                .map(ticket -> {
                    LocalTime startDepartureTime = ticketRepository.getStartTime(
                            ticket.getIdTrip(),
                            ticket.getStartStationId(),
                            ticket.getEndStationId()
                    );

                    Trip trip = tripRepository.findById(ticket.getIdTrip()).orElse(null);
                    LocalDate departureDate = (trip != null) ? trip.getDepartureDate() : null;

                    Staff staff = staffRepository.findById(ticket.getIdStaff()).orElse(null);
                    String staffName = (staff != null) ? staff.getFullName() : "N/A";

                    return new TicketRes(
                            ticket.getIdTicket(),
                            ticket.getCustomer().getFullName(),
                            ticket.getStartStationId(),
                            ticket.getEndStationId(),
                            staffName,
                            ticket.getIdTrip(),
                            ticket.getBookingTime(),
                            ticket.getPrice(),
                            ticket.getSeatQuantity(),
                            ticket.getPaymentMethod(),
                            startDepartureTime,
                            departureDate
                    );
                })
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

    public List<TicketInfoRes> getTicketInfo(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        List<Long> seatIds = seatTicketRepository.findSeatIdsByTicketId(ticketId);

        if (ticket.getSeatQuantity() == 1) {
            // Trường hợp chỉ 1 khách => dùng Customer
            Customer customer = ticket.getCustomer();
            Long seatId = seatIds.isEmpty() ? null : seatIds.get(0);
            return List.of(new TicketInfoRes(customer.getFullName(), customer.getBirthYear(), List.of(seatId)));
        } else {
            // Nhiều khách => dùng TicketDetail
            List<TicketDetail> details = ticketDetailRepository.findByTicket_IdTicket(ticketId);
            List<TicketInfoRes> responses = new ArrayList<>();

            for (int i = 0; i < details.size(); i++) {
                TicketDetail detail = details.get(i);
                Long seatId = (i < seatIds.size()) ? seatIds.get(i) : null;

                responses.add(new TicketInfoRes(detail.getFullName(), detail.getBirthYear(), List.of(seatId)));
            }

            return responses;
        }
    }

    public Double getTotalIncome() {
        return Optional.ofNullable(ticketRepository.getTotalIncome()).orElse(0.0);
    }

    public Double getIncomeByDate(LocalDate date) {
        return Optional.ofNullable(ticketRepository.getIncomeByDate(date)).orElse(0.0);
    }

    public Double getIncomeByMonth(int year, int month) {
        return Optional.ofNullable(ticketRepository.getIncomeByMonth(year, month)).orElse(0.0);
    }

}
