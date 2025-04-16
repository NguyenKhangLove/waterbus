package com.example.waterbus.service;

import com.example.waterbus.domain.Customer;
import com.example.waterbus.domain.SeatTicket;
import com.example.waterbus.domain.Ticket;
import com.example.waterbus.domain.TicketDetail;
import com.example.waterbus.dto.req.TicketDetailReq;
import com.example.waterbus.dto.req.TicketReq;
import com.example.waterbus.repository.CustomerRepository;
import com.example.waterbus.repository.TicketDetailRepository;
import com.example.waterbus.repository.TicketRepository;
import com.example.waterbus.repository.TicketSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketSeatRepository ticketSeatRepository;
    @Autowired
    private TicketDetailRepository ticketDetailRepository;

    /*public void bookTicket(TicketReq req) {
        // 1. Lưu thông tin khách hàng
        Customer customer = new Customer(req.getCustomer());
        customerRepository.save(customer);
        // 2. Tạo vé
        Ticket ticket = new Ticket();
        ticket.setCustomer(customer.getId());
        ticket.setIdTrip(req.getTripId());
        ticket.setBookingTime(LocalDateTime.now());
        ticket.setStartStationId(req.getStartStationId());
        ticket.setEndStationId(req.getEndStationId());
        ticket.setDepartureTime(req.getDepartureTime());
        ticket.setArrivalTime(req.getArrivalTime());
        ticket.setDepartureDate(req.getDepartureDate());
        ticketRepository.save(ticket);

        // 3. Lưu danh sách ghế
        for (String seat : req.getSeats()) {
            TicketSeatRepository ticketSeat = new SeatTicket(ticket(), seat);
            ticketSeatRepository.save(ticketSeat);
        }

        // 4. Lưu danh sách hành khách nếu có
        if (req.getTicketDetails() != null && !req.getTicketDetails().isEmpty()) {
            for (TicketDetailReq dto : req.getTicketDetails()) {
                TicketDetail detail = new TicketDetail();
                detail.setIdDetail(ticket.getIdTicket());
                detail.setFullName(dto.getFullname());
                detail.setBirthYear(dto.getBirthYear());
                ticketDetailRepository.save(detail);
            }
        }
    }*/
}
