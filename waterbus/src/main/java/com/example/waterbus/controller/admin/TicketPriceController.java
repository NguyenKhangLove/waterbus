package com.example.waterbus.controller.admin;

import com.example.waterbus.dto.req.TicketPriceDTO;
import com.example.waterbus.service.TicketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-prices")
public class TicketPriceController {

    @Autowired
    private TicketPriceService ticketPriceService;

    @GetMapping
    public List<TicketPriceDTO> getAllPrices() {
        return ticketPriceService.getAllPrices();
    }

    @PostMapping
    public ResponseEntity<TicketPriceDTO> addPrice(@RequestBody TicketPriceDTO dto) {
        TicketPriceDTO created = ticketPriceService.addTicketPrice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
