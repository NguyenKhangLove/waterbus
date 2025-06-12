package com.example.waterbus.controller.admin;

import com.example.waterbus.dto.res.*;
import com.example.waterbus.repository.TicketRepository;
import com.example.waterbus.service.TicketPriceService;
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
    @Autowired
    private TicketPriceService ticketPriceService;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public ResponseEntity<List<TicketRes>> getAllTickets() {
        List<TicketRes> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalIncome() {
        return ResponseEntity.ok(ticketService.getTotalIncome());
    }

    @GetMapping("/day")
    public ResponseEntity<Double> getIncomeByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ticketService.getIncomeByDate(date));
    }

    @GetMapping("/month")
    public ResponseEntity<Double> getIncomeByMonth(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(ticketService.getIncomeByMonth(year, month));
    }

    @GetMapping("details/{id}")
    public ResponseEntity<List<TicketInfoRes>> getTicketDetails(@PathVariable("id") Long id) {
        List<TicketInfoRes> info = ticketService.getTicketInfo(id);
        return ResponseEntity.ok(info);
    }

    // Lấy giá tiền theo idPrice
    @GetMapping("/idPrice/{idPrice}")
    public ResponseEntity<TicketPriceRes> getTicketPriceById(@PathVariable Long idPrice) {
        TicketPriceRes priceRes = ticketPriceService.getTicketPriceById(idPrice);
        return ResponseEntity.ok(priceRes);
    }

    // Lấy giá tiền theo idCategory
    @GetMapping("/category/{idCategory}")
    public ResponseEntity<TicketPriceRes> getTicketPriceByCategory(@PathVariable Long idCategory) {
        TicketPriceRes priceRes = ticketPriceService.getTicketPriceByCategory(idCategory);
        return ResponseEntity.ok(priceRes);
    }

    @GetMapping("/stats/month")
    public List<TicketStatsDTO> getMonthlyStats() {
        return ticketRepository.getMonthlyTicketStats();
    }


    @GetMapping("/latest")
    public ResponseEntity<Double> getLatestPrice(@RequestParam Long categoryId) {
        Double price = ticketPriceService.getLatestPriceByCategoryId(categoryId);
        return ResponseEntity.ok(price);
    }
}
