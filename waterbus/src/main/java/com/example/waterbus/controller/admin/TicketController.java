package com.example.waterbus.controller.admin;

import com.example.waterbus.dto.res.*;
import com.example.waterbus.entity.TicketDetail;
import com.example.waterbus.repository.TicketDetailRepository;
import com.example.waterbus.repository.TicketRepository;
import com.example.waterbus.service.TicketPriceService;
import com.example.waterbus.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketPriceService ticketPriceService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketDetailRepository ticketDetailRepository;

    @GetMapping
    public ResponseEntity<List<TicketRes>> getAllTickets() {
        List<TicketRes> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/date")
    public ResponseEntity<List<TicketRes>> getTicketsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TicketRes> tickets = ticketService.getTicketsByDate(date);
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

    @GetMapping("/by-type")
    public List<Map<String, Object>> getTicketStatsByType() {
        List<TicketDetail> allDetails = ticketDetailRepository.findAll();
        int currentYear = LocalDate.now().getYear();

        long under5 = allDetails.stream().filter(d -> currentYear - d.getBirthYear() < 5).count();
        long over80 = allDetails.stream().filter(d -> currentYear - d.getBirthYear() > 80).count();
        long from5To80 = allDetails.size() - under5 - over80;

        return List.of(
                Map.of("label", "Dưới 5 tuổi", "count", under5),
                Map.of("label", "Trên 80 tuổi", "count", over80),
                Map.of("label", "Từ 5 đến 80 tuổi", "count", from5To80)
        );
    }
}
