package com.example.waterbus.controller.admin;

import com.example.waterbus.domain.Ticket;
import com.example.waterbus.domain.TicketDetail;
import com.example.waterbus.dto.res.RevenueRes;
import com.example.waterbus.dto.res.TicketRes;
import com.example.waterbus.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketRes>> getAllTickets() {
        List<TicketRes> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/date")
    public ResponseEntity<List<TicketRes>> getTicketsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TicketRes> tickets = ticketService.getTicketsByDate(date);
        return ResponseEntity.ok(tickets);
    }

    // Thống kê theo ngày cụ thể
    @GetMapping("/day")
    public ResponseEntity<RevenueRes> getRevenueByDay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ticketService.getRevenueByDay(date));
    }

    // Thống kê theo tháng cụ thể
    @GetMapping("/month")
    public ResponseEntity<RevenueRes> getRevenueByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year) {
        return ResponseEntity.ok(ticketService.getRevenueByMonth(month, year));
    }
}
